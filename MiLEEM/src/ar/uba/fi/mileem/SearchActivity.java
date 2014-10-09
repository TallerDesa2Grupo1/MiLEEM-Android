package ar.uba.fi.mileem;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ar.uba.fi.mileem.models.FormField;
import ar.uba.fi.mileem.models.PublicationResult;
import ar.uba.fi.mileem.models.SearchForm;
import ar.uba.fi.mileem.models.SortFilter;
import ar.uba.fi.mileem.utils.ApiHelper;
import ar.uba.fi.mileem.utils.DialogFactory;
import ar.uba.fi.mileem.utils.SearchCache;
import ar.uba.fi.mileem.utils.SearchViewAdapter;
import ar.uba.fi.mileem.utils.TypefaceSpan;
import ar.uba.fi.mileem.R;

public class SearchActivity extends ListActivity {

	private List<PublicationResult> mListItems;
	private PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<PublicationResult> mAdapter;
	private SortFilter filter = SortFilter.HIGHLIGHTED;
	private String order = "DESC";
	private long timestamp = 0;
	private boolean refreshEnabled = true;
	private Object adapterLock = new Object();
	private TextView emptyView = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_view);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if(refreshEnabled)
							searchMoreResults();
					}
				});

		mPullRefreshListView.setMode(Mode.PULL_FROM_END );
		mPullRefreshListView.setShowIndicator(true);
		mPullRefreshListView.setShowViewWhileRefreshing(true);
		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
		setTitle();
		mListItems = SearchCache.getInstance().getResults();
		mAdapter = new SearchViewAdapter(this, mListItems);
		actualListView.setAdapter(mAdapter);
		emptyView = (TextView) findViewById(R.id.emptyText);
		mPullRefreshListView.setEmptyView(findViewById(R.id.emptyLayout));
		mPullRefreshListView.setScrollEmptyView(true);
		resetSearch();
	}

	
	protected void setTitle(){
		SpannableString s = new SpannableString(getString(R.string.app_name));
		s.setSpan(new TypefaceSpan(this, "Roboto-Light.ttf"), 0, s.length(),
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// Update the action bar title with the TypefaceSpan instance
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(s);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	private void resetSearch() {
		if(ApiHelper.getInstance().isNetworkAvailable(this)){
			if(refreshEnabled){
				timestamp = System.currentTimeMillis()/1000;
				SearchCache.getInstance().clearResults();
				synchronized (adapterLock) {
					mAdapter.notifyDataSetChanged();
				}
				searchMoreResults();
			}else{
				Log.e("refreshSearch", "NO PUEDE");
			}
		}else{
			DialogFactory.getFactory().showError(this, R.string.oops, R.string.connection_error, true);
		}
	}

	public void restoreFlags() {
		refreshEnabled = true;
		mPullRefreshListView.onRefreshComplete();
	}

	
	
	private void searchMoreResults() {
		emptyView.setText(R.string.pull_to_refresh_refreshing_label);
		refreshEnabled = false;
		mPullRefreshListView.setRefreshing(true);
		RequestParams rq = SearchForm.getAsRequestParams();
		rq.put(FormField.OFFSET.toString(), SearchCache.getInstance().getResults().size());
		rq.put(FormField.TIMESTAMP.toString(), timestamp);
		if(filter!=null){
			rq.put(FormField.SORT.toString(),filter.toString());
		}
		rq.put(FormField.ORDER.toString(),order);
		rq.put(FormField.NEIGHBORHOOD.toString(), SearchForm.getField(FormField.NEIGHBORHOOD));
		rq.put(FormField.OPERATION_TYPE.toString(), SearchForm.getField(FormField.OPERATION_TYPE));
		rq.put(FormField.PROPERTY_TYPE.toString(), SearchForm.getField(FormField.PROPERTY_TYPE));
		ApiHelper.getInstance().search(rq, new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				super.onSuccess(statusCode, headers, response);
				new DataTask(response).execute();
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("searchMoreResults", "onFailure");
				emptyView.setText(R.string.connection_error);
				restoreFlags();
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Log.e("searchMoreResults", "onFailure2");
				emptyView.setText(R.string.connection_error);
				restoreFlags();
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Log.e("searchMoreResults", "onFailure3");
				emptyView.setText(R.string.connection_error);
				restoreFlags();
				
			}
		});

	}


	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_results_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	    
	//Para ordenamiento
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		
		case R.id.action_chart:
			showCharts();
			break;
		case R.id.action_refresh:
			resetSearch();
			return true;
		case R.id.sort_destacados:
			Toast.makeText(this, R.string.sort_destacadas, Toast.LENGTH_SHORT)
					.show();
			filter = SortFilter.HIGHLIGHTED;
			order ="DESC";
			break;
		case R.id.sort_precio_mayor:
			Toast.makeText(this, R.string.sort_precio_mayor, Toast.LENGTH_SHORT)
					.show();
			filter = SortFilter.PRICE_DESC;
			order ="DESC";
			break;
		case R.id.sort_precio_menor:
			Toast.makeText(this, R.string.sort_precio_menor, Toast.LENGTH_SHORT)
					.show();
			filter = SortFilter.PRICE_ASC;
			order ="ASC";
			break;
		case R.id.sort_recientes:
			Toast.makeText(this, R.string.sort_recientes, Toast.LENGTH_SHORT)
					.show();
			filter = SortFilter.PUBLICATION_DATE_DESC;
			order ="DESC";
			break;
		 case android.R.id.home:
	         finish();
	         return true;
		default:
			return false;
		}
		resetSearch();
		return true;
	}

	

	private void showCharts() {
		Intent i = new Intent(SearchActivity.this, StatsActivity.class);
		SearchActivity.this.startActivity(i);
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isFinishing()) {
			SearchCache.getInstance().clearResults();
		}
	}



	private class DataTask extends
			AsyncTask<Void, Void, ArrayList<PublicationResult>> {

		JSONArray response = null;

		public DataTask(JSONArray response) {
			super();
			this.response = response;
		}

		@Override
		protected ArrayList<PublicationResult> doInBackground(Void... params) {

			ArrayList<PublicationResult> list = new ArrayList<PublicationResult>();
				for (int i = 0; i < response.length(); ++i) {
					try {
						list.add(new PublicationResult(response.getJSONObject(i)));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			
			 SearchCache.getInstance().addResults(list);
			
			 return null;
		}

		@Override
		protected void onPostExecute(ArrayList<PublicationResult> results) {
			synchronized (adapterLock) {
				 if(SearchCache.getInstance().getResults().size()==0){
						emptyView.setText("No se encontraron resultados =(");
				 }
				mAdapter.notifyDataSetChanged();
			}
			// Call onRefreshComplete when the list has been refreshed.
			 restoreFlags();
			super.onPostExecute(results);
		}
	}

	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		PublicationResult selectedPublication = (PublicationResult) getListView().getItemAtPosition(position);
		Intent i = new Intent(SearchActivity.this, PublicationActivity.class);
		Bundle b = new Bundle();
		b.putString(Config.PUBLICATION_ID, selectedPublication.getId());
		i.putExtras(b);		
		SearchActivity.this.startActivity(i);
	}

}
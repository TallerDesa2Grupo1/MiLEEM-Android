package ar.uba.fi.mileem;

import java.util.ArrayList;
import java.util.List;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import ar.uba.fi.mileem.models.PublicationResult;
import ar.uba.fi.mileem.utils.SearchCache;
import ar.uba.fi.mileem.utils.SearchViewAdapter;
import ar.uba.fi.mileem.R;


public class SearchActivity extends ListActivity {

	private List<PublicationResult> mListItems;
	private PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<PublicationResult> mAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_view);

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});

		mPullRefreshListView.setMode(Mode.BOTH);

		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);

		mListItems = SearchCache.getInstance().getResults();
		mAdapter = new SearchViewAdapter(this, mListItems);
		actualListView.setAdapter(mAdapter);
	
	}

	
	private class GetDataTask extends AsyncTask<Void, Void, ArrayList<PublicationResult>> {

		@Override
		protected  ArrayList<PublicationResult> doInBackground(Void... params) {
			
			/* Codigo que simula la carga de datos*/
			ArrayList<PublicationResult> list = new ArrayList<PublicationResult>();
			try {
				    for (int i = 0; i < 5; ++i) {
				      list.add(new PublicationResult());
				    }
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return list;
		}

		@Override
		protected void onPostExecute( ArrayList<PublicationResult> results) {
			mListItems = SearchCache.getInstance().addResults(results);	
			
			mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(results);
		}
	}

	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.search_results_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	        	new GetDataTask().execute();
				mPullRefreshListView.setRefreshing(true);
				break;
	        case R.id.sort_destacados:
	            Toast.makeText(this, R.string.sort_destacadas, Toast.LENGTH_SHORT).show();
	            break;
	        case R.id.sort_precio_mayor:
	            Toast.makeText(this, R.string.sort_precio_mayor, Toast.LENGTH_SHORT).show();
	            break;
	        case R.id.sort_precio_menor:
	        	Toast.makeText(this, R.string.sort_precio_menor, Toast.LENGTH_SHORT).show();
	        	break;
	        case R.id.sort_recientes:
	        	Toast.makeText(this, R.string.sort_recientes, Toast.LENGTH_SHORT).show();
	        	break;
	            default: return false;
	    }
	    return true;
	}
	

	


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

		menu.setHeaderTitle("Item: " + getListView().getItemAtPosition(info.position));
		menu.add("Item 1");
		menu.add("Item 2");
		menu.add("Item 3");
		menu.add("Item 4");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

		
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(isFinishing()){
			SearchCache.getInstance().clearResults();
		}
	}

	  public boolean onMenuItemClick (MenuItem item) {
		  switch (item.getItemId()) {
	        case R.id.sort_destacados:
	            Toast.makeText(this, R.string.sort_destacadas, Toast.LENGTH_SHORT).show();
	            break;
	        case R.id.sort_precio_mayor:
	            Toast.makeText(this, R.string.sort_precio_mayor, Toast.LENGTH_SHORT).show();
	            break;
	        case R.id.sort_precio_menor:
	        	Toast.makeText(this, R.string.sort_precio_menor, Toast.LENGTH_SHORT).show();
	        	break;
	        case R.id.sort_recientes:
	        	Toast.makeText(this, R.string.sort_recientes, Toast.LENGTH_SHORT).show();
	        	break;
	        default:
	        	return false;
	    }
		  return true;
    }

}
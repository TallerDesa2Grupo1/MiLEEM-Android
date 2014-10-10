package ar.uba.fi.mileem;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import ar.uba.fi.mileem.models.FormField;
import ar.uba.fi.mileem.models.SearchForm;
import ar.uba.fi.mileem.utils.ApiHelper;

@SuppressLint("SetJavaScriptEnabled")
public class StatsActivity  extends Activity {
	WebView webView = null;
	
	private boolean refreshEnabled = true;
	private TextView emptyView = null;
	protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		        setContentView(R.layout.layout_webchart);
		        getActionBar().setDisplayHomeAsUpEnabled(true);
		    	emptyView = (TextView) findViewById(R.id.emptyText);
		        resetSearch();
	}
	
	

	 public class WebAppInterface {
		 String data = "";
		public WebAppInterface(String data) {
			 this.data = data;
		}
		 
	     @JavascriptInterface
	     public String getData() {
	 	 return data;//"{\"rooms\":[{\"Publication\":{\"rooms\":\"1\",\"count\":\"4\"}},{\"Publication\":{\"rooms\":\"2\",\"count\":\"9\"}},{\"Publication\":{\"rooms\":\"4\",\"count\":\"6\"}}],\"average\":[{\"Publication\":{\"neighborhood_name\":\"Villa Ort\\u00fazar\",\"average_price\":\"0.0009000900090009\"}},{\"Publication\":{\"neighborhood_name\":\"Belgrano\",\"average_price\":\"77.37885154061624\"}},{\"Publication\":{\"neighborhood_name\":\"N\\u00fa\\u00f1ez\",\"average_price\":\"320\"}}]}";
	     }
	 }
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.stats_view_menu, menu);
			
			return super.onCreateOptionsMenu(menu);
		}
	 
	 private void resetSearch() {
			if(ApiHelper.getInstance().isNetworkAvailable(this)){
				if(refreshEnabled){
					emptyView.setText(R.string.cargando);
					 emptyView.setVisibility(View.VISIBLE);
					ApiHelper.getInstance().getStats((String)SearchForm.getField(FormField.NEIGHBORHOOD), new JsonHttpResponseHandler(){
						public void onSuccess(int statusCode, Header[] headers,	JSONObject response) {
							super.onSuccess(statusCode, headers, response);
							 emptyView.setVisibility(View.GONE);
							 webView = (WebView)findViewById(R.id.web);
						     webView.addJavascriptInterface(new WebAppInterface(response.toString()), "Android");
	  				         webView.getSettings().setJavaScriptEnabled(true); 
						     webView.loadUrl("file:///android_asset/chart.html");
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
			}
		}
	 	
	 public void restoreFlags() {
			refreshEnabled = true;
		}
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
			// Handle presses on the action bar items
			switch (item.getItemId()) {
			case R.id.action_refresh:
				resetSearch();
				return true;
			case android.R.id.home:
				finish();
				return true;
			}
			return false;
		}
}

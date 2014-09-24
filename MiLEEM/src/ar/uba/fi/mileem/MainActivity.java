package ar.uba.fi.mileem;

import org.apache.http.Header;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import ar.uba.fi.mileem.utils.ApiHelper;
import ar.uba.fi.mileem.utils.JsonCacheHttpResponseHandler;

public class MainActivity extends Activity {
	
	private static int SPLASH_TIME_OUT = 2000;
	final Object lock = new Object();
	boolean can_continue = false;
	int counter = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		ApiHelper.getInstance().getNeighborhoodsByCity(listener);
		ApiHelper.getInstance().getPropertyTypes(listener);
		new Handler().postDelayed(new Runnable() {
	            public void run() {
	            	can_continue = true;
	            	if(canContinue() ){
						go();
					}
	            }
	        }, SPLASH_TIME_OUT);
	}

	private final JsonCacheHttpResponseHandler listener  =  new JsonCacheHttpResponseHandler(){
		public void onSuccess(int statusCode, Header[] headers, org.json.JSONArray response) {
			synchronized (lock) {
				counter++;
				if(canContinue() ){
					go();
				}
			}
		};
		
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			Log.e("Splash", "Error: "+responseString);
			
		};
	};

	private boolean canContinue(){
		return (can_continue && (counter == 2));
	}
	private void go(){
		Intent i = new Intent(MainActivity.this,SimpleFormActivity.class);
		MainActivity.this.startActivity(i);
		finish();
	}

}

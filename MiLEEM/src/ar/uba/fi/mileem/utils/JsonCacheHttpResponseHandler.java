package ar.uba.fi.mileem.utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class JsonCacheHttpResponseHandler extends JsonHttpResponseHandler {
	
	public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
		if(statusCode != 0){
			int position = getRequestURI().toString().indexOf('?');
			String url = getRequestURI().toString().substring(0,(position>0)?position:getRequestURI().toString().length() );
			Log.e("JsonCache", "requestURI " + url);
			Cache.saveTo(url, response.toString());
		}
	}
	
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		if(statusCode != 0){
			int position = getRequestURI().toString().indexOf('?');
			String url =  getRequestURI().toString().substring(0,(position>0)?position:getRequestURI().toString().length() );
			Log.e("JsonCache", "requestURI " + url);
			Cache.saveTo(url, response.toString());
		}
	}

}

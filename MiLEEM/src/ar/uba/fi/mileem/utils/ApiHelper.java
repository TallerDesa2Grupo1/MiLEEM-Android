package ar.uba.fi.mileem.utils;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.*;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import ar.uba.fi.mileem.Config;
import ar.uba.fi.mileem.MileemApp;
import ar.uba.fi.mileem.R;

public class ApiHelper {
	private AsyncHttpClient client;
	private static ApiHelper INSTANCE = null;
	
	private ApiHelper(){
		client = new AsyncHttpClient();
	};
	
	static public ApiHelper getInstance(){
		if(INSTANCE == null){
			INSTANCE =  new ApiHelper();
		}
		return
		INSTANCE;
	}
	
	
	public String getVideoThumbnail(String youtube_code){
			return "http://img.youtube.com/vi/"+youtube_code+"/0.jpg";
	}
	
	
	
	public String getMapUrl(LatLng coords){
		return "http://maps.googleapis.com/maps/api/staticmap?sensor=false&size=600x300&maptype=roadmap&languaje=es&region=ar&center=%22"+coords.latitude+","+coords.longitude+"%22&zoom=15&markers=color:0xe91e63%7C"+coords.latitude+","+coords.longitude+"&key="+MileemApp.getContext().getString(R.string.api_key);
	}
	
	public void getStats(String id, JsonHttpResponseHandler responseHandler){
		doGet(Config.BASEURL + "stats/"+id, responseHandler);
	} 
	
	public void getPublication(String id, JsonHttpResponseHandler responseHandler){
		doGet(Config.BASEURL + Config.PUBLICACION_CONTROLLER+"/"+id, responseHandler);
	} 	
	
	public void search( RequestParams params,JsonHttpResponseHandler responseHandler){
		doGet(Config.BASEURL + Config.PUBLICACIONES_CONTROLLER ,params, responseHandler);
	} 
	
	public void getPropertyTypes(JsonHttpResponseHandler responseHandler){
		doGet(Config.BASEURL + Config.TIPO_PROPIEDADES_CONTROLLER, responseHandler);
	} 
	
	public void getOperationTypes(JsonHttpResponseHandler responseHandler){
		doGet(Config.BASEURL + Config.TIPO_OPERACIONES_CONTROLLER, responseHandler);
	} 
	
	public void getNeighborhoodsByCity(JsonHttpResponseHandler responseHandler){
		 getNeighborhoodsByCity(Config.CAPITAL_FEDERAL_ID,responseHandler);
	}

	public void getNeighborhoodsByCity(int city_id,JsonHttpResponseHandler responseHandler){
		doGet(Config.BASEURL + Config.BARRIOS_CONTROLLER,responseHandler);
	} 
	
	private void doGet(String url, JsonHttpResponseHandler responseHandler){
		doGet(url,null, responseHandler);
	}
	
	@SuppressWarnings("unused")
	private void doPost(String url, JsonHttpResponseHandler responseHandler){
		doPost(url,null, responseHandler);
	}
	
	private void doGet(String url,RequestParams rq, JsonHttpResponseHandler responseHandler){
		String o = (String) Cache.getCachedObject(url);
		if(o != null){
			responseHandler.sendSuccessMessage(0, null,o.getBytes());
		}else{
			if(rq == null)
				client.get(url,responseHandler);
			else
				client.get(url, rq, responseHandler);
		}
	}
	
	private void doPost(String url,RequestParams rq, JsonHttpResponseHandler responseHandler){
		String o = (String) Cache.getCachedObject(url);
		if(o != null){
			responseHandler.sendSuccessMessage(0, null,o.getBytes());
		}else{
			if(rq == null)
				client.post(url,responseHandler);
			else
				client.post(url, rq, responseHandler);
		}
	}
	
	public  boolean isNetworkAvailable(Context ctx) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	
}

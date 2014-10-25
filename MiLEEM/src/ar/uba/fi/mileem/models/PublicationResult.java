package ar.uba.fi.mileem.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.uba.fi.mileem.Config;


/*
 * 
 * {
"Publication":{
	"images_url":"encoded json array",
	"currency":"ARS",
	"publication_type":"1",
	"address":"ddsd 34",
	"rooms":"34",
	"total_area":"34",
	"neighborhood":"Agronom\u00eda",
	"operation_type":"Venta",
	"property_type":"Casa",
	"scaled_price":"234524"
	}
}
 * */

public class PublicationResult {
	
	int height;
	JSONObject publication = null;
	
		
	public PublicationResult(JSONObject jo){
		this.publication = jo.optJSONObject("Publication");
	}
	
	public String toString() {
			return getAddress();
	}
	
	public String getAddress(){
		return publication.optString("address");
	} 
	
	public String getDescription(){
		return getNeighborhood() +' '+ getRooms() + getTotalArea();
	} 
	
	public String getPrice(){
		return ((publication.optString("currency").equals("ARS"))?"$":"u$d") + " "+publication.optString("price");
	}
	
	
	public Boolean isHighlighted(){
		return  !publication.optString("publication_type").equals("1");
	}

	
	public String getNeighborhood(){
		try{
			return  publication.optString("neighborhood");
		}catch( Exception e){
			return "";
		}
	}
	
	public String getRooms(){
		String rooms = "";
		try{
			rooms =  publication.optString("rooms");
		}catch( Exception e){
		}
		return (rooms.equals("null"))?"":rooms+" Amb.";
	}
	
	public String getTotalArea(){
		String area = "";
		try{
			area =  publication.optString("total_area");
		}catch( Exception e){
		}
		return (area.equals(""))?"":" "+area+" m2";
	}
	
	
	public String getMainImage(){
		String imgs = publication.optString("images_url");
		JSONArray ja=null;
		try {
			ja = new JSONArray(imgs);
		} catch (JSONException e) {
		}
		if(ja != null && ja.length()>0){
			return Config.SITEBASEURL + ja.optString(0);
		}
		return null;
	}

	public String getId() {
		
		return publication.optString("id");
	} 
	
	
	
}

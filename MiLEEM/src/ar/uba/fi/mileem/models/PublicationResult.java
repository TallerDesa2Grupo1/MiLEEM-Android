package ar.uba.fi.mileem.models;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;



public class PublicationResult {
	
	int height;
	JSONObject publication = null;
	public PublicationResult(){
		
		Random r=new Random();
		height = (r.nextInt(1000));
		String json = "{\"id\":\""+height+"\", \"direccion\":\""+toString()+"\"}";
		try {
			publication = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
			return "Calle Falsa " + height;
	}

}

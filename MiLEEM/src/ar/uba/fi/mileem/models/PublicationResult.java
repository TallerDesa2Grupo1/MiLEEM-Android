package ar.uba.fi.mileem.models;

import java.util.Date;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;




public class PublicationResult {
	
	int height;
	JSONObject publication = null;
	
	public PublicationResult(){
		
		Random r=new Random();
		height = (r.nextInt(1000));
		String json = "{  \"id\": 5,  \"price_with_currency\": \"$ 100."+height+"\",  \"highlight\": "+((height%2==0)?"false":"true")+",  \"property\": [    {      \"address\": \"Dr E Finochietto 850\",      \"characteristic_instances\": [],      \"images\": [  \"http://www.alquilerentaturista.com.ar/apartamentos/buenosairesg.jpg\",\t\t  \"http://www.rentnbaires.com/JUS/alquiler-temporario-departamentos/images/calo1.jpg\",\t\t  \"http://www.alquiler-temporario.com/imagenes/alquiler-temporario-departamento-1-dormitorio-argentina-cordoba/GetAttachment7.jpg\",\t\t  \"http://imganuncios.mitula.net/alquiler_de_03_departamentos_de_estreno_96752207889417804.jpg\",],      \"videos\": [\"https://www.youtube.com/watch?v=-F_9fgtEKYg\"],      \"property_type\": {        \"id\": 1,        \"name\": \"Casa\"      },      \"location\": {        \"id\": 2,        \"name\": \"Belgrano\"      }    }  ],  \"user_id\": 1}";
		try {
			publication = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public PublicationResult(JSONObject jo){
		this.publication = jo;
	}
	
	public String toString() {
			return getAddress();
	}
	
	public String getAddress(){
		return publication.optString("address");
	} 
	
	public String getPrice(){
		return publication.optString("price_with_currency");
	}
	
	public Date getPublicationDate(){
		return new Date();
	}
	
	public Boolean isHighlighted(){
		return  publication.optBoolean("highlight");
	}

	
	public String getNeighborhood(){
		try{
			return  publication.optJSONObject("location").optString("name");
		}catch( Exception e){
			return "";
		}
	}
	
	public String getMainImage(){
//		JSONArray ja = optProperty().optJSONArray("images");
//		if(ja != null){
//			return ja.optString(0);
//		}
		return "http://www.rentnbaires.com/JUS/alquiler-temporario-departamentos/images/calo1.jpg";
	} 
	
//	private JSONObject optProperty(){
//		JSONArray ja =  publication.optJSONArray("property");
//		if(ja != null ){
//			JSONObject jo = ja.optJSONObject(0);
//			if(jo == null){
//				return new JSONObject();
//			}
//			return jo;
//		}
//		return new JSONObject();
//	}
	
}

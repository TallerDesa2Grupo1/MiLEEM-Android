package ar.uba.fi.mileem.models;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.uba.fi.mileem.Config;

import com.google.android.gms.maps.model.LatLng;

public class PublicationFullResult extends PublicationResult {

	JSONObject contact = null;
	JSONObject publication = null;

	public PublicationFullResult(JSONObject jo) {
		super(jo);
		contact = jo.optJSONObject("User");
		publication = jo.optJSONObject("Publication");

	}

	/*
	 * 
	 * { "Publication":{"id":"1", "street":"Av Diaz Velez", "st_number":"3790",
	 * "neighborhood_id":"2", "covered_area":"30", "total_area":"35",
	 * "rooms":"1", "expenses":"800", "age":"5", "brand_new":false,
	 * "price":"2000", "currency":"ARS", "balcony":true, "bathrooms":"1",
	 * "dining_room":false, "ensuite_bedroom":false, "storage":false,
	 * "garage":false, "studio":false, "kitchen":true, "service_unit":false,
	 * "hall":false, "frontgarden":false, "backyard":false, "laundry":false,
	 * "lounge":false, "living_room":true, "terrace":false, "mains_water":true,
	 * "drains":true, "cable":true, "gas":true, "internet":true,
	 * "pavement":true, "publication_date":"2014-09-28 00:00:00",
	 * "publication_type":"1", "status":"2", "video":"", "images_url":
	 * "[\"files\\\/96df72290f54511ba31ce5fe4f5e26009ebc4b78.jpg\",\"files\\\/4b26605a1e2ac0b4dc968b64d9425f166c4525bc.jpg\"]",
	 * "created":"2014-09-28 19:46:14", "updated":"2014-09-28 19:46:14",
	 * "operation_type_id":"1", "property_type_id":"4", "user_id":"5",
	 * "end_date":"2014-10-28 00:00:00", "description":
	 * "Departamento de Categor\u00eda, orientaci\u00f3n Norte. Excelente zona. 2 cuadras subte A, 3 cuadras de subte B. "
	 * , "availability":"", "address":"Av Diaz Velez 3790",
	 * "neighborhood":"Almagro", "operation_type":"Alquiler",
	 * "property_type":"Departamento"} , "User":{ "name":"Liz Smocovich",
	 * "username":"liz.smocovich@gmail.com", "tel_part":"48653118",
	 * "mobile":"1130672233"} }
	 */

	public String getVideoUrl() {
		return publication.optString("video", "");
	}

public LatLng getCoords(){
	return new LatLng(Double.parseDouble(publication.optString("lat", "0.0")),Double.parseDouble(publication.optString("lng", "0.0")));
}
	public String getVideoCode() {
		URI uri;
		try {
			uri = new URI(getVideoUrl());
			String query = uri.getRawQuery();
			return (query!= null)?query.replace("v=", ""):"";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return "";
	}

	public String getNeighborhoodId() {
		return publication.optString("neighborhood_id");
	}

	public String getCoveredArea() {
		return publication.optString("covered_area");
	}

	@Override
	public String getTotalArea() {
		return publication.optString("total_area");
	}

	@Override
	public String getRooms() {
		return publication.optString("rooms");
	}

	public String getExpenses() {
		return publication.optString("expenses");
	}

	public String getAge() {
		return publication.optString("age");
	}

	public Boolean isBrandNew() {
		return publication.optBoolean("brand_new");
	}

	
	public String getCurrency() {
		return publication.optString("currency");
	}
	public String getPublicationDescription() {
		return publication.optString("description");
	}

	public Boolean hasBalcony() {
		return publication.optBoolean("balcony");
	}

	public String getBathrooms() {
		return publication.optString("bathrooms");
	}

	public Boolean hasDiningRoom() {
		return publication.optBoolean("dining_room");
	}

	public Boolean hasEnsuiteBedroom() {
		return publication.optBoolean("ensuite_bedroom");
	}

	public Boolean hasStorage() {
		return publication.optBoolean("storage");
	}

	public Boolean hasGarage() {
		return publication.optBoolean("garage");
	}

	public Boolean hasStudio() {
		return publication.optBoolean("studio");
	}

	public Boolean hasKitchen() {
		return publication.optBoolean("kitchen");
	}

	public Boolean hasServiceUnit() {
		return publication.optBoolean("service_unit");
	}

	public Boolean hasHall() {
		return publication.optBoolean("hall");
	}

	public Boolean hasFrontgarden() {
		return publication.optBoolean("frontgarden");
	}

	public Boolean hasBackyard() {
		return publication.optBoolean("backyard");
	}

	public Boolean hasLaundry() {
		return publication.optBoolean("laundry");
	}

	public Boolean hasLounge() {
		return publication.optBoolean("lounge");
	}

	public Boolean hasLivingRoom() {
		return publication.optBoolean("living_room");
	}

	public Boolean hasTerrace() {
		return publication.optBoolean("terrace");
	}

	public Boolean hasMainsWater() {
		return publication.optBoolean("mains_water");
	}

	public Boolean hasDrains() {
		return publication.optBoolean("drains");
	}

	public Boolean hasCable() {
		return publication.optBoolean("cable");
	}

	public Boolean hasGas() {
		return publication.optBoolean("gas");
	}

	public Boolean hasInternet() {
		return publication.optBoolean("internet");
	}

	public Boolean hasPavement() {
		return publication.optBoolean("pavement");
	}

	public String getPublicationDate() {
		return publication.optString("publication_date");
	}

	public String getPublicationType() {
		return publication.optString("publication_type");
	}

	public String getStatus() {
		return publication.optString("status");
	}

	public String getVideo() {
		return publication.optString("video");
	}

	public String getCreated() {
		return publication.optString("created");
	}

	public String getUpdated() {
		return publication.optString("updated");
	}

	public String getOperationTypeId() {
		return publication.optString("operation_type_id");
	}

	public String getPropertyTypeId() {
		return publication.optString("property_type_id");
	}

	public String getUserId() {
		return publication.optString("user_id");
	}

	public String getEndDate() {
		return publication.optString("end_date");
	}

	@Override
	public String getDescription() {
		return publication.optString("description");
	}

	public String getAvailability() {
		return publication.optString("availability");
	}

	@Override
	public String getNeighborhood() {
		return publication.optString("neighborhood");
	}

	public String getOperationType() {
		return publication.optString("operation_type");
	}

	public String getPropertyType() {
		return publication.optString("property_type");
	}

	

	public String getContactName() {

		return contact.optString("name");
	}

	public String getHomePhone() {

		return contact.optString("tel_part");
	}

	public String getMobilePhone() {

		return contact.optString("mobile");
	}

	public String getContactEmail() {
		return contact.optString("username");
	}

	public List<String> getImagesUrl() {
		String imgs = publication.optString("images_url");
		List<String> images = new ArrayList<String>();
		JSONArray ja = null;
		try {
			ja = new JSONArray(imgs);
		} catch (JSONException e) {
		}

		if (ja != null && ja.length() > 0) {
			for (int i = 0; i < ja.length(); i++) {
				String currentImage = Config.SITEBASEURL + ja.optString(i);
				images.add(currentImage);
			}
		}
		return images;
	}

}

package ar.uba.fi.mileem.models;

import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class PublicationFullResult extends PublicationResult {

	public PublicationFullResult(JSONObject jo) {
		super(jo);
	}
	
	/*
	 * 
	 * {
"Publication":{"id":"1",
				"street":"Av Diaz Velez",
				"st_number":"3790",
				"neighborhood_id":"2",
				"covered_area":"30",
				"total_area":"35",
				"rooms":"1",
				"expenses":"800",
				"age":"5",
				"brand_new":false,
				"price":"2000",
				"currency":"ARS",
				"balcony":true,
				"bathrooms":"1",
				"dining_room":false,
				"ensuite_bedroom":false,
				"storage":false,
				"garage":false,
				"studio":false,
				"kitchen":true,
				"service_unit":false,
				"hall":false,
				"frontgarden":false,
				"backyard":false,
				"laundry":false,
				"lounge":false,
				"living_room":true,
				"terrace":false,
				"mains_water":true,
				"drains":true,
				"cable":true,
				"gas":true,
				"internet":true,
				"pavement":true,
				"publication_date":"2014-09-28 00:00:00",
				"publication_type":"1",
				"status":"2",
				"video":"",
				"images_url":"[\"files\\\/96df72290f54511ba31ce5fe4f5e26009ebc4b78.jpg\",\"files\\\/4b26605a1e2ac0b4dc968b64d9425f166c4525bc.jpg\"]",
				"created":"2014-09-28 19:46:14",
				"updated":"2014-09-28 19:46:14",
				"operation_type_id":"1",
				"property_type_id":"4",
				"user_id":"5",
				"end_date":"2014-10-28 00:00:00",
				"description":"Departamento de Categor\u00eda, orientaci\u00f3n Norte. Excelente zona. 2 cuadras subte A, 3 cuadras de subte B. ",
				"availability":"",
				"address":"Av Diaz Velez 3790",
				"neighborhood":"Almagro",
				"operation_type":"Alquiler",
				"property_type":"Departamento"}
,
"User":{	"name":"Liz Smocovich",
			"username":"liz.smocovich@gmail.com",
			"tel_part":"48653118",
			"mobile":"1130672233"}
}

	 * */
	
			public LatLng getCoords(){
				return new LatLng(-34.6204561,-58.365235);
			}
	
	

}

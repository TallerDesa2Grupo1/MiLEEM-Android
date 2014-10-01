package ar.uba.fi.mileem.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import com.loopj.android.http.RequestParams;

public class SearchForm {
	private static FormField[] requireds = {FormField.NEIGHBORHOOD ,FormField.SURROUNDING_AREAS,FormField.PROPERTY_TYPE, FormField.OPERATION_TYPE };
	private static FormField[] optionals = {FormField.EXCHANGE ,FormField.PRICE ,FormField.COVERED_AREA,FormField.TOTAL_AREA ,FormField.BRAND_NEW ,FormField.OLD, FormField.EXPENSE };
	private static FormField[][] fields_by_category = {{},{FormField.ROOMS,FormField.BATHROOM, FormField.SUITE_ROOM, FormField.GARAGE},{FormField.ROOMS,FormField.BATHROOM, FormField.SUITE_ROOM, FormField.GARAGE},{}}; 
	
	private static HashMap<FormField, Object> form =  getDefaults();
	
	public static Object getField(FormField field){
		return form.get(field);
	}
	
	public static void setField(FormField field,Object value){
		form.put(field, value);
	}
	
	public static void setField(FormField field,int value){
		form.put(field,Integer.valueOf(value));
	}

	public static void removeField(FormField field){
		form.remove(field);
	}

	public static void cleanInvalidFields(boolean is_advanced){
		 int category = Integer.parseInt(getSelectedCategory());
		 Collection<FormField> collection = new ArrayList<FormField>();
		 collection.addAll(Arrays.asList(requireds));
		 if(is_advanced){
			 collection.addAll(Arrays.asList(optionals));
			 collection.addAll(Arrays.asList(fields_by_category[category]));
		 }
		 Object[] keys = form.keySet().toArray();
		 for (int i = 0; i < keys.length; i++) {
			 if(!collection.contains(keys[i])){
					form.remove(keys[i]);
				}
		}
	}
	
	public static RequestParams getAsRequestParams(){
		RequestParams rp = new RequestParams();
		for (FormField key : form.keySet()) {
			rp.put(key.toString(),form.get(key));
		}
		return rp;
	}
	
	public static void clearForm(){
		form.clear();
	}
	
	private static HashMap<FormField, Object> getDefaults(){
		 HashMap<FormField, Object> tmp = new HashMap<FormField, Object>();
		 tmp.put(FormField.SURROUNDING_AREAS, false);
		 return tmp;
	}
	
	public static String getSelectedCategory() {
		String property_type = (String) getField(FormField.PROPERTY_TYPE);
		switch (Integer.parseInt(property_type)) {
		case 1:
		case 2:
		case 3:
			return "1";
		case 4:
		case 5:
		case 6:
		case 7:
			return "2";
		default:
			return "3";
		}
	}
	
	
   
	
}

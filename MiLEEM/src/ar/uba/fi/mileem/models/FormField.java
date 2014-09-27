package ar.uba.fi.mileem.models;

import java.util.HashMap;
import java.util.Map;

public enum FormField {
    OPERATION_TYPE("q[operation_type_eq]"),
    PROPERTY_TYPE("q[property_property_type_id_eq]"),
    NEIGHBORHOOD("q[location_id_eq]"),
    SURROUNDING_AREAS("con_zonas"),
    PRICE("precio"),
    EXCHANGE("moneda"),
    TOTAL_AREA("sup_total"),
    COVERED_AREA("sup_cubierta"),
    ROOMS("ambientes"),
    EXPENSE("expensas"),
    OLD("antiguedad"),
    BRAND_NEW("a_estrenar"),
    BATHROOM("banos"),
    SUITE_ROOM("en_suite"),
    GARAGE("con_cochera"),
    SORT("q[s]"),
    OFFSET("offset"),
    TIMESTAMP("timestamp"),
    ;
    

    private final String text;

    private static final Map<String, FormField> STRING_TO_ENUM = new HashMap<String, FormField>();

    static {
        for (FormField field : FormField.values()) {
            STRING_TO_ENUM.put(field.text, field);
        }
    }
    
    private FormField(final String text) {
        this.text = text;
    }
   
    public String toString() {
    	
        return text;
    }
    
    public static FormField getByName(String name){
    	  return STRING_TO_ENUM.get(name);
    }
    
}
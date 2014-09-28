package ar.uba.fi.mileem.models;

import java.util.HashMap;
import java.util.Map;

public enum SortFilter {
    PRICE_ASC("scaled_price"),
    PRICE_DESC("scaled_price"),
    HIGHLIGHTED("publication_type"),
    PUBLICATION_DATE_DESC("publication_date"),
    PUBLICATION_DATE_ASC("publication_date"),
    ;   

    private final String text;

    private static final Map<String, SortFilter> STRING_TO_ENUM = new HashMap<String, SortFilter>();

    static {
        for (SortFilter field : SortFilter.values()) {
            STRING_TO_ENUM.put(field.text, field);
        }
    }
    
    private SortFilter(final String text) {
        this.text = text;
    }
   
    public String toString() {
    	
        return text;
    }
    
    public static SortFilter getByName(String name){
    	  return STRING_TO_ENUM.get(name);
    }
    
}
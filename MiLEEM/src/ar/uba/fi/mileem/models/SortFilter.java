package ar.uba.fi.mileem.models;

import java.util.HashMap;
import java.util.Map;

public enum SortFilter {
    PRICE_ASC("price_asc"),
    PRICE_DESC("price_desc"),
    HIGHLIGHTED("highlighted"),
    PUBLICATION_DATE_DESC("publication_date_desc"),
   
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
package ar.uba.fi.mileem.models;

import java.util.HashMap;
import java.util.Map;

public enum SortFilter {
    PRICE_ASC("price asc"),
    PRICE_DESC("price desc"),
    HIGHLIGHTED("highlighted"),
    PUBLICATION_DATE_DESC("start_date desc"),
    PUBLICATION_DATE_ASC("start_date asc"),
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
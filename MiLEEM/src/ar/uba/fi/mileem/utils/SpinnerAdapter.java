package ar.uba.fi.mileem.utils;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.uba.fi.mileem.R;

public class SpinnerAdapter extends ArrayAdapter<SimpleEntry<String, String>>{
	
	public SpinnerAdapter(Context context,  ArrayList<SimpleEntry<String, String>> options) {
	        super(context,  android.R.layout.simple_spinner_item,  options);
	}
	
	public SpinnerAdapter(Context context) {
        super(context,  android.R.layout.simple_spinner_item, ( new  ArrayList<SimpleEntry<String, String>>() ));
	}


    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
    	if(convertView == null){
      	  	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.row, parent, false);
    	}
        TextView label=(TextView)convertView.findViewById(R.id.opcion);
        label.setText(getItem(position).getValue());
        return convertView;
    }
    
    public int getPosition(SimpleEntry<String, String> item) {
    	for (int i = 0; i < getCount(); i++) {
    		SimpleEntry<String, String> tmp = getItem(i);
    		if(item.getKey().equals(tmp.getKey())){
    			return i;
    		}
		}
    	return -1;
    }
	
    
}
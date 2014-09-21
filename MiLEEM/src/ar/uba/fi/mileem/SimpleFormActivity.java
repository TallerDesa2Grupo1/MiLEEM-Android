package ar.uba.fi.mileem;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import ar.uba.fi.mileem.models.FormField;
import ar.uba.fi.mileem.models.SearchForm;
import ar.uba.fi.mileem.utils.ApiHelper;
import ar.uba.fi.mileem.utils.JsonCacheHttpResponseHandler;
import ar.uba.fi.mileem.utils.OperationTypeSpinnerAdapter;
import ar.uba.fi.mileem.utils.SpinnerAdapter;
import ar.uba.fi.mileem.R;

public class SimpleFormActivity extends Activity {
	
	Spinner neighborhoodsSpinner = null;
	Spinner operationtypeSpinner = null;
	Spinner propertyTypeSpinner = null;
	CheckBox includeNeighborsCheck = null;
	Button btnSearch  = null;
	Button btnAdvancedSearch  = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_form);
		neighborhoodsSpinner = (Spinner) findViewById(R.id.neighborhood);
		operationtypeSpinner = (Spinner) findViewById(R.id.operation_type);
		propertyTypeSpinner = (Spinner) findViewById(R.id.property_type);
		includeNeighborsCheck = (CheckBox) findViewById(R.id.include_neighbors);
		btnSearch =  (Button) findViewById(R.id.simple_search);
		btnAdvancedSearch =  (Button) findViewById(R.id.advanced_search);
		
		setListeners();
	}
	
	
	protected void onResume() {
		super.onResume();
		neighborhoodsSpinner.setEnabled(false);
		propertyTypeSpinner.setEnabled(false);
		btnSearch.setEnabled(false);
		btnAdvancedSearch.setEnabled(false);
		initForm();
	}
	
	@SuppressWarnings("unchecked")
	private void initForm(){
		
		operationtypeSpinner.setAdapter(new OperationTypeSpinnerAdapter(this));
		
		ApiHelper.getInstance().getNeighborhoodsByCity(new JsonCacheHttpResponseHandler(){
			public void onSuccess(int statusCode, Header[] headers,	JSONArray response) {
				super.onSuccess(statusCode, headers, response);
				 ArrayAdapter<SimpleEntry<String, String>> adapter = new SpinnerAdapter(SimpleFormActivity.this,genericJSONArrayToEntryList(response));
				 neighborhoodsSpinner.setAdapter(adapter);
				 neighborhoodsSpinner.setEnabled(true);
				 enableSearch();
				 if(SearchForm.getField(FormField.NEIGHBORHOOD) != null ){
						int position =adapter.getPosition(new SimpleEntry<String, String>((String)SearchForm.getField(FormField.NEIGHBORHOOD),null));
						neighborhoodsSpinner.setSelection(position);
				 }
			}
		});
		
		ApiHelper.getInstance().getPropertyTypes(new JsonCacheHttpResponseHandler(){
			public void onSuccess(int statusCode, Header[] headers,	JSONArray response) {
				super.onSuccess(statusCode, headers, response);
				 ArrayAdapter<SimpleEntry<String, String>> adapter = new SpinnerAdapter(SimpleFormActivity.this,genericJSONArrayToEntryList(response));
				 propertyTypeSpinner.setAdapter(adapter);
				 propertyTypeSpinner.setEnabled(true);
				 enableSearch();	
				 if(SearchForm.getField(FormField.PROPERTY_TYPE) != null ){
						int position =adapter.getPosition(new SimpleEntry<String, String>((String)SearchForm.getField(FormField.PROPERTY_TYPE),null));
						propertyTypeSpinner.setSelection(position);
				 }
			}
		});
		
		ArrayAdapter<SimpleEntry<String, String>>  adapter = (ArrayAdapter<SimpleEntry<String, String>>)operationtypeSpinner.getAdapter();
		if(SearchForm.getField(FormField.OPERATION_TYPE) != null ){
			int position =adapter.getPosition(new SimpleEntry<String, String>((String)SearchForm.getField(FormField.OPERATION_TYPE),null));
			operationtypeSpinner.setSelection(position);
		}
		
		includeNeighborsCheck.setChecked((Boolean)SearchForm.getField(FormField.SURROUNDING_AREAS));
	}
	
	private void setListeners(){
		
		btnSearch.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(SimpleFormActivity.this,SearchActivity.class);
				SimpleFormActivity.this.startActivity(i);
			}
		});
		btnAdvancedSearch.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(SimpleFormActivity.this,AdvancedFormActivity.class);
				SimpleFormActivity.this.startActivity(i);
			}
		});
		
		operationtypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
            	Object item = operationtypeSpinner.getSelectedItem();
            	if( item instanceof SimpleEntry<?, ?>){
            		Object value = (( SimpleEntry<?, ?>)item).getKey();
            		SearchForm.setField(FormField.OPERATION_TYPE,value);
            	}

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
		
		propertyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
            	Object item = propertyTypeSpinner.getSelectedItem();
            	if( item instanceof SimpleEntry<?, ?>){
            		Object value = (( SimpleEntry<?, ?>)item).getKey();
            		SearchForm.setField(FormField.PROPERTY_TYPE, value);
            	}

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
		
		includeNeighborsCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				SearchForm.setField(FormField.SURROUNDING_AREAS, checked);		
			}
		});
		
	}
	
	private ArrayList<SimpleEntry<String, String>> genericJSONArrayToEntryList(JSONArray ja){
		  ArrayList<SimpleEntry<String, String>> options =  new ArrayList<SimpleEntry<String, String>>();
		  for (int i = 0; i < ja.length(); i++) {
			try {
				JSONObject jo = ja.getJSONObject(i);
				options.add(new SimpleEntry<String, String>(jo.getString("id"),jo.getString("name")));

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		 return options;
	}

	
	private  void enableSearch(){
		 btnSearch.setEnabled(neighborhoodsSpinner.isEnabled() && propertyTypeSpinner.isEnabled() && operationtypeSpinner.isEnabled());
		 btnAdvancedSearch.setEnabled(btnSearch.isEnabled());
	}

}

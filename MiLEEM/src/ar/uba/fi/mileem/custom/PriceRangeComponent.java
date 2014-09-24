package ar.uba.fi.mileem.custom;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import ar.uba.fi.mileem.models.FormField;
import ar.uba.fi.mileem.R;

public class PriceRangeComponent extends CustomFormComponentBase {
	EditText from = null;
	EditText to = null;
	
	public PriceRangeComponent(Context context) {
		this(context,null);
	}

	public PriceRangeComponent(Context context, AttributeSet attrs) {
		super(context,attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.rango_precios_layout,getContainer(),true);
		if(!isInEditMode())
			initComponents();
	}
	
	
	@Override
	public void onEnabledChange(boolean enabled) {
		from.setEnabled(enabled);
		to.setEnabled(enabled);
	}

	@Override
	public Object getValue() {
		ArrayList<String> precios =  new ArrayList<String>();
		precios.add(from.getText().toString());
		precios.add(to.getText().toString());
		return precios;
	}
	
	
	@Override
	public void setValue(Object value) {
		@SuppressWarnings("unchecked")
		ArrayList<String> precios =  (ArrayList<String>) value;
		from.setText(precios.get(0));
		to.setText(precios.get(1));
	}
	
	private void initComponents(){
		setName(FormField.PRICE);
		setLabel(getContext().getString(R.string.precio));
		from = (EditText) getContainer().findViewById(R.id.desde);
		to = (EditText) getContainer().findViewById(R.id.hasta);
		from.addTextChangedListener(textWatcher);
		to.addTextChangedListener(textWatcher);
	}
	
	private final TextWatcher textWatcher = new TextWatcher() {
		
		public void onTextChanged(CharSequence s, int start, int before, int count) {				
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
		}
		
		public void afterTextChanged(Editable s) {
			onInputValueChange();
		}
	}; 
	
	@Override
	public void saveInputValue(Editor e) {
		e.putString(getName().toString(), from.getText()+","+to.getText());
		e.putBoolean(getName().toString()+"_check", isEnabled() );
	}

	@Override
	public void restoreValue(SharedPreferences sharedpreferences) {
		String value = sharedpreferences.getString(getName().toString(), ",");
		String[] values = value.split(",");
		if(values.length == 2){
			from.setText(values[0]);
			to.setText(values[1]);
		}
		setChecked(sharedpreferences.getBoolean(getName().toString()+"_check", true));
	}

}

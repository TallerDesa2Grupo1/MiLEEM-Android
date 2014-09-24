package ar.uba.fi.mileem.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import ar.uba.fi.mileem.R;
import ar.uba.fi.mileem.models.FormField;

public class ExchangeComponent extends CustomFormComponentBase {

	RadioButton peso = null;
	RadioButton dollar = null;
	
	public ExchangeComponent(Context context, AttributeSet attrs) {
		super(context,attrs);
		setName(FormField.EXCHANGE);
		if(!isInEditMode())
			initComponents(context);
	}
	public ExchangeComponent(Context context){
		this(context,null);
	}
	
	@Override
	public void onEnabledChange(boolean enabled) {
		peso.setEnabled(enabled);
		dollar.setEnabled(enabled);
	}

	@Override
	public Object getValue() {
		return (peso.isChecked())?"ARS":"USD";
	}
	
	
	@Override
	public void setValue(Object value) {
		if(value.equals("ARS")){
			peso.setChecked(true);
		}else {
			dollar.setChecked(true);
		}
	}
	
	private void initComponents(Context context){
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.exchange_layout,getContainer(),true);
		setLabel(getContext().getString(R.string.moneda));
		peso = (RadioButton) getContainer().findViewById(R.id.peso);
		dollar = (RadioButton) getContainer().findViewById(R.id.dolar);
		RadioGroup group = (RadioGroup) getContainer().findViewById(R.id.exchange_group);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				onInputValueChange();
			}
		});
	}

	@Override
	public void saveInputValue(Editor e) {
		e.putString(getName().toString(), (String)getValue());
		e.putBoolean(getName().toString()+"_check", isEnabled() );
	}

	@Override
	public void restoreValue(SharedPreferences sharedpreferences) {
		setValue(sharedpreferences.getString(getName().toString(), "ARS"));
		setChecked(sharedpreferences.getBoolean(getName().toString()+"_check", true));
	}
	
}

package ar.uba.fi.mileem.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import ar.uba.fi.mileem.models.FormField;
import ar.uba.fi.mileem.R;

public class ExchangeComponent extends CustomFormComponentBase {

	RadioButton peso = null;
	RadioButton dollar = null;
	
	public ExchangeComponent(Context context, AttributeSet attrs) {
		super(context,attrs);
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
	
	
	private void initComponents(Context context){
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.monedas_layout,getContainer(),true);
		setName(FormField.EXCHANGE);
		setLabel(getContext().getString(R.string.moneda));
		peso = (RadioButton) getContainer().findViewById(R.id.peso);
		dollar = (RadioButton) getContainer().findViewById(R.id.dolar);
	}

	
}

package ar.uba.fi.mileem.custom;

import java.util.ArrayList;

import android.content.Context;
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
	
	
	private void initComponents(){
		setName(FormField.PRICE);
		setLabel(getContext().getString(R.string.precio));
		from = (EditText) getContainer().findViewById(R.id.desde);
		to = (EditText) getContainer().findViewById(R.id.hasta);
	}
	

}

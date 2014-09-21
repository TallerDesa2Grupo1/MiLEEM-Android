package ar.uba.fi.mileem.custom;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Switch;
import ar.uba.fi.mileem.models.FormField;
import ar.uba.fi.mileem.R;

public class BooleanComponent extends CustomFormComponentBase {
	Switch input = null;
	
	public BooleanComponent(Context context) {
		this(context,null);
	}

	public BooleanComponent(Context context, AttributeSet attrs) {
		super(context,attrs);
		if(!isInEditMode())
			initComponents(context , attrs);
	}
	
	
	@Override
	public void onEnabledChange(boolean enabled) {
		input.setEnabled(enabled);
	}

	@Override
	public Object getValue() {
		return input.isChecked();
	}
	
	
	private void initComponents(Context context,AttributeSet attrs){
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.switch_layout,getContainer(),true);
		input = (Switch)getContainer().findViewById(R.id.switch_id);
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BooleanComponent);
		setName(FormField.getByName(a.getString(R.styleable.BooleanComponent_name)));
		setLabel(a.getString(R.styleable.BooleanComponent_label));
		a.recycle();
	
	}

}

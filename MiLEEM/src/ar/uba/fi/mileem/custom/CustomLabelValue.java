package ar.uba.fi.mileem.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.uba.fi.mileem.R;

public class CustomLabelValue extends LinearLayout {

	private TextView label = null;
	private TextView value = null;

	
	public CustomLabelValue(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.detail_item, this, true);
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		if(!isInEditMode())
			initComponent(context,attrs);
	}

	public CustomLabelValue(Context context) {
		this(context, null);
	}
	
	private void initComponent(Context context, AttributeSet attrs) {

		label = (TextView) findViewById(R.id.label);
		value = (TextView) findViewById(R.id.value);

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.CustomLabelValue);
		String slabel = a.getString(R.styleable.CustomLabelValue_label);
		label.setText(((slabel!=null)?slabel:"")+":");
		value.setText("-");
		a.recycle();
	}
	
	public void setValue(CharSequence v){
		value.setText(v);
	}
}

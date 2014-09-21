package ar.uba.fi.mileem.custom;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.uba.fi.mileem.R;

public  class CustomComponentsGroup extends LinearLayout{

	private CheckBox checkAll = null;
	private LinearLayout container = null;
	private  TextView title = null;
	
	public CustomComponentsGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.custom_component_group, this, true);
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER_VERTICAL);
	   if(!isInEditMode())
		   initComponent(context,attrs);
	}
	
	private void initComponent(Context context,AttributeSet attrs){
		
		 title = (TextView) findViewById(R.id.titulo);
		    container = this;
		    checkAll = (CheckBox) findViewById(R.id.check_all);
		    
		    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomComponentsGroup);
			title.setText(a.getString(R.styleable.CustomComponentsGroup_label));
		    a.recycle();
		    checkAll.setChecked(true);
		    checkAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					stateChange(isChecked);
				}
			});
	}
	
	
	public CustomComponentsGroup(Context context) {
	    this(context, null);
	  }
	
	private LinearLayout getContainer(){
		return container;
	}
	
	private void stateChange(boolean isChecked){
		int children =  getContainer().getChildCount();
		for (int i = 1; i < children; i++) {
			View v = getContainer().getChildAt(i);
			((CustomFormComponentBase)v).setChecked(isChecked);
		}
		
	}
	
	public boolean isEnabled(){
		return checkAll.isChecked();
	}
	

	
	

}

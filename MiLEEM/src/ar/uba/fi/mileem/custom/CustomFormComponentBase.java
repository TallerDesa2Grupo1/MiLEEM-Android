package ar.uba.fi.mileem.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.uba.fi.mileem.models.FormField;
import ar.uba.fi.mileem.R;

public abstract class CustomFormComponentBase extends LinearLayout{

	private CheckBox stateCheck = null;
	private FormField name = null;
	private LinearLayout container = null;
	private  TextView label = null;
	
	public CustomFormComponentBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.custom_form_component, this, true);
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
	   if(!isInEditMode())
		   initComponent(context);
	}
	
	private void initComponent(Context context){
		
		 label = (TextView) findViewById(R.id.label);
		    container = (LinearLayout)findViewById(R.id.container);
		    stateCheck = (CheckBox) findViewById(R.id.state);
		    
		    stateCheck.setChecked(true);
		    stateCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					stateChange(isChecked);
					CustomFormComponentBase.this.onEnabledChange(isChecked);
				}
			});
	}
	
	
	public CustomFormComponentBase(Context context) {
	    this(context, null);
	  }
	
	protected LinearLayout getContainer(){
		return container;
	}
	private void stateChange(boolean isChecked){
		label.setTextColor((isChecked)?getResources().getColor(R.color.apptheme_color):getResources().getColor(R.color.disabled));
	}
	
	public void setChecked(boolean isChecked){
		 stateCheck.setChecked(isChecked);
	}
	
	public abstract void onEnabledChange(boolean enabled);
	
	public  Object getValue(){
		return null;
	};
	
	public void setName(FormField ff){
		this.name = ff;
	}
	
	public String getName(){
		return (name != null)?name.toString():"";
	}

	public boolean isEnabled(){
		return stateCheck.isChecked();
	}
	
	public void setLabel(String labelText){
		 label.setText(labelText);
	}
	
	

}

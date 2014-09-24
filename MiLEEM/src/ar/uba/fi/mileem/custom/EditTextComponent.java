package ar.uba.fi.mileem.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import ar.uba.fi.mileem.models.FormField;
import ar.uba.fi.mileem.R;

public class EditTextComponent extends CustomFormComponentBase {
	EditText input = null;
	
	public EditTextComponent(Context context) {
		this(context,null);
	}

	public EditTextComponent(Context context, AttributeSet attrs) {
		super(context,attrs);
		input = new CustomEditText(context);
		
		if(!isInEditMode())
			initComponents(attrs);
	}
	
	
	@Override
	public void onEnabledChange(boolean enabled) {
		input.setEnabled(enabled);
	}

	@Override
	public Object getValue() {
		return input.getText().toString();
	}
	
	
	public void setValue(Object value) {
		input.setText((String)value);		
	}
	
	private void initComponents(AttributeSet attrs){
		getContainer().addView(input);
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EditTextComponent);
		setName(FormField.getByName(a.getString(R.styleable.EditTextComponent_name)));
		setLabel(a.getString(R.styleable.EditTextComponent_label));
		input.setHint(a.getString(R.styleable.EditTextComponent_hint));
		input.setInputType(a.getInt(R.styleable.EditTextComponent_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL));
		a.recycle();
		input.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			}
			
			public void afterTextChanged(Editable s) {
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
		setValue(sharedpreferences.getString(getName().toString(), ""));
		setChecked(sharedpreferences.getBoolean(getName().toString()+"_check", true));
	}

}

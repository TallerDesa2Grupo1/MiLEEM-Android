package ar.uba.fi.mileem.custom;

import android.content.Context;
import android.content.res.TypedArray;
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
		input = new EditText(context);
		
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
	
	
	private void initComponents(AttributeSet attrs){
		getContainer().addView(input);
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EditTextComponent);
		setName(FormField.getByName(a.getString(R.styleable.EditTextComponent_name)));
		setLabel(a.getString(R.styleable.EditTextComponent_label));
		input.setHint(a.getString(R.styleable.EditTextComponent_hint));
		input.setInputType(a.getInt(R.styleable.EditTextComponent_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL));
		a.recycle();
	}

}

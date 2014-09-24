package ar.uba.fi.mileem.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import ar.uba.fi.mileem.models.SearchForm;

public class CustomComponentsGroup extends LinearLayout {

	private CheckBox checkAll = null;
	private LinearLayout container = null;
	private TextView title = null;

	public CustomComponentsGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.custom_component_group, this, true);
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER_VERTICAL);
		if (!isInEditMode())
			initComponent(context, attrs);
	}

	private void initComponent(Context context, AttributeSet attrs) {

		title = (TextView) findViewById(R.id.titulo);
		container = this;
		checkAll = (CheckBox) findViewById(R.id.check_all);

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.CustomComponentsGroup);
		title.setText(a.getString(R.styleable.CustomComponentsGroup_label));
		if (a.hasValue(R.styleable.CustomComponentsGroup_categories)) {
			String cs = a
					.getString(R.styleable.CustomComponentsGroup_categories);
			evaluateVisibility(cs);
		} else {
			evaluateVisibility(null);
		}

		a.recycle();
		checkAll.setChecked(true);
		checkAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				stateChange(isChecked);
			}
		});
	}

	public CustomComponentsGroup(Context context) {
		this(context, null);
	}

	private LinearLayout getContainer() {
		return container;
	}

	private void stateChange(boolean isChecked) {
		int children = getContainer().getChildCount();
		for (int i = 1; i < children; i++) {
			View v = getContainer().getChildAt(i);
			((CustomFormComponentBase) v).setChecked(isChecked);
		}

	}

	public boolean isEnabled() {
		return checkAll.isChecked();
	}

	private void evaluateVisibility(String categories) {
		boolean visible = true;
		if (categories != null) {
			String[] categories_array = categories.split(",");
			boolean match = false;
			String category = SearchForm.getSelectedCategory();
			for (int i = 0; i < categories_array.length; i++) {
				if (categories_array[i].equals(category)) {
					match = true;
					break;
				}
			}
			visible = match;
		}
		setVisibility((visible) ? View.VISIBLE : View.GONE);
	}

	
	public final void saveInputsValues() {
		SharedPreferences sharedpreferences = getContext()
				.getSharedPreferences("CustomComponentsGroup_" +getId(),
						Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putBoolean("enabled", isEnabled());
		int children = getContainer().getChildCount();
		for (int i = 1; i < children; i++) {
			View v = getContainer().getChildAt(i);
			((CustomFormComponentBase) v).saveInputValue(editor);
		}
		editor.commit();
	}

	public final void loadInputsValues() {
		SharedPreferences sharedpreferences = getContext()
				.getSharedPreferences("CustomComponentsGroup_" + getId(),
						Context.MODE_PRIVATE);
		if (sharedpreferences.contains("enabled")) {
			checkAll.setChecked(sharedpreferences.getBoolean("enabled", true));
			int children = getContainer().getChildCount();
			for (int i = 1; i < children; i++) {
				View v = getContainer().getChildAt(i);
				((CustomFormComponentBase) v).restoreValue(sharedpreferences);
			}
		}
	}

}

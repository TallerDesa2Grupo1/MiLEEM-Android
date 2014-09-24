package ar.uba.fi.mileem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ar.uba.fi.mileem.R;
import ar.uba.fi.mileem.custom.CustomComponentsGroup;
import ar.uba.fi.mileem.models.SearchForm;
import ar.uba.fi.mileem.utils.ApiHelper;
import ar.uba.fi.mileem.utils.DialogFactory;

public class AdvancedFormActivity extends Activity {
	
	CustomComponentsGroup general_options = null;
	CustomComponentsGroup extra_options = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advanced_search);
		 general_options = (CustomComponentsGroup) findViewById(R.id.general_options);
		 extra_options = (CustomComponentsGroup) findViewById(R.id.extra_options);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		general_options.loadInputsValues();
		extra_options.loadInputsValues();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.advanced_search_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			openSearch();
			return true;
		}
		return false;
	}

	public void openSearch() {
		if(ApiHelper.getInstance().isNetworkAvailable(this)){
			SearchForm.cleanInvalidFields(true);
			Intent i = new Intent(AdvancedFormActivity.this, SearchActivity.class);
			AdvancedFormActivity.this.startActivity(i);
		}else{
			DialogFactory.getFactory().showError(AdvancedFormActivity.this, R.string.oops, R.string.connection_error);
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		general_options.saveInputsValues();
		extra_options.saveInputsValues();
	}

}

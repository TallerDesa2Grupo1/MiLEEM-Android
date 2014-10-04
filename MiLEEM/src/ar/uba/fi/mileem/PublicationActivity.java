package ar.uba.fi.mileem;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import ar.uba.fi.mileem.models.PublicationFullResult;
import ar.uba.fi.mileem.utils.ApiHelper;
import ar.uba.fi.mileem.utils.JsonCacheHttpResponseHandler;
import ar.uba.fi.mileem.utils.TabsPagerAdapter;
import ar.uba.fi.mileem.utils.TypefaceSpan;

public class PublicationActivity extends FragmentActivity  implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Detalles", "Fotos", "Videos","Mapa","Contacto" };
	private String id = "";
	private Object pubLock = new Object();
	private PublicationFullResult publication = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.publication_activity);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		
		// Adding Tabs
		 setTitleAndTabs();
		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		Bundle b = getIntent().getExtras();
		id = b.getString(Config.PUBLICATION_ID);
		findPublicationInfo();
		
	}
	protected void setTitleAndTabs(){
		SpannableString s = new SpannableString(getString(R.string.app_name));
		s.setSpan(new TypefaceSpan(this, "Roboto-Light.ttf"), 0, s.length(),
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		actionBar.setTitle(s);
		for (String tab_name : tabs) {
			 s = new SpannableString(tab_name);
			s.setSpan(new TypefaceSpan(this, "Roboto-Regular.ttf"), 0, s.length(),
			        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			actionBar.addTab(actionBar.newTab().setText(s)
					.setTabListener(this));
		}

	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.publication_view_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_refresh:
			findPublicationInfo();
			return true;
		case R.id.action_share:
			Toast.makeText(this, R.string.compartir, Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_chart:
			Toast.makeText(this, R.string.ver_estadisticas, Toast.LENGTH_SHORT).show();
			return true;
		 case android.R.id.home:
	         NavUtils.navigateUpFromSameTask(this);
	         return true;
		}
		return false;
	}

	
	private void findPublicationInfo(){
		if(getPublication() == null){
			Toast.makeText(this, R.string.cargando, Toast.LENGTH_SHORT).show();
			ApiHelper.getInstance().getPublication(id, new JsonCacheHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					synchronized (pubLock) {
						publication = new PublicationFullResult(response);
					}
					
					notifyFrames();
				}
	
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONArray errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}
			});
		}
	}
	
	
	private void notifyFrames() {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		for (Fragment fragment : fragments) {
			if(fragment instanceof IPublicationDataObserver){
				((IPublicationDataObserver)fragment).onPublicationData();
			}
		}
	}
	
	public PublicationFullResult getPublication() {
		synchronized (pubLock) {
			return publication;
		}
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}


}

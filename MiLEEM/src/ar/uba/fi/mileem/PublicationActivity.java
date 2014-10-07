package ar.uba.fi.mileem;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import ar.uba.fi.mileem.models.PublicationFullResult;
import ar.uba.fi.mileem.utils.ApiHelper;
import ar.uba.fi.mileem.utils.JsonCacheHttpResponseHandler;
import ar.uba.fi.mileem.utils.TabsPagerAdapter;
import ar.uba.fi.mileem.utils.TypefaceSpan;

public class PublicationActivity extends FragmentActivity  {

	private String id = "";
	private Object pubLock = new Object();
	private PublicationFullResult publication = null;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private TabsPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.publication_activity);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new TabsPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);
		tabs.setIndicatorColor(getResources().getColor(R.color.apptheme_color));
		tabs.setTextColor(getResources().getColor(R.color.apptheme_color));
		tabs.setTypeface(TypefaceSpan.getTypeFace(this, "Roboto-Regular.ttf"), Typeface.NORMAL);
		setTitleAndTabs();
		
		tabs.setShouldExpand(true);

		id = null;
		Intent intent = getIntent();
		String action = intent.getAction();
		Uri data = intent.getData();
		if (action != null && data != null) {
			id = data.toString().substring(data.toString().lastIndexOf('/'),
					data.toString().length());
		} else {
			Bundle b = getIntent().getExtras();
			id = b.getString(Config.PUBLICATION_ID);

		}
		if (id == null) {
			Toast.makeText(this, "Publicacion Inválida", Toast.LENGTH_LONG)
					.show();
			finish();
		} else {
			findPublicationInfo();
		}

	}

	protected void setTitleAndTabs() {
		SpannableString s = new SpannableString(getString(R.string.app_name));
		s.setSpan(new TypefaceSpan(this, "Roboto-Light.ttf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(s);
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
			Toast.makeText(this, R.string.cargando, Toast.LENGTH_SHORT).show();
			findPublicationInfo();
			return true;
		case R.id.action_share:
			shareTextUrl();
			return true;
		case R.id.action_chart:
			Toast.makeText(this, R.string.ver_estadisticas, Toast.LENGTH_SHORT)
					.show();
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return false;
	}

	private void shareTextUrl() {
		PublicationFullResult p = getPublication();
		if (p != null) {
			String text = p.getPropertyType() + " | " + p.getOperationType()
					+ " | " + p.getAddress() + " " + p.getDescription();
			String link = Config.PUBLIC_VIEW_URL + p.getId();
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text
					+ " " + link);
			startActivity(Intent.createChooser(sharingIntent, "Compartir"));
		}
	}

	private void findPublicationInfo() {
		if (getPublication() == null) {
			ApiHelper.getInstance().getPublication(id,
					new JsonCacheHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							super.onSuccess(statusCode, headers, response);
							synchronized (pubLock) {
								publication = new PublicationFullResult(
										response);
							}

							notifyFrames();
						}

						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONArray errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
						}
					});
		}
	}

	private void notifyFrames() {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		for (Fragment fragment : fragments) {
			if (fragment instanceof IPublicationDataObserver) {
				((IPublicationDataObserver) fragment).onPublicationData();
			}
		}
	}

	public PublicationFullResult getPublication() {
		synchronized (pubLock) {
			return publication;
		}
	}

	private static final int REQ_START_STANDALONE_PLAYER = 1;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_START_STANDALONE_PLAYER
				&& resultCode != RESULT_OK) {
			YouTubeInitializationResult errorReason = YouTubeStandalonePlayer
					.getReturnedInitializationResult(data);
			if (errorReason.isUserRecoverableError()) {
				errorReason.getErrorDialog(this, 0).show();
			} else {
				String errorMessage = String.format(
						getString(R.string.error_player),
						errorReason.toString());
				Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
			}
		}
	}

}

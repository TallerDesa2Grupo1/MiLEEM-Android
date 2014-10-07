package ar.uba.fi.mileem.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import ar.uba.fi.mileem.ContactFragment;
import ar.uba.fi.mileem.DetailsFragment;
import ar.uba.fi.mileem.MapFragment;
import ar.uba.fi.mileem.PicturesFragment;
import ar.uba.fi.mileem.VideosFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private String[] tabs_tags = { "Detalles", "Fotos", "Videos","Mapa","Contacto" };
	FragmentManager fm = null;
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
	}

	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Games fragment activity
			return new DetailsFragment();
		case 1:
			// Movies fragment activity
			return new PicturesFragment();
		case 2:
			// Top Rated fragment activity
			return new VideosFragment();
		case 3:
			// Top Rated fragment activity
			return new MapFragment();
		case 4:
			// Top Rated fragment activity
			return new ContactFragment();
	
		}

		return null;
	}

	public CharSequence getPageTitle(int position) {
		return tabs_tags[position];
		}
	public int getCount() {
		return tabs_tags.length;
	}
}

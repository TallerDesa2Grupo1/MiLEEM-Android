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

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
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

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}

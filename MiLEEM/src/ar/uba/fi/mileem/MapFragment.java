package ar.uba.fi.mileem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends SupportMapFragment {

	private LatLng mPosFija;

	public MapFragment() {
		super();

	}

	public static MapFragment newInstance(LatLng posicion) {
		MapFragment frag = new MapFragment();
		frag.mPosFija = posicion;
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		View v = super.onCreateView(arg0, arg1, arg2);
		initMap();
		return v;
	}

	private void initMap() {
		UiSettings settings = getMap().getUiSettings();
		settings.setAllGesturesEnabled(true);
		settings.setMyLocationButtonEnabled(false);

		getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(mPosFija, 14));
		getMap().addMarker(
				new MarkerOptions().position(mPosFija));
						
	}
	
	
}
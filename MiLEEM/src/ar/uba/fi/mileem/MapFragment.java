package ar.uba.fi.mileem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.uba.fi.mileem.models.PublicationFullResult;

public class MapFragment extends SupportMapFragment implements IPublicationDataObserver {


	public MapFragment() {
		super();

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
		if(getMap() != null){
			UiSettings settings = getMap().getUiSettings();
			settings.setAllGesturesEnabled(true);
			settings.setMyLocationButtonEnabled(true);
			PublicationActivity a = ((PublicationActivity) getActivity());
			PublicationFullResult p =  a.getPublication();
			if(p!= null){
				getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(p.getCoords(), 14));
				getMap().addMarker(
						new MarkerOptions().title(p.getContactAddress()).position(p.getCoords()));
			}	
		}
	}
	
	public void onPublicationData() {
		initMap();		
	}
	
}
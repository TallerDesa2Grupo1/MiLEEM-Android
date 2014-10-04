package ar.uba.fi.mileem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

public class PicturesFragment extends Fragment {
	private SliderLayout mDemoSlider;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
		mDemoSlider = (SliderLayout)rootView.findViewById(R.id.slider);
		
		TextSliderView textSliderView = new TextSliderView(this.getActivity());
		// initialize a SliderLayout
		textSliderView
		.description("prueba Fufa")
		.image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg")
		.setScaleType(BaseSliderView.ScaleType.CenterCrop);
		//.setOnSliderClickListener(this);
		//add your extra information
		textSliderView.getBundle();
		mDemoSlider.addSlider(textSliderView);
		
		textSliderView = new TextSliderView(this.getActivity());
		// initialize a SliderLayout
		textSliderView
		.description("prueba Fufa")
		.image("http://cdn3.nflximg.net/images/3093/2043093.jpg")
		.setScaleType(BaseSliderView.ScaleType.CenterCrop);
		//.setOnSliderClickListener(this);
		//add your extra information
		textSliderView.getBundle();
		mDemoSlider.addSlider(textSliderView);
		
		Log.e(this.toString(), "oncreateview");
		return rootView;
	}

}

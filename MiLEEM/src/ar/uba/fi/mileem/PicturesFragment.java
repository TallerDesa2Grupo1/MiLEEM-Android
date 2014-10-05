package ar.uba.fi.mileem;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.uba.fi.mileem.models.PublicationFullResult;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

public class PicturesFragment extends Fragment  implements IPublicationDataObserver{
	private SliderLayout mDemoSlider;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
		setViewInfo(rootView);
		Log.e(this.toString(), "oncreateview");
		return rootView;
	}
	
	private void setViewInfo(View v){
		PublicationActivity a = ((PublicationActivity) getActivity());
		PublicationFullResult p =  a.getPublication();
		
		if(p!= null && v != null){
			List<String> images = p.getImagesUrl();
			mDemoSlider = (SliderLayout)v.findViewById(R.id.slider);
			for (String image : images) {
				TextSliderView textSliderView = new TextSliderView(this.getActivity());
				// initialize a SliderLayout
				textSliderView
				//.description("texto para mostrar")
				.image(image)
				.setScaleType(BaseSliderView.ScaleType.CenterCrop);
				//.setOnSliderClickListener(this);
				//add your extra information
				mDemoSlider.addSlider(textSliderView);	
				mDemoSlider.stopAutoCycle();
			}

		}
		
	}
	
	public void onPublicationData() {
		 setViewInfo(getView());		
	}

}

package ar.uba.fi.mileem;

import com.google.android.gms.maps.model.LatLng;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import ar.uba.fi.mileem.utils.ApiHelper;

public class DetailsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_details, container, false);
		ImageView staticMapImage =  (ImageView) rootView.findViewById(R.id.static_map);
		String url = ApiHelper.getInstance().getMapUrl(new LatLng(-34.6204561,-58.365235));
		System.out.println(url);
		 UrlImageViewHelper.setUrlDrawable(staticMapImage,url,R.drawable.placeholder,  new UrlImageViewCallback() {
	          @Override
	          public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
	              if (!loadedFromCache) {
	                  ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
	                  scale.setDuration(300);
	                  scale.setInterpolator(new OvershootInterpolator());
	                  imageView.startAnimation(scale);
	              }
	          }});
	      
		
		return rootView;
	}
	
	
}

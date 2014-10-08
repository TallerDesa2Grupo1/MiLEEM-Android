package ar.uba.fi.mileem;

import java.util.List;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ar.uba.fi.mileem.models.PublicationFullResult;
import ar.uba.fi.mileem.utils.ApiHelper;

public class VideosFragment extends Fragment implements IPublicationDataObserver {

	 private static final int REQ_START_STANDALONE_PLAYER = 1;
	  private static final int REQ_RESOLVE_SERVICE_MISSING = 2;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		View rootView = inflater.inflate(R.layout.fragment_videos, container, false);
		rootView.findViewById(R.id.no_video_text).setVisibility(View.VISIBLE);
		rootView.findViewById(R.id.video_frame).setVisibility(View.GONE);
		((TextView)rootView.findViewById(R.id.no_video_text)).setText(R.string.cargando);
		initVideo(rootView);
		Log.e(this.toString(), "oncreateview");
		return rootView;
	}
	
	 private boolean canResolveIntent(Intent intent) {
		    List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
		    return resolveInfo != null && !resolveInfo.isEmpty();
	 }
	
	 
	 private void initVideo(View v){
			PublicationActivity a = ((PublicationActivity) getActivity());
			PublicationFullResult p =  a.getPublication();
			if(p!= null && v != null){
				String code = p.getVideoCode();
				if(code.equals("")){
					((TextView)v.findViewById(R.id.no_video_text)).setText(R.string.no_video);
					return;
				}
				v.findViewById(R.id.no_video_text).setVisibility(View.GONE);
				v.findViewById(R.id.video_frame).setVisibility(View.VISIBLE);
				ImageButton button = (ImageButton)v.findViewById(R.id.videoButton);
				button.setOnClickListener(listener);
				String url = ApiHelper.getInstance().getVideoThumbnail(code);
				UrlImageViewHelper.setUrlDrawable(button,url,R.drawable.placeholder,  new UrlImageViewCallback() {
			          @Override
			          public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
			              if (!loadedFromCache) {
			                  ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
			                  scale.setDuration(300);
			                  scale.setInterpolator(new OvershootInterpolator());
			                  imageView.startAnimation(scale);
			              }
			          }});
			}
			
	 }
	 
	private final OnClickListener listener =  new  OnClickListener() {
		
		public void onClick(View v) {
			PublicationActivity a = ((PublicationActivity) getActivity());
			PublicationFullResult p =  a.getPublication();
			 Intent intent = YouTubeStandalonePlayer.createVideoIntent(
			          getActivity(), getResources().getString(R.string.api_key),p.getVideoCode(), 0, true, true);

			    if (intent != null) {
			      if (canResolveIntent(intent)) {
			        startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
			      } else {
			        // Could not resolve the intent - must need to install or update the YouTube API service.
			        YouTubeInitializationResult.SERVICE_MISSING.getErrorDialog(getActivity(), REQ_RESOLVE_SERVICE_MISSING).show();
			      }
			    }
			
		}
	};

	public void onPublicationData() {
		initVideo(getView());
	}
}

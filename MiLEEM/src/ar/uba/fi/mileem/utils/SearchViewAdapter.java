package ar.uba.fi.mileem.utils;

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import ar.uba.fi.mileem.R;
import ar.uba.fi.mileem.custom.CustomTextView;
import ar.uba.fi.mileem.models.PublicationResult;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class SearchViewAdapter extends ArrayAdapter<PublicationResult> {
  private final Context context;
  private List<PublicationResult> publications;
  
  public SearchViewAdapter(Context context,List<PublicationResult> objects) {
  	super(context, R.layout.feed_item, objects);
  	this.context = context;
  	this.publications = objects;
  }
  
 static class PublicationsViewHolder {
	CustomTextView txSubTitle;
	CustomTextView txTitle;
	CustomTextView txPrice;
	ImageView imgPublication;
	ImageView bigImgPublication;
 }

  public View getView(int position, View rowView, ViewGroup parent) {
	  final PublicationsViewHolder viewHolder;
      
      if (rowView == null) {
		  	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  	rowView = inflater.inflate(R.layout.feed_item, parent, false);
    	  
			viewHolder = new PublicationsViewHolder();
			viewHolder.txTitle = (CustomTextView) rowView.findViewById(R.id.titulo_item);
			viewHolder.txSubTitle = (CustomTextView) rowView.findViewById(R.id.subtitulo_item);
			viewHolder.txPrice = (CustomTextView) rowView.findViewById(R.id.item_price);
			viewHolder.imgPublication = (ImageView) rowView.findViewById(R.id.item_preview);
			viewHolder.bigImgPublication = (ImageView) rowView.findViewById(R.id.big_image);
			
			rowView.setTag(viewHolder);
      }else{
    	  viewHolder = (PublicationsViewHolder) rowView.getTag();
      }
      
	    PublicationResult publication = publications.get(position);
		if (publication != null) {
			viewHolder.txTitle.setText(publication.toString());
			viewHolder.txSubTitle.setText(publication.getDescription());
			viewHolder.txPrice.setText(publication.getPrice());
		}
		
		if(publication.isHighlighted()){
			viewHolder.bigImgPublication.setVisibility(View.VISIBLE);
			viewHolder.imgPublication.setVisibility(View.GONE);
			 UrlImageViewHelper.setUrlDrawable(viewHolder.bigImgPublication,publication.getMainImage(),R.drawable.placeholder,  new UrlImageViewCallback() {
		          @Override
		          public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
		              if (!loadedFromCache) {
		                  ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
		                  scale.setDuration(300);
		                  scale.setInterpolator(new OvershootInterpolator());
		                  imageView.startAnimation(scale);
		              }
		     
		          }});
		}else{
			viewHolder.bigImgPublication.setVisibility(View.GONE);
			viewHolder.imgPublication.setVisibility(View.VISIBLE);
			 UrlImageViewHelper.setUrlDrawable(viewHolder.imgPublication,publication.getMainImage(),R.drawable.placeholder,  new UrlImageViewCallback() {
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
			
     
      
      //rowView.setBackgroundResource(((publication.isHighlighted())?R.color.highlighted_publication_background:android.R.color.background_light));
   
      return rowView;
    }
  

  public boolean hasStableIds() {
    return true;
  }

} 
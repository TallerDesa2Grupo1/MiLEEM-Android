package ar.uba.fi.mileem;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ar.uba.fi.mileem.custom.CustomCheckBox;
import ar.uba.fi.mileem.custom.CustomLabelValue;
import ar.uba.fi.mileem.models.PublicationFullResult;
import ar.uba.fi.mileem.utils.ApiHelper;
import ar.uba.fi.mileem.utils.TypefaceSpan;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class DetailsFragment extends Fragment implements IPublicationDataObserver {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_details, container, false);
		setViewInfo(rootView);
		return rootView;
	}
	
	
	private void setViewInfo(View v){
		PublicationActivity a = ((PublicationActivity) getActivity());
		PublicationFullResult p =  a.getPublication();
		if(p!= null && v != null){
			ImageView staticMapImage =  (ImageView) v.findViewById(R.id.static_map);
			String url = ApiHelper.getInstance().getMapUrl(p.getCoords());
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
			
			CustomLabelValue item = (CustomLabelValue) v.findViewById(R.id.operation_type);
			item.setValue( p.getOperationType());	
			item = (CustomLabelValue) v.findViewById(R.id.property_type);
			item.setValue( p.getPropertyType());
			item = (CustomLabelValue) v.findViewById(R.id.address);
			item.setValue( p.getPublicationAddress());
			item = (CustomLabelValue) v.findViewById(R.id.neighborhood);
			item.setValue( p.getNeighborhood());
			item = (CustomLabelValue) v.findViewById(R.id.covered_area);
			item.setValue( p.getCoveredArea() + " m2");
			item = (CustomLabelValue) v.findViewById(R.id.total_area);
			item.setValue( p.getTotalArea() + " m2");
			item = (CustomLabelValue) v.findViewById(R.id.rooms);
			if(!p.getRooms().equals("null"))
				item.setValue( p.getRooms() + " Amb.");
			item = (CustomLabelValue) v.findViewById(R.id.expenses);
			if(!p.getExpenses().equals("null"))
				item.setValue( "$ " + p.getExpenses());
			item = (CustomLabelValue) v.findViewById(R.id.age);
			if(!p.getAge().equals("null"))
				item.setValue( p.getAge() + " años");
			item = (CustomLabelValue) v.findViewById(R.id.price);
			item.setValue(  p.getPrice());
			item = (CustomLabelValue) v.findViewById(R.id.publication_date);
			item.setValue( p.getPublicationDate().subSequence(0, 10));
			


			
			LinearLayout layout = (LinearLayout) v
					.findViewById(R.id.check_boxes_layout);
			Collection<String> checkBoxs = getAmenities(p);
			if (checkBoxs.size() > 0) {
				int idx = 0;
				LinearLayout container =null;
				for (String chkText : checkBoxs) {
					if(idx%2 == 0){
						LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE);
						container = (LinearLayout)inflater.inflate( R.layout.item_checks_row,layout,false );
						layout.addView(container);
					}
					CustomCheckBox chk = new CustomCheckBox(layout.getContext());
					chk.setText(chkText);
					chk.setChecked(true);
					chk.setClickable(false);
					LinearLayout.LayoutParams lparams =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
					chk.setLayoutParams(lparams);
					chk.setTypeface( TypefaceSpan.getTypeFace(layout.getContext(), "Roboto-Light.ttf"));
					container.addView(chk);
					idx++;
				}
			}else{
				layout.setVisibility(View.GONE);
			}
			
   		}
	}


	public Collection<String> getAmenities(PublicationFullResult p) {
		Collection<String> checkBoxs;
		checkBoxs = new ArrayList<String>();
		if (p.isBrandNew())
			checkBoxs.add("A estrenar");
		if (p.hasKitchen())
			checkBoxs.add("Cocina");
		if (p.hasDiningRoom())
			checkBoxs.add("Comedor");
		if (p.hasLounge())
			checkBoxs.add("Living Comedor");
		if (p.hasLivingRoom())
			checkBoxs.add("Living");
		if (p.hasEnsuiteBedroom())
			checkBoxs.add("Dormitorio en Suite");
		if (p.hasHall())
			checkBoxs.add("Hall");
		if (p.hasServiceUnit())
			checkBoxs.add("Dependencia de Servicio");
		if (p.hasStudio())
			checkBoxs.add("Escritorio/Estudio");
		if (p.hasLaundry())
			checkBoxs.add("Lavadero");
		if (p.hasBackyard())
			checkBoxs.add("Patio");
		if (p.hasBalcony())
			checkBoxs.add("Balcon");
		if (p.hasFrontgarden())
			checkBoxs.add("Jardín Delantero");
		if (p.hasTerrace())
			checkBoxs.add("Terraza");
		if (p.hasStorage())
			checkBoxs.add("Baulera");
		if (p.hasGarage())
			checkBoxs.add("Cochera");
		if (p.hasCable())
			checkBoxs.add("Cable");
		if (p.hasInternet())
			checkBoxs.add("Internet");
		if (p.hasDrains())
			checkBoxs.add("Desagüe Cloacal");
		if (p.hasGas())
			checkBoxs.add("Gas Natural");
		if (p.hasMainsWater())
			checkBoxs.add("Agua Corriente");
		if (p.hasPavement())
			checkBoxs.add("Pavimento");
		return checkBoxs;
	};
		
	
	@Override
	public void onPublicationData() {
		 setViewInfo(getView());
		 
	}
	
}

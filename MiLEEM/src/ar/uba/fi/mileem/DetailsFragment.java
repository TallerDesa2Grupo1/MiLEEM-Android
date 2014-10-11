package ar.uba.fi.mileem;

import java.util.ArrayList;
import java.util.Collection;

import android.app.ActionBar.LayoutParams;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import ar.uba.fi.mileem.models.PublicationFullResult;
import ar.uba.fi.mileem.utils.ApiHelper;

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
			
			String content;
			String title;
			int colourApp = getResources().getColor(R.color.apptheme_color);
			int indexTitle;
			int indexContent;
			TextView operation_type = (TextView) v.findViewById(R.id.operation_type);
			//operation_type.setText("Tipo de Operación: " + p.getOperationType());
			title = "Tipo de Operación: ";
			content = p.getOperationType();
			indexTitle = title.length(); //wherever bold should begin 
			indexContent = indexTitle + content.length(); //wherever bold should end 
			Spannable span = new SpannableString(title + content); 
			span.setSpan(new StyleSpan(Typeface.BOLD),indexTitle, indexContent,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
			span.setSpan(new ForegroundColorSpan(colourApp), 0, indexTitle-1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			operation_type.setText(span);	
						
			TextView property_type = (TextView) v.findViewById(R.id.property_type);
			//property_type.setText("Tipo de Propiedad: " + p.getPropertyType());
			
			title = "Tipo de Propiedad: ";
			content = p.getPropertyType();
			indexTitle = title.length(); 
			indexContent = indexTitle + content.length();  
			span = new SpannableString(title + content); 
			span.setSpan(new StyleSpan(Typeface.BOLD),indexTitle, indexContent,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
			span.setSpan(new ForegroundColorSpan(colourApp), 0, indexTitle-1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			property_type.setText(span);	
			
			TextView address = (TextView) v.findViewById(R.id.address);
			title = "Domicilio: ";
			content = p.getPublicationAddress();
			indexTitle = title.length();  
			indexContent = indexTitle + content.length();  
			span = new SpannableString(title + content); 
			span.setSpan(new StyleSpan(Typeface.BOLD),indexTitle, indexContent,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
			span.setSpan(new ForegroundColorSpan(colourApp), 0, indexTitle-1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			address.setText(span);
			
			TextView neighborhood = (TextView) v.findViewById(R.id.neighborhood);
			title = "Barrio: ";
			content = p.getNeighborhood();
			indexTitle = title.length();  
			indexContent = indexTitle + content.length();  
			span = new SpannableString(title + content); 
			span.setSpan(new StyleSpan(Typeface.BOLD),indexTitle, indexContent,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
			span.setSpan(new ForegroundColorSpan(colourApp), 0, indexTitle-1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			neighborhood.setText(span);
			
			TextView covered_area = (TextView) v.findViewById(R.id.covered_area);
			covered_area.setText("Área Cubierta: " + p.getCoveredArea() + " m" + (char)178);
			
			TextView total_area = (TextView) v.findViewById(R.id.total_area);
			total_area.setText("Área Total: " + p.getTotalArea()+ " m" + (char)178);
			
			TextView rooms = (TextView) v.findViewById(R.id.rooms);
			rooms.setText("Ambientes: " + p.getRooms());
			
			TextView expenses = (TextView) v.findViewById(R.id.expenses);
			expenses.setText("Expensas: $ " + p.getExpenses());
			
			TextView age = (TextView) v.findViewById(R.id.age);
			age.setText("Antigüedad: " + p.getAge() + " años");
			
			String currency = p.getCurrency();
			if (currency != null){
			if (currency.equals("USD")){
				currency = "U$S";
			} else if (currency.equals("ARS")){
				currency = "$";
			}
			}else{
				currency = "";
			}
			
			TextView price = (TextView) v.findViewById(R.id.price);
			price.setText("Precio: " + currency + " " + p.getPrice());


			TextView publication_date = (TextView) v.findViewById(R.id.publication_date);
			
			publication_date.setText("Fecha de Publicación: " + p.getPublicationDate());
			
			LinearLayout layout = (LinearLayout) v.findViewById(R.id.check_boxes_layout);
	
			Collection<String> checkBoxs = getAmenities(p);
			
			TableLayout.LayoutParams tableRowParams= new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            int leftMargin=Double.valueOf(layout.getWidth()*0.30).intValue(),rightMargin=0,bottomMargin=0, topMargin=0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            tableRowParams.gravity = Gravity.LEFT;
           
			for (String chkText : checkBoxs) {
				CheckBox chk=new CheckBox(layout.getContext());  
				chk.setText(chkText);
				chk.setChecked(true);
				chk.setClickable(false);
				chk.setLayoutParams(tableRowParams);
//				(Double.valueOf(layout.getWidth()*0.40).intValue(),
//				        chk.getPaddingTop(),
//				        chk.getPaddingRight(),
//				        chk.getPaddingBottom());
				layout.addView(chk); 
		
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
				
		
			
			 
			
			
		/*	TextView publication_type = (TextView) v.findViewById(R.id.publication_type);
			
			publication_type.setText("Tipo de Publicación: " + p.getPublicationType());
			
			TextView status = (TextView) v.findViewById(R.id.status);
			
			status.setText("Estado: " + p.getStatus());

*/			
		
		
	
	
	
	
	@Override
	public void onPublicationData() {
		 setViewInfo(getView());
		 
	}
	
}

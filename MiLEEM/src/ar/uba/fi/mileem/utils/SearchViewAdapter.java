package ar.uba.fi.mileem.utils;

import java.util.List;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.uba.fi.mileem.models.PublicationResult;
import ar.uba.fi.mileem.R;

public class SearchViewAdapter extends ArrayAdapter<PublicationResult> {
  private final Context context;

  String[] url_images = {
		  "http://www.alquilerentaturista.com.ar/apartamentos/buenosairesg.jpg",
		  "http://www.rentnbaires.com/JUS/alquiler-temporario-departamentos/images/calo1.jpg",
		  "http://www.alquiler-temporario.com/imagenes/alquiler-temporario-departamento-1-dormitorio-argentina-cordoba/GetAttachment7.jpg",
		  "http://imganuncios.mitula.net/alquiler_de_03_departamentos_de_estreno_96752207889417804.jpg",
		  "http://www.apartments-for-rent.paginadigital.com.ar/JUS/alquiler-temporario-departamentos/images/yatch%20livingc.jpg",
		  "http://mla-s2-p.mlstatic.com/mar-del-plata-alquiler-departamento-edificio-havanna-dueno-13643-MLA3078387428_082012-O.jpg",
		  "http://images.evisos.com.ar/2011/03/16/alquiler-temporario-departamento-1-dormitorio-rosario_713f7d7ba_3.jpg",
		  "http://images01.olx-st.com/ui/16/18/03/1384788018_566671503_2-Alquiler-24-meses-departamento-3-ambientes-en-edificio-nuevo-plaza-mitre-Mar-del-Plata.jpg",
		  "http://staticcl.lavozdelinterior.com.ar/files/imagecache/ficha_aviso_600_400/avisos/aviso_departamento/aviso-departamento-alquileres-temporarios-1725789.jpg",
		  "http://images.clasiar.com/2011/12/21/departamentos-temporarios-para-alquiler-temporario_a0457909_3.jpg"
  };
  public SearchViewAdapter(Context context,List<PublicationResult> objects) {
  	super(context, R.layout.search_result_item, objects);
  	this.context = context;
  }

  public View getView(int position, View rowView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      if (rowView == null) {
      	rowView = inflater.inflate(R.layout.search_result_item, parent, false);
      }
      ImageView preview = (ImageView) rowView.findViewById(R.id.item_preview);
      UrlImageViewHelper.setUrlDrawable(preview, url_images[((position%10))], R.drawable.placeholder,  new UrlImageViewCallback() {
          @Override
          public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
              if (!loadedFromCache) {
                  ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                  scale.setDuration(300);
                  scale.setInterpolator(new OvershootInterpolator());
                  imageView.startAnimation(scale);
              }
          }});
      
      TextView titulo = (TextView) rowView.findViewById(R.id.titulo_item);
      TextView subtitulo = (TextView) rowView.findViewById(R.id.subtitulo_item);
      TextView price= (TextView) rowView.findViewById(R.id.item_price);
      PublicationResult item = getItem(position); 
      String s = item.toString();
      rowView.setBackgroundResource(((item.isHighlighted())?R.color.highlighted_publication_background:android.R.color.background_light));
      titulo.setText(s);
      subtitulo.setText(item.getNeighborhood());
      price.setText(item.getPrice());
      return rowView;
    }
  

  public boolean hasStableIds() {
    return true;
  }

} 
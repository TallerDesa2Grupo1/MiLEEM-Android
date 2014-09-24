package ar.uba.fi.mileem.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import ar.uba.fi.mileem.utils.TypefaceSpan;

public class CustomTextView extends TextView {

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(!isInEditMode()){
			init();
		}
	}

	private void init(){
		if(getTypeface() != null &&    getTypeface().getStyle() == Typeface.BOLD){
			setTypeface( TypefaceSpan.getTypeFace(getContext(), "Roboto-Bold.ttf"));
		}else{
			setTypeface( TypefaceSpan.getTypeFace(getContext(), "Roboto-Light.ttf"));
		}
	}

}

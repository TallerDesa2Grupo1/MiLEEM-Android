package ar.uba.fi.mileem.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FeedImageView extends ImageView {

	public FeedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FeedImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FeedImageView(Context context) {
		this(context, null);
	}
	
	
	    protected void onLayout(boolean changed, int left, int top, int right,
	            int bottom) {
	        super.onLayout(changed, left, top, right, bottom);
	    }
	 
	    protected void onDetachedFromWindow() {
	        super.onDetachedFromWindow();
	        System.gc();
	    }
	 
	    @Override
	    protected void drawableStateChanged() {
	        super.drawableStateChanged();
	        invalidate();
	    }
	 
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        int width = MeasureSpec.getSize(widthMeasureSpec);
	        int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
	        setMeasuredDimension(width, height);
	    }
	    

}

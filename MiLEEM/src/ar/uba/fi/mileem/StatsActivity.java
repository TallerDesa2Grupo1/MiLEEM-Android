package ar.uba.fi.mileem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class StatsActivity  extends Activity {
	WebView webView = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		        setContentView(R.layout.layout_webchart);
		        
		        webView = (WebView)findViewById(R.id.web);
		        webView.addJavascriptInterface(new WebAppInterface(), "Android");

		        webView.getSettings().setJavaScriptEnabled(true); 
		        webView.loadUrl("file:///android_asset/chart.html");
	}
	

	 public class WebAppInterface {

	     @JavascriptInterface
	  public int getNum1() {
	   return 2;
	  }
	  
	  @JavascriptInterface
	  public int getNum2() {
	   return 3;
	  }
	  
	  @JavascriptInterface
	  public int getNum3() {
	   return 4;
	  }
	  
	  @JavascriptInterface
	  public int getNum4() {
	   return 5;
	  }
	  
	  @JavascriptInterface
	  public int getNum5() {
	   return 6;
	  }
	 }
}

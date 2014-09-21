package ar.uba.fi.mileem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ar.uba.fi.mileem.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btnGo =  (Button) findViewById(R.id.buttonGo);
		btnGo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,SimpleFormActivity.class);
				MainActivity.this.startActivity(i);
			}
		});
	}




}

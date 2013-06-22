package com.example.volt;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class Explain extends Activity {

  DataStore DS;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explain);
		DS = new DataStore(this);
		TextView textView = (TextView) findViewById(R.id.title4);
		String testString = Integer.toString(DS.getSeekBarSetting1());
		textView.setText(testString);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.explain, menu);
		return true;
	}

}

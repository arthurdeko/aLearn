package com.jilgen.in3rds;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.view.Menu;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import java.lang.Integer;
import android.util.Log;

public class SettingsActivity extends Activity {
	
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;
	private EditText intervalEdit;
	final static String TAG = "Settings";
	static final String PREFS_FILE = "In3rdsPrefs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);	
		
		this.settings = getSharedPreferences(PREFS_FILE,0);
		int pollingInterval = this.settings.getInt("interval", 100000);
		this.intervalEdit = (EditText) findViewById(R.id.editIntervalText);
		
		Log.d(TAG, "Polling Interval: "+pollingInterval);
		Log.d(TAG, this.intervalEdit.toString());
		this.intervalEdit.setText(Integer.toString(pollingInterval));
		
	}

	public void onClickSettingsMenu(MenuItem item) {
		Intent intent = new Intent(this, SettingsActivity.class);
	    startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onPause() {
		super.onPause();
		this._updatePollingInterval();		
	}
	
	private void _updatePollingInterval() {
		int pollingInterval = Integer.parseInt(intervalEdit.getText().toString());
		this.settings = getSharedPreferences(PREFS_FILE, 0);
		this.editor=settings.edit();
		this.editor.putInt("interval", pollingInterval);
		this.editor.commit();
	}
	
	public void onClickDone(View view) {
		Intent intent = new Intent(this, MainActivity.class);		 
		this._updatePollingInterval();
		
		startActivity(intent);
	}
	
}

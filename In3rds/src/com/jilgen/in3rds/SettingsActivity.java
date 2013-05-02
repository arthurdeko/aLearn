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
	private EditText scaleEdit;
	final static String TAG = "Settings";
	static final String PREFS_FILE = "In3rdsPrefs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);	
		
		this.settings = getSharedPreferences(PREFS_FILE,0);
		int pollingInterval = this.settings.getInt("interval", 10);
		float scale = this.settings.getFloat("scale", 0x1);
		
		this.intervalEdit = (EditText) findViewById(R.id.editIntervalText);
		this.scaleEdit = (EditText) findViewById(R.id.editScaleText);
		
		Log.d(TAG, "Polling Interval: "+pollingInterval);
		Log.d(TAG, this.intervalEdit.toString());
		this.intervalEdit.setText(Integer.toString(pollingInterval));
		this.scaleEdit.setText(Float.toString(scale));
		
	}

	public void onClickSettingsMenu(MenuItem item) {
		Intent intent = new Intent(this, SettingsActivity.class);
	    startActivity(intent);
	}
	
	public void onClickReset(MenuItem item) {
        // Initialize database
    	StatsDatabaseHandler db = new StatsDatabaseHandler(this);
    	db.initialize();
	    db.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onPause() {
		super.onPause();
		this._updateSettings();		
	}
	
	private void _updateSettings() {
		int pollingInterval = Integer.parseInt(this.intervalEdit.getText().toString());
		float scale = Float.parseFloat(this.scaleEdit.getText().toString());
		
		this.settings = getSharedPreferences(PREFS_FILE, 0);
		this.editor=settings.edit();
		
		this.editor.putInt("interval", pollingInterval);
		this.editor.putFloat("scale", scale);
		this.editor.commit();
	}
	
}

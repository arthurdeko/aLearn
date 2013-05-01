package com.jilgen.in3rds;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.jilgen.in3rds.InternalStatistics;
import com.jilgen.in3rds.SimpleIntentService;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Handler;
import com.jilgen.in3rds.R;
import android.widget.RelativeLayout;
import com.jilgen.in3rds.StatGraph;
import com.jilgen.in3rds.StatsDatabaseHandler;
import com.jilgen.in3rds.InternalStats;
import com.jilgen.in3rds.SettingsActivity;
import com.jilgen.in3rds.StatTableActivity;
import android.content.SharedPreferences;
import android.view.MenuItem;
import java.util.List;

public class MainActivity extends Activity {
	
	static final String UPDATE_BATTERY_STRENGTH = "@string/update_battery_strength";
	static final String TAG = "In3rds";
	static final String PREFS_FILE = "In3rdsPrefs";
    private static Context context;
	InternalStatistics internalStatistics;
	private SharedPreferences settings;
	boolean isBound;
	private StatGraph batteryGraphView;
	private StatGraph signalGraphView;
	private int _pollingInterval = 10;
	private float _scale = 1;
	final Handler handler = new Handler();
	int createdCounter = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.settings = getSharedPreferences(PREFS_FILE,0);		
		this._pollingInterval = settings.getInt("interval", this._pollingInterval);
		
        MainActivity.context = getApplicationContext();
         
		Intent intent = new Intent(this, SimpleIntentService.class);
		startService(intent);
        
		setContentView(R.layout.activity_main);

		final Runnable r = new Runnable () {
			public void run() {
				updateGraphView();
				handler.postDelayed(this, _pollingInterval * 1000);
			}
		};
		this.handler.postDelayed(r, this._pollingInterval * 1000);
		
		this.updateGraphView();
        
	}

	public void onDestory() {
		Intent intent = new Intent(this, SimpleIntentService.class );
		stopService(intent);
		
		super.onDestroy();
	}
	
	public float getScaleSetting() {
		float scale = this.settings.getFloat("scale", 1);
		return scale;
	}
	
	public void onClickReset(MenuItem item) {
        // Initialize database
    	StatsDatabaseHandler db = new StatsDatabaseHandler(this);
    	db.initialize();
	    db.close();
	    updateGraphView();
	}
	
	public void onClickUpdateView(View view) { 	
    	this.updateGraphView();
	}
	
	public void onResume() {
		this.updateGraphView();
		super.onResume();
	}
	
	public void onClickTable(View view) {
		Intent intent = new Intent(this, StatTableActivity.class);
		Log.d(TAG, intent.toString());
		startActivity(intent);
	}
	
	public int getPollingInterval() {
		int interval;
		SharedPreferences settings = getSharedPreferences(PREFS_FILE, 0);
		interval=settings.getInt("interval", 6);
		return interval;
	}
	
	public void updateGraphView() {
		final RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
	    mainLayout.removeView(batteryGraphView);
	    mainLayout.removeView(signalGraphView);

       	StatsDatabaseHandler db = new StatsDatabaseHandler(this);
	    List<InternalStats> batteryRecords = db.getAllBatteryStrengths();
	    
	    batteryGraphView = new StatGraph(this);
	    batteryGraphView.setRecords(batteryRecords);
	    batteryGraphView.setScale(this.getScaleSetting());
	    batteryGraphView.setStart(120);
	    batteryGraphView.graphType="battery";
	    batteryGraphView.setScaleX(this._scale);
    	mainLayout.addView(batteryGraphView);
    	
	    List<InternalStats> signalRecords = db.getAllSignalStrengths();
	    
	    signalGraphView = new StatGraph(this);
	    signalGraphView.setRecords(signalRecords);
	    signalGraphView.setScale(this.getScaleSetting());
	    signalGraphView.setStart(240);
	    signalGraphView.graphType="signal";
    	mainLayout.addView(signalGraphView);
    	
    	db.close();
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
		
    public static Context getAppContext() {
        return MainActivity.context;
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.d(TAG, contents+" "+format);
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
    
}
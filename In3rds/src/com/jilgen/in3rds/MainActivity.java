package com.jilgen.in3rds;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import com.jilgen.in3rds.InternalStatistics;
import com.jilgen.in3rds.SignalStrengthListener;
import com.jilgen.in3rds.SimpleIntentService;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Handler;
import android.text.format.Time;
import com.jilgen.in3rds.R;
import com.jilgen.in3rds.BatteryValues;
import android.widget.RelativeLayout;
import com.jilgen.in3rds.StatGraph;
import com.jilgen.in3rds.StatsDatabaseHandler;
import com.jilgen.in3rds.BatteryStrength;
import com.jilgen.in3rds.InternalStats;
import com.jilgen.in3rds.SettingsActivity;
import com.jilgen.in3rds.StatTableActivity;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.content.BroadcastReceiver;
import java.util.List;

public class MainActivity extends Activity {
	
	static final String UPDATE_BATTERY_STRENGTH = "@string/update_battery_strength";
	static final String TAG = "In3rds";
	static final String PREFS_FILE = "In3rdsPrefs";
    private static Context context;
	InternalStatistics internalStatistics;
	private SharedPreferences settings;
	boolean isBound;
	private StatGraph statsGraphView;
	private int _pollingInterval = 10;
	private int _barWidth = 1;
	final Handler handler = new Handler();
	int createdCounter = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.settings = getSharedPreferences(PREFS_FILE,0);		
		this._pollingInterval = settings.getInt("interval", this._pollingInterval);
		this._barWidth = settings.getInt("barWidth", this._barWidth);
		
        MainActivity.context = getApplicationContext();
         
		setContentView(R.layout.activity_main);
		createdCounter++;
		final Runnable r = new Runnable () {
			public void run() {
				updateGraphView();
				handler.postDelayed(this, _pollingInterval * 1000);
			}
		};
		this.handler.postDelayed(r, this._pollingInterval * 1000);
		
		Intent intent = new Intent(this, SimpleIntentService.class);
		startService(intent);
		
		this.updateGraphView();
        
	}

	public void onDestory() {
		Intent intent = new Intent(this, SimpleIntentService.class );
		stopService(intent);
		
		super.onDestroy();
	}
	
	public int getBarScaleSetting() {
		int barScale = this.settings.getInt("barScale", 1);
		return barScale;
	}
	
	public int getBarWidthSetting() {
		int barWidth = this.settings.getInt("barWidth", 1);
		return barWidth;
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
	    mainLayout.removeView(statsGraphView);

       	StatsDatabaseHandler db = new StatsDatabaseHandler(this);
	    List<InternalStats> records = db.getAllBatteryStrengths();
	    db.close();
	    
	    Log.d(TAG, "Records count in Main "+records.size());
	    statsGraphView = new StatGraph(this);
	    statsGraphView.setRecords(records);
	    statsGraphView.setBarScale(this.getBarScaleSetting());
	    statsGraphView.setBarWidth(this.getBarWidthSetting());
    	mainLayout.addView(statsGraphView);
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
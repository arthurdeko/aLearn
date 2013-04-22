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
import com.jilgen.in3rds.BatteryValues;
import android.widget.RelativeLayout;
import com.jilgen.in3rds.StatGraph;
import com.jilgen.in3rds.StatsDatabaseHandler;
import com.jilgen.in3rds.BatteryStrength;
import com.jilgen.in3rds.InternalStats;
import com.jilgen.in3rds.SettingsActivity;
import android.content.SharedPreferences;
import android.view.MenuItem;
import java.util.List;

public class MainActivity extends Activity {
	
	static final String UPDATE_BATTERY_STRENGTH = "@string/update_battery_strength";
	static final String TAG = "In3rds";
	static final String PREFS_FILE = "In3rdsPrefs";
    private static Context context;
	InternalStatistics internalStatistics;
	//BatteryStrength batteryStrength;
	private TextView intervalView;
	private SharedPreferences settings;
	boolean isBound;
	private StatGraph statsGraphView;
	public int pollingInterval = 10000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.settings = getSharedPreferences(PREFS_FILE,0);		
		this.pollingInterval = settings.getInt("interval", 100000);
		
        MainActivity.context = getApplicationContext();
         
		setContentView(R.layout.activity_main);
		
		final RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
		
		this.intervalView = (TextView) findViewById(R.id.textViewInterval);
		this.intervalView.setText(Integer.toString(this.pollingInterval));
		
	    statsGraphView = new StatGraph(this);
 
	    StatsDatabaseHandler db = new StatsDatabaseHandler(this);
	    List<InternalStats> records = db.getAllBatteryStrengths();
	    Log.d(TAG, "Records count in Main "+records.size());
	    statsGraphView.setRecords(records);
	    mainLayout.addView(statsGraphView);
	        
	    final BatteryStrength batteryStrength = new BatteryStrength(this);
	    final Handler handler = new Handler();
	    final Runnable r = new Runnable()
	    {
	        public void run() 
	        {
	        
	        	SharedPreferences settings = getSharedPreferences(PREFS_FILE, 0);
	        	int pollingInterval=settings.getInt("Interval", 2000);
	        	Log.d(TAG, "Interval from prefs: "+pollingInterval);
	            handler.postDelayed(this, pollingInterval);
	            
	            Log.d(TAG, "BS "+batteryStrength.getValue());
	            
	        	StatsDatabaseHandler db = new StatsDatabaseHandler(context);
	            db.addStat(new InternalStats(batteryStrength.getValue()));	        	
	            List<InternalStats> stats = db.getAllBatteryStrengths();
	            Log.d(TAG, Integer.toString(stats.size()));
	            
	            db.close();
	            
	            updateGraphView();
	        }
	    };
	    
	    handler.postDelayed(r, this.getPollingInterval());
        
	}
	
	public void onResume() {
		super.onResume();
		this.settings = getSharedPreferences(PREFS_FILE, 0);
		int pollingInterval = this.settings.getInt("interval", 10000);
		Log.d(TAG, "polling Interval: "+pollingInterval);
		this.intervalView.setText(Integer.toString(pollingInterval));
		
	}
	
	public void onClickReset(View view) {
        // Initialize database
    	StatsDatabaseHandler db = new StatsDatabaseHandler(this);
    	db.initialize();
	    db.close();
	    updateGraphView();
	}
	
	public void onClickUpdateView(View view) { 	
    	this.updateGraphView();
	}
	
	public int getPollingInterval() {
		int interval;
		SharedPreferences settings = getSharedPreferences(PREFS_FILE, 0);
		interval=settings.getInt("interval", 60000);
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
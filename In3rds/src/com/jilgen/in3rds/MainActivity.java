package com.jilgen.in3rds;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import com.jilgen.in3rds.InternalStatistics;
import com.jilgen.in3rds.SignalStrengthListener;
import com.jilgen.in3rds.SimpleIntentService;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import com.jilgen.in3rds.BatteryValues;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import java.lang.Thread;
import android.widget.ImageView;
import android.graphics.drawable.*;
import com.jilgen.in3rds.StatGraph;
import com.jilgen.in3rds.GraphBar;
import java.util.List;

public class MainActivity extends Activity {
	TextView textDisplay;
	TextView locationDisplay;
	TextView signalDisplay;
	TextView batteryDisplay;
	TextView batteryHistory;

	static final String UPDATE_BATTERY_STRENGTH = "@string/update_battery_strength";
	static final String TAG = "In3rds";
    private static Context context;
	InternalStatistics internalStatistics;
	SignalStrengthListener signalStrength;
	//BatteryStrength batteryStrength;
	boolean isBound;
	LocationManager locationManager;
	Double longitude, latitude;
	public BatteryValues batteryValues = new BatteryValues();
	private StatGraph statsGraphView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    	Log.d(TAG, "sasad");
    	
        MainActivity.context = getApplicationContext();
         
		setContentView(R.layout.activity_main);
		final RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
		
		signalDisplay = (TextView) findViewById(R.id.textViewSignal);
		batteryDisplay = (TextView) findViewById(R.id.textViewBattery);
		
		signalStrength=new SignalStrengthListener( this );
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
	    if (!gpsEnabled) {
	        // Build an alert dialog here that requests that the user enable
	        // the location services, then when the user clicks the "OK" button,
	        // call enableLocationSettings()
	    	Toast toast = Toast.makeText(this, "GPS not enabled", Toast.LENGTH_SHORT);
	    	toast.show();
	    } else {
	    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
	    	        10000,          // 10-second interval.
	    	        10,             // 10 meters.
	    	        listener);
	    }

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
	        	
	            handler.postDelayed(this, 300000);
	            
	            Log.d(TAG, "BS "+batteryStrength.getValue());
	            
	        	StatsDatabaseHandler db = new StatsDatabaseHandler(context);
	            db.addStat(new InternalStats(batteryStrength.getValue()));	        	
	            List<InternalStats> stats = db.getAllBatteryStrengths();
	            Log.d(TAG, Integer.toString(stats.size()));
	            
	            db.close();
	        }
	    };
	    
	    handler.postDelayed(r, 300000);
        
	}

	public void onClickReset(View view) {
        // Initialize database
    	StatsDatabaseHandler db = new StatsDatabaseHandler(this);
    	db.initialize();
    	/*
		final RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
    	mainLayout.removeView(statsGraphView);
	    statsGraphView = new StatGraph(this);
	    
	    List<InternalStats> records = db.getAllBatteryStrengths();
	    Log.d(TAG, "Records count in Main "+records.size());
	    statsGraphView.setRecords(records);
    	mainLayout.addView(statsGraphView);
    	*/
	}
	
	public void updateBatteryValues ( String value ) {
		this.batteryValues.addValue(value);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private final LocationListener listener = new LocationListener( ) {
		
		public void onLocationChanged(final Location location) { 
			locationDisplay = (TextView) findViewById(R.id.textViewLocation);
			
			// TODO this is where you'd do something like context.sendBroadcast()
			Log.d(TAG, "Latitude: "+location.getLatitude()
					  +" Longitude: "+location.getLongitude()
					  +" Speed: "+location.getSpeed());
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			String locDump=location.toString().replaceAll(",", "\n");
			locationDisplay.setText( "Lat: "+latitude+"\nLong: "+longitude+"\n"+locDump);
		}
		
		public void onProviderEnabled( String string ) {
		}
		
		public void onProviderDisabled( String string ) {
		}
		
		public void onStatusChanged( String string, int flag, Bundle bundle ) {
		}
	};
	
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
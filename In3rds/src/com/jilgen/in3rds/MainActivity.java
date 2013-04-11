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
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import com.jilgen.in3rds.BatteryValues;
import java.lang.Thread;

public class MainActivity extends Activity implements OnClickListener {
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
	BatteryStrength batteryStrength;
	boolean isBound;
	LocationManager locationManager;
	Double longitude, latitude;
	public BatteryValues batteryValues = new BatteryValues();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        
		setContentView(R.layout.activity_main);
		signalDisplay = (TextView) findViewById(R.id.textViewSignal);
		batteryDisplay = (TextView) findViewById(R.id.textViewBattery);	
		batteryHistory = (TextView) findViewById(R.id.batteryHistoryView);
		
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);
		signalStrength=new SignalStrengthListener( this );
		batteryStrength=new BatteryStrength( this );
		
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
	    
	    Intent batteryStrengthIntent = new Intent(this, SimpleIntentService.class );
	    this.startService(batteryStrengthIntent);
	    
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
	
    public void onClick(View view) {
		//signalDisplay.setText( "Signal: "+this.signalStrength.value+" Battery: "+this.batteryStrength.value );
		signalDisplay.setText( "Signal: "+this.signalStrength.value );
		batteryDisplay.setText("Battery: "+this.batteryStrength.value );

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
    
    public void onToggleClicked(View view) {
    	Log.d(TAG, "Toggle clicked");
    }
    
}
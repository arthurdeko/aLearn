package com.jilgen.in3rds;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.location.LocationManager;
import android.location.Location;
import android.location.LocationListener;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.os.Messenger;
import android.os.Message;

public class LocationService extends IntentService {
	static final String TAG = "In3rds";
	public String latitude = "unk";
	public String longitude = "unk";

	public LocationService() {
		super("LocationService");
	}
	
	public LocationManager locationManager;
	public Location location;
	
	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {

		Log.d(TAG,"Service Started");
		
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

	    return super.onStartCommand(intent, flags, startId);

	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "Got intent "+intent.toString());
		Bundle extras = intent.getExtras();
		
		if (extras != null) {
			Messenger messenger = (Messenger) extras.get("MESSENGER");
			Message msg = Message.obtain();
			Bundle data = new Bundle();
			msg.arg1=1;
			data.putString("Longitude", longitude);
			data.putString("Latitude", latitude);
			data.putString("jeff", "ilgen");
			msg.setData( data );
			msg.what=999;
			try {
				Log.d(TAG, "Sending message "+msg.peekData());
				messenger.send(msg);
			} catch (android.os.RemoteException e1) {
				Log.w(getClass().getName(), "Exception sending message", e1);
			}

		}
	}
	
	
	private final LocationListener listener = new LocationListener( ) {
		
		public void onLocationChanged(final Location location) { 
			// TODO this is where you'd do something like context.sendBroadcast()
			Log.d(TAG, "Latitude: "+location.getLatitude()
					  +" Longitude: "+location.getLongitude()
					  +"Speed: "+location.getSpeed());
			latitude = ""+location.getLatitude();
					//location.getLatitude() + ", " +location.getLongitude()
			longitude = ""+location.getLongitude();
		}
		
		public void onProviderEnabled( String string ) {
		}
		
		public void onProviderDisabled( String string ) {
		}
		
		public void onStatusChanged( String string, int flag, Bundle bundle ) {
		}
	};
}

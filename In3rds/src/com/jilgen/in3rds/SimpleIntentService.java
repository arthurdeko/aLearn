package com.jilgen.in3rds;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import com.jilgen.in3rds.BatteryStrength;
import com.jilgen.in3rds.BatteryValues;
import com.jilgen.in3rds.MainActivity;
import java.lang.Math;

public class SimpleIntentService extends IntentService {

	public Context context=MainActivity.getAppContext();
	static final String TAG = "SimpleIntentService";
	public BatteryValues batteryValues = new BatteryValues();
   
	public SimpleIntentService() {
		super("SimpleIntentService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {

		Log.i(TAG,"Service Started");

		final BatteryStrength batteryStrength = new BatteryStrength(this.context);
		final Handler handler = new Handler();
		final Runnable r = new Runnable()
		{
			public void run() 
			{
        
				SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_FILE, 0);
				int pollingInterval=settings.getInt("interval", 2) * 1000;
        	
				Log.d(TAG, "Interval from prefs: "+pollingInterval);
				Log.d(TAG, "BS "+batteryStrength.getValue());
            
				StatsDatabaseHandler db = new StatsDatabaseHandler(context);
				
				db.addStat(new InternalStats(batteryStrength.getValue()));	        	
            
				db.close();
				
				handler.postDelayed(this, pollingInterval);
			}
		};
    
		SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_FILE, 0);
		int pollingInterval=settings.getInt("interval", 2) * 1000;
		handler.postDelayed(r, pollingInterval);
	
	    return super.onStartCommand(intent, flags, startId);

	}

}

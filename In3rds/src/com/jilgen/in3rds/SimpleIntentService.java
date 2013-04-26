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

public class SimpleIntentService extends IntentService {

	public Context context=MainActivity.getAppContext();
	static final String TAG = "SimpleIntentService";
	public BatteryStrength batteryStrength=new BatteryStrength( context );
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
		Log.d(TAG, intent.toString());

		String batteryString = ""+this.batteryStrength.getValue();
		this.batteryValues.addValue(batteryString);
		this.batteryValues.addValue(batteryString);
		this.batteryValues.addValue(batteryString);
		this.batteryValues.addValue(batteryString);
		Log.d(TAG, "Battery Strength "+batteryValues.toString());
        
		final BatteryStrength batteryStrength = new BatteryStrength(this);
		final Handler handler = new Handler();
		final Runnable r = new Runnable()
		{
			public void run() 
			{
        
				SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_FILE, 0);
				int pollingInterval=settings.getInt("interval", 2000);
        	
				Log.d(TAG, "Interval from prefs: "+pollingInterval);
				Log.d(TAG, "BS "+batteryStrength.getValue());
            
				StatsDatabaseHandler db = new StatsDatabaseHandler(context);
				Time time = new Time();
				time.setToNow();
				db.addStat(new InternalStats(batteryStrength.getValue(), time.hour+":"+time.minute+":"+time.second));	        	

				Log.d(TAG, time.hour+":"+time.minute+":"+time.second);
            
				List<InternalStats> stats = db.getAllBatteryStrengths();
				Log.d(TAG, Integer.toString(stats.size()));
            
				db.close();
            
				updateGraphView();
				handler.postDelayed(this, pollingInterval);
        	
        }
    };
    
    handler.postDelayed(r, this.getPollingInterval());
		
		
		//while (true) {
			//batteryHistory.setText(batteryValues.toString());
			//try {
				//Thread.sleep(100);
			//} catch (InterruptedException e) {
				
			//}
		//}
		
		
	    return super.onStartCommand(intent, flags, startId);

	}

}

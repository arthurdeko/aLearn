package com.jilgen.in3rds;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import com.jilgen.in3rds.BatteryStrength;
import com.jilgen.in3rds.BatteryValues;

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

		String batteryString = ""+this.batteryStrength.value;
		this.batteryValues.addValue(batteryString);
		this.batteryValues.addValue(batteryString);
		this.batteryValues.addValue(batteryString);
		this.batteryValues.addValue(batteryString);
		Log.d(TAG, "Battery Strength "+batteryValues.toString());
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

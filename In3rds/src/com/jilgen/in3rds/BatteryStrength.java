package com.jilgen.in3rds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryStrength extends BroadcastReceiver {
    private int _value = -1;
    double ftemp = 0;
    static final String TAG = "BatteryStrength";
	
	public BatteryStrength( Context context ) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(this, filter);
	}
	
	public int getValue() {
		Log.d(TAG, "Value requested "+this._value);
		return this._value;
	}
	
	public void setValue( int _value) {
		this._value=_value;
	}
	
    @Override
    public void onReceive(Context context, Intent intent) {
        this.setValue(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1));
        String valueString = Integer.toString(this._value);
        Log.d(TAG, "As int "+this._value);
        Log.i(TAG, "Battery strength "+valueString);
    }
}

package com.jilgen.in3rds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryStrength extends BroadcastReceiver {
    int value = -1;
    double ftemp = 0;
	
	public BatteryStrength( Context context ) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(this, filter);
	}
	
    @Override
    public void onReceive(Context context, Intent intent) {
        value = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    }
}

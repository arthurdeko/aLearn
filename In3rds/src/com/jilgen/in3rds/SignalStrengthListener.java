package com.jilgen.in3rds;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.content.Context;
import android.telephony.SignalStrength;

public class SignalStrengthListener extends PhoneStateListener {
	static final String TAG = "In3rds";
	protected TelephonyManager telephonyManager;
	public Context context;
	public String value;
	
	public SignalStrengthListener( Context context ) { 
		telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(this, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS );
	}
	
	public void onSignalStrengthsChanged(SignalStrength signalStrength) { 
		Log.d(TAG, "Signal Strength "+signalStrength.getGsmSignalStrength() );
		value=Integer.toString( signalStrength.getGsmSignalStrength() );
	}
}
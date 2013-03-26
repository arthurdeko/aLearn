package com.example.in3rds;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;

public class MainActivity extends Activity {
	TextView textDisplay;

	static final String TAG = "In3rds";
	TelephonyManager telephonyManager;
	PhoneStateListener phoneListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textDisplay = (TextView) findViewById(R.id.textView1);
		
		//create Broadcast Receiver
		///intent filters
		//battery
		
		//get signal strength
		phoneListener=new android.telephony.PhoneStateListener() {
		     public void onSignalStrengthsChanged(int asu) { 
		    	 Log.d(TAG, "Signal Strength "+asu );
		    	 textDisplay.setText( ""+asu );
		     }
		};
		telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		Log.d(TAG, "string" );
		telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}



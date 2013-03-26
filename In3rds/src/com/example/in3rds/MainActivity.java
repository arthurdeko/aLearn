package com.example.in3rds;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import java.lang.System;
import com.example.in3rds.StatContainer;

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
		
		textDisplay.setText("Started \n");
		
		//create Broadcast Receiver
		///intent filters
		//battery
		
		//get signal strength
		phoneListener=new android.telephony.PhoneStateListener() {
		     public void onSignalStrengthsChanged(SignalStrength signalStrength) { 
		    	 Log.d(TAG, "Signal Strength "+signalStrength.getGsmSignalStrength() );
		    	 textDisplay.append( System.currentTimeMillis()+" "+signalStrength.getGsmSignalStrength()+"\n" );
		 		 StatContainer statContainer = new StatContainer();
				 statContainer.Name="Signal Strength";
				 statContainer.setValue( signalStrength.getGsmSignalStrength() );
				 statContainer.Time=System.currentTimeMillis();
				 Log.d(TAG, statContainer.asString() );
		     }
		};
		telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS );

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}



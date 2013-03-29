package com.jilgen.in3rds;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import com.jilgen.in3rds.InternalStatistics;
import com.jilgen.in3rds.SignalStrengthListener;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener {
	TextView textDisplay;
	TextView statDisplay;

	static final String TAG = "In3rds";
	InternalStatistics internalStatistics;
	SignalStrengthListener signalStrength;
	BatteryStrength batteryStrength;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textDisplay = (TextView) findViewById(R.id.textView1);
		statDisplay = (TextView) findViewById(R.id.textView2);
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);
		textDisplay.setText("Started \n");
		signalStrength=new SignalStrengthListener( this );
		batteryStrength=new BatteryStrength( this );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public void onClick(View v) {
		statDisplay.setText( "Signal: "+this.signalStrength.value+" Battery: "+this.batteryStrength.value );
    }
}



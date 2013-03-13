package com.jilgen.batteryclock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ClockActivity extends Activity {
	
	private View textOutput;
	private EditText ex;
	private EditText why;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        
        View textOutput = (View) findViewById(R.id.fullscreen_content);
        ex = (EditText) findViewById(R.id.ex);
        why = (EditText) findViewById(R.id.why);
        
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
    
    public void updateScreen(View view) {
    }
}

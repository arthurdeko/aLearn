package com.jilgen.in3rds;

import com.jilgen.in3rds.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.CursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import com.jilgen.in3rds.StatsDatabaseHandler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class StatTableActivity extends ListActivity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_stat_table);
		
		StatsDatabaseHandler db = new StatsDatabaseHandler(this);
		Cursor cursor=db.getAllRecordsForView();

        // THE DESIRED COLUMNS TO BE BOUND
        String[] columns = new String[] { db.KEY_TIME, db.KEY_BATTERY_STRENGTH, db.KEY_SIGNAL_STRENGTH };
        // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
        int[] to = new int[] { R.id.datetime_entry, R.id.battery_strength_entry, R.id.signal_strength_entry };

        // CREATE THE ADAPTER USING THE CURSOR POINTING TO THE DESIRED DATA AS WELL AS THE LAYOUT INFORMATION
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.stats_entry, cursor, columns, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        // SET THIS ADAPTER AS YOUR LISTACTIVITY'S ADAPTER
        this.setListAdapter(mAdapter);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			//mSystemUiHider.hide();
		}
	};

	public void onClickSettingsMenu(MenuItem item) {
		Intent intent = new Intent(this, SettingsActivity.class);
	    startActivity(intent);
	}
	
	public void onClickReset(MenuItem item) {
        // Initialize database
    	StatsDatabaseHandler db = new StatsDatabaseHandler(this);
    	db.initialize();
	    db.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}

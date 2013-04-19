package com.jilgen.in3rds;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.graphics.Canvas;
import android.util.Log;
import com.jilgen.in3rds.InternalStats;
import java.util.List;
import java.util.ArrayList;

public class StatGraph extends ImageView {

	final static String TAG = "StatGraph";
	private ShapeDrawable bar = new ShapeDrawable(new RectShape());
	private List<InternalStats> _records;
	ShapeDrawable graphBar;
	
	public StatGraph(Context context) {
		super(context);
		Log.d(TAG, "Constructed");
		graphBar = new ShapeDrawable(new RectShape());
		graphBar.getPaint().setColor(0xff74AC23);
		graphBar.setBounds(20, 50, 20 + 200, 50 + 5);
	}

	public StatGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public StatGraph(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public List<InternalStats> getRecords() {
		return this._records;
	}
	
	public void setRecords(List<InternalStats> _records) {
		this._records=_records;
	}
	
	public void onDraw(Canvas canvas) {
		/*
		int counter = 0;
		int x = 20;
		int barWidth = 10;
		Log.d(TAG, "Drawing");
		
		int recordCount = this.getRecords().size();
		Log.d(TAG, "Drawing "+recordCount);
		for ( InternalStats statsRecord : this.getRecords() ) {
			//this.bar.getPaint().setColor(0xff74AC23);
			//int width = statsRecord.getBatteryStrength();
			//int height = barWidth;
			//int y = counter * barWidth;
			//this.bar.setBounds(x, y, x + width, y + height);
			//this.bar.draw(canvas);
			//Log.d(TAG, "Drawing "+height+":"+width);
			Log.d(TAG, "Counter "+counter);
			counter++;			
		}
		*/
		graphBar.draw(canvas);

	}

}

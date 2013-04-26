package com.jilgen.in3rds;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.util.Log;
import com.jilgen.in3rds.InternalStats;
import java.util.List;
import java.util.ArrayList;

public class StatGraph extends View {

	final static String TAG = "StatGraph";
	private ShapeDrawable bar = new ShapeDrawable(new RectShape());
	private List<InternalStats> _records;
	private ShapeDrawable graphBar;
	private int barScale = 1; 
	
	public StatGraph(Context context) {
		super(context);
		Log.d(TAG, "Constructed");
		graphBar = new ShapeDrawable(new RectShape());
		graphBar.getPaint().setColor(0xff74AC23);

	}
	
	public void setBarScale( int scale ) {
		this.barScale=scale;
	}
	
	public int getBarScale() {
		return this.barScale;
	}
	
	public List<InternalStats> getRecords() {
		return this._records;
	}
	
	public void setRecords(List<InternalStats> _records) {
		this._records=_records;
	}
	
	protected void onDraw(Canvas canvas) {

		int counter = 0;
		int x = 20;
		int barWidth = 1;
		
		int recordCount = this.getRecords().size();
		Log.d(TAG, "Drawing "+recordCount);
		for ( InternalStats statsRecord : this.getRecords() ) {
			this.bar.getPaint().setColor(0xff74AC23);

			int width = statsRecord.getBatteryStrength() * this.barScale;
			int y = counter * barWidth;
			y += 2;
			
			this.bar.setBounds(x, y, x + width, y + barWidth);
			this.bar.draw(canvas);
			counter++;			
		}

	}

}

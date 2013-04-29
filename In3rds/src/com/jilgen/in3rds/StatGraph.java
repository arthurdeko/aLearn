package com.jilgen.in3rds;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.LinearGradient;
import android.util.Log;
import android.graphics.Path;
import android.graphics.Paint;
import com.jilgen.in3rds.InternalStats;
import java.util.List;
import java.util.ArrayList;

public class StatGraph extends View {

	final static String TAG = "StatGraph";
	private ShapeDrawable bar = new ShapeDrawable(new RectShape());
	private List<InternalStats> _records;
	private ShapeDrawable graphBar;
	private int _barScale = 1; 
	private int _barWidth = 1;
	
	public StatGraph(Context context) {
		super(context);
		Log.d(TAG, "Constructed");
		graphBar = new ShapeDrawable(new RectShape());
		graphBar.getPaint().setColor(0xff74AC23);
	}

	private float getSlope( double t1, double t2, int y1, int y2 ) {
		double slope=0;
		double timeDelta = t1 - t2;
		double strengthDelta = y1 - y2;
		if ( timeDelta == 0 ) {
			slope=0;
		} else {
			slope=strengthDelta / timeDelta;
		}
		return Math.abs((float)slope);
	}
	
	public void setBarScale( int _scale ) {
		this._barScale=_scale;
	}
	
	public int getBarScale() {
		return this._barScale;
	}
	
	public void setBarWidth(int _width) {
		this._barWidth=_width;
	}
	
	public List<InternalStats> getRecords() {
		return this._records;
	}
	
	public void setRecords(List<InternalStats> _records) {
		this._records=_records;
	}
	
	protected void onDraw(Canvas canvas) {

		int recordCount = this.getRecords().size();
		Log.d(TAG, "Drawing "+recordCount);

		int previousStrength = 100;
		double previousTime = 0;
		
		ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
		    @Override
		    public Shader resize(int width, int height) {
		        LinearGradient gradient = new LinearGradient(0, 0, width, height, 
		            new int[] { 0x44449958, 0xee449958, 0xaa449958 }, 
		            new float[] { 0.0f, 0.90f, 1.0f },
		            Shader.TileMode.REPEAT);
		        return gradient;
		    }
		};
		
		int topMargin = 5;
		int leftMargin = 5;
		int leftBatteryMargin = 20;
		int y = topMargin;
		float batteryStrokeWidth = 5;
		float slope = 0;
		float slopeX = 5;
		double dt = 0;

		Path slopePath = new Path();
		Paint slopePaint = new Paint();
		slopePaint.setStyle(Paint.Style.STROKE);
		slopePath.moveTo(leftMargin, topMargin);
		slopePaint.setColor(0xffffff58);
		
		Path batteryPath = new Path();
		Paint batteryPaint = new Paint();
		batteryPaint.setStyle(Paint.Style.STROKE);
		batteryPath.moveTo(leftMargin, topMargin);
		batteryPaint.setColor(0xff2299cc);
		batteryPaint.setAntiAlias(true);
		batteryPaint.setStrokeCap(Paint.Cap.ROUND);

		batteryPaint.setStrokeWidth(batteryStrokeWidth);
		
		for ( InternalStats statsRecord : this.getRecords() ) {
			
			int currentStrength = statsRecord.getBatteryStrength();
			double currentTime = statsRecord.getTime();
			
			if ( previousTime == 0 ) {
				dt = 0;
			} else {
				dt = currentTime - previousTime;
			}
			y = (int)dt + y;
			
			slope = getSlope( previousTime, currentTime, previousStrength, currentStrength);

			float batteryX = (float)currentStrength + (float)leftBatteryMargin;
			if ( y == topMargin ) {
				batteryPath.moveTo( batteryX, (float)y);
			} else {
				batteryPath.lineTo( batteryX, (float)y);
			}

			Log.d(TAG, "Slope " + (int)Math.floor(slope * 1000) + " " + dt);
			
			slopeX = (float)slope + leftMargin;
			slopePath.lineTo( slopeX, (float)y);
			Log.d(TAG, "Point " + slopeX + "," + y);

			previousStrength = currentStrength;
			previousTime = currentTime;
		}
		
		canvas.drawPath(slopePath, slopePaint);
		canvas.drawPath(batteryPath, batteryPaint);
	}

}

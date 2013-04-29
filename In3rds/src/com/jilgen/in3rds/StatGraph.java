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

	private double getSlope( long t1, long t2, int y1, int y2 ) {
		double slope=0;
		double timeDelta = t1 - t2;
		double strengthDelta = y1 - y2;
		if ( timeDelta == 0 ) {
			slope=0;
		} else {
			slope=strengthDelta / timeDelta;
		}
		return Math.abs(slope);
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

		int counter = 0;
		int x = 20;
		
		int recordCount = this.getRecords().size();
		Log.d(TAG, "Drawing "+recordCount);
		int previousStrength = 100;
		long previousTime = 0;
		
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
		
		for ( InternalStats statsRecord : this.getRecords() ) {

			int currentStrength = statsRecord.getBatteryStrength();
			long currentTime = statsRecord.getTime();

			double slope = getSlope( previousTime, currentTime, previousStrength, currentStrength + 1);
			
			Log.d(TAG, "Slope "+(int)Math.floor(slope * 1000000));
			
			this.bar.getPaint().setColor(0x77449958);
			this.bar.setShaderFactory(sf);
			int width = currentStrength * this._barScale;
			int y = counter * this._barWidth;
			y += 2;
			
			this.bar.setBounds(x, y, x + width, y + this._barWidth);
			//this.bar.draw(canvas);
			
			Path slopePath = new Path();
			Paint slopePaint = new Paint();
			slopePaint.setStyle(Paint.Style.STROKE);
			slopePath.moveTo(0,0);
			slopePath.lineTo(100, 100);
			slopePath.lineTo(100, 200);
			
			slopePaint.setColor(0xffffff58);
			canvas.drawPath(slopePath, slopePaint);
			
			counter++;

			previousStrength = currentStrength;
			previousTime = currentTime;

		}

	}

}

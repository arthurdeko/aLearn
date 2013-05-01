package com.jilgen.in3rds;

import android.content.Context;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.LinearGradient;
import android.util.Log;
import android.graphics.Path;
import android.graphics.Paint;
import com.jilgen.in3rds.InternalStats;
import java.util.List;
import java.util.Random;

public class StatGraph extends View {

	final static String TAG = "StatGraph";
	private List<InternalStats> _records;
	private float _scale = 1;
	private float _strokeWidth = 1;
	public String graphType = "battery";
	private float _start = 20;
	
	public StatGraph(Context context) {
		super(context);
		Log.d(TAG, "Constructed");
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
	
	public void setStart( float _start ) {
		this._start = _start;
	}

	public float getStart( ) {
		return this._start;
	}
	
	public void setScale( float _scale ) {
		this._scale=_scale;
	}
	
	public float getScale() {
		return this._scale;
	}
	
	public void setStrokeWidth( float _width ) {
		this._strokeWidth=_width;
	}
	
	public float getStrokeWidth() {
		return this._strokeWidth;
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
		
		double previousTime = 0;
		int leftMargin = 5;
		float x = leftMargin;
		this.setStrokeWidth(4);
		double dt = 0;
		double currentTime = 0;
		int value = 0;
		
		Path graphPath = new Path();
		Paint graphPaint = new Paint();
		graphPaint.setStyle(Paint.Style.STROKE);
		graphPath.moveTo(leftMargin, this._start);
		graphPaint.setColor(0xff2299cc);
		graphPaint.setAntiAlias(true);
		graphPaint.setStrokeCap(Paint.Cap.ROUND);

		graphPaint.setStrokeWidth(this.getStrokeWidth());
		
		for ( InternalStats statsRecord : this.getRecords() ) {
			float y = 0;
			
			if ( this.graphType == "battery" ) {
				value = statsRecord.getBatteryStrength();
			} else if ( this.graphType == "signal") {
				value = statsRecord.getSignalStrength();
			}
			currentTime = statsRecord.getTime();
			
			if ( previousTime == 0 ) {
				dt = 0;
			} else {
				dt = currentTime - previousTime;
			}
			x = (int)dt + x;
			
			y = this._start - (float)value;
			//Random random = new Random();
			//y = this._start - (float)random.nextInt(100);
			
			graphPath.lineTo( x, y );
			
			previousTime = currentTime;
		}		
		canvas.drawPath(graphPath, graphPaint);
	}
}
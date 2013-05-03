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
import android.graphics.Color;

public class StatGraph extends View {

	final static String TAG = "StatGraph";
	private List<InternalStats> _records;
	private float _scale = 1;
	private float _strokeWidth = 1;
	public String graphType = "battery";
	private float _graphHeight;
	private float _startX = 20;
	private float _startY = 120;
	private final Path axisPathX = new Path();
	private final Path axisPathY = new Path();
	private final Path graphPath = new Path();
	private final Paint graphPaint = new Paint();
	private final Paint axisPaint = new Paint();
	private int _lineColor;
	private int _axisColor;
	
	public StatGraph(Context context) {
		super(context);
		Log.d(TAG, "Constructed");
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
	
	public void setLocation( float x, float y ) {
		this._startX=x;
		this._startY=y;
	}
	
	public void setGraphHeight( float _height ) {
		this._graphHeight=_height;
	}
	
	public void setLineColor( int color ) {
		this._lineColor=color;
	}

	public void setAxisColor( int color ) {
		this._axisColor=color;
	}

	protected void onDraw(Canvas canvas) {

		int recordCount = this.getRecords().size();
		Log.d(TAG, "Drawing "+recordCount);

		double previousTime = 0;
				
		int leftMargin = 20;
		float x = leftMargin;
		this.setStrokeWidth(4);
		double dt = 0;
		double currentTime = 0;
		int value = 0;
		int canvasWidth = canvas.getWidth();
				
		graphPath.moveTo(this._startX, this._startY);
		graphPaint.setStyle(Paint.Style.STROKE);
		graphPaint.setColor(this._lineColor);
		graphPaint.setAntiAlias(true);
		graphPaint.setStrokeCap(Paint.Cap.ROUND);
		graphPaint.setStrokeWidth(this._strokeWidth);
		
		axisPaint.setColor(this._axisColor);
		axisPaint.setStyle(Paint.Style.STROKE);
		axisPathX.moveTo(this._startX, this._startY);
		axisPathX.lineTo(canvasWidth, this._startY);
		axisPathY.moveTo(this._startX, this._startY);
		axisPathY.lineTo(this._startX, this._startY - this._graphHeight );	
		
		for ( InternalStats statsRecord : this.getRecords() ) {
			float y = 0;
			
			if ( this.graphType == "battery" ) {
				value = statsRecord.getBatteryStrength();
			} else if ( this.graphType == "signal") {
				value = statsRecord.getSignalStrength();
			}
			
			if ( currentTime == 0 ) {
				graphPath.moveTo(this._strokeWidth + (float)this._startX, (float)this._startY - (float)value);
			}
			
			currentTime = statsRecord.getTime();
			
			if ( previousTime == 0 ) {
				dt = 0;
			} else {
				dt = currentTime - previousTime;
			}
			x = (int)dt + x;
			
			y = this._startY - (float)value;
			//Random random = new Random();
			//y = this._start - (float)random.nextInt(100);
			
			graphPath.lineTo( x, y );
			
			previousTime = currentTime;
		}		
		canvas.drawPath(graphPath, graphPaint);
		canvas.drawPath(axisPathX, axisPaint);
		canvas.drawPath(axisPathY, axisPaint);
	}
}

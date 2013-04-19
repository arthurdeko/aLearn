package com.jilgen.in3rds;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;

public class GraphBar extends View {

	final static String TAG = "GraphBar";
	private ShapeDrawable graphBar;
	public int top;
	public int height = 5;
	public int width = 50;
	public int x = 50;
	public int y = 200;
	
	public GraphBar(Context context) {
		super(context);
		Log.d(TAG, "Creating graph bar");
		graphBar = new ShapeDrawable(new RectShape());
		graphBar.getPaint().setColor(0xff74AC23);
        //int barLength = batteryStrength.getValue();
        //Log.d(TAG, "Updating graph "+offset);
       
	}
	
	public void setBounds() {
		graphBar.setBounds(x, y, x + width, y + height);
	}
	
	public GraphBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GraphBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	protected void onDraw(Canvas canvas) {
		graphBar.setBounds(this.x, this.y, this.x + this.width, this.y + this.height);
		graphBar.draw(canvas);
	}

}

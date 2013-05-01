package com.jilgen.in3rds;

public class InternalStats {
	private int _id;
	private double _time;
	private int _batteryStrength;
	private int _signalStrength;
	
	public InternalStats() {
	}
	
	public InternalStats(int _batteryStrength, int _signalStrength) {
		this._batteryStrength=_batteryStrength;
		this._signalStrength=_signalStrength;
	}
	
	public InternalStats(int _batteryStrength, int _signalStrength, float _time) {
		this._time=_time;
		this._batteryStrength=_batteryStrength;
		this._signalStrength=_signalStrength;
	}	
	
	public int getID() {
		return this._id;
	}
	
	public void setID(int _id) {
		this._id = _id;
	}
	
	public double getTime() {
		return this._time;
	}
	
	public void setTime(long _time) {
		this._time=_time;
	}
	
	public int getBatteryStrength() {
		return this._batteryStrength;
	}
	
	public void setBatteryStrength( int _batteryStrength) {
		this._batteryStrength=_batteryStrength;
	}
	
	public void setSignalStrength( int _signalStrength ) {
		this._signalStrength=_signalStrength;
	}
	
	public int getSignalStrength( ) {
		return this._signalStrength;
	}
}

package com.jilgen.in3rds;

public class InternalStats {
	private int _id;
	private long _time;
	private int _batteryStrength;
	
	public InternalStats() {
		
	}
	
	public InternalStats(int _batteryStrength) {
		this._batteryStrength=_batteryStrength;
	}
	
	public InternalStats(int _batteryStrength, long _time) {
		this._time=_time;
		this._batteryStrength=_batteryStrength;
	}	
	
	public int getID() {
		return this._id;
	}
	
	public void setID(int _id) {
		this._id = _id;
	}
	
	public long getTime() {
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
	
}

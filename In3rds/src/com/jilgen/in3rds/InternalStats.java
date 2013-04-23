package com.jilgen.in3rds;
import android.text.format.Time;

public class InternalStats {
	private int _id;
	private String _datetime;
	private int _batteryStrength;
	
	public InternalStats() {
		
	}
	
	public InternalStats( int _id, Time datetime, int _batteryStrength ) {
		this._id=_id;
		this._batteryStrength=_batteryStrength;	
	}
	
	public InternalStats(int _batteryStrength, String _datetime) {
		this._datetime=_datetime;
		this._batteryStrength=_batteryStrength;
	}	
	
	public int getID() {
		return this._id;
	}
	
	public void setID(int _id) {
		this._id = _id;
	}
	
	public String getDateTime() {
		return this._datetime;
	}
	
	public void setDateTime(String _datetime) {
		this._datetime=_datetime;
	}
	
	public int getBatteryStrength() {
		return this._batteryStrength;
	}
	
	public void setBatteryStrength( int _batteryStrength) {
		this._batteryStrength=_batteryStrength;
	}
	
}

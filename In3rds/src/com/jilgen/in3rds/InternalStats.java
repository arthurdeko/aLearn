package com.jilgen.in3rds;

public class InternalStats {
	int _id;
	int _batteryStrength;
	
	public InternalStats() {
		
	}
	
	public InternalStats( int _id, int _batteryStrength ) {
		this._id=_id;
		this._batteryStrength=_batteryStrength;	
	}
	
	public InternalStats(int _batteryStrength) {
		this._batteryStrength=_batteryStrength;
	}	
	
	public int getID() {
		return this._id;
	}
	
	public void setID(int _id) {
		this._id = _id;
	}
	
	public int getBatteryStrength() {
		return this._batteryStrength;
	}
	
	public void setBatteryStrength( int _batteryStrength) {
		this._batteryStrength=_batteryStrength;
	}
	
}

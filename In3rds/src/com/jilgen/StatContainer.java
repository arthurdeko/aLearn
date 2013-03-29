package com.jilgen;

public class StatContainer {
	public long Time;
	private String Value;
	public String Name;
	
	public StatContainer() {
		// TODO Auto-generated constructor stub
	}

	public void setValue( Object value ) {
		this.Value=value.toString();
	}
	
	public String getValue() {
		return this.Value;
	}
	
	public String asString() {
		return "Name: '"+this.Name+"' Time: "+this.Time+" Value: "+this.getValue();
	}
}
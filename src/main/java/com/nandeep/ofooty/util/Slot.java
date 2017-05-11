package com.nandeep.ofooty.util;

public enum Slot {
	
	ONE("6:00 AM - 7:00 AM"),
	TWO("7:00 AM - 8:00 AM"),
	THREE("8:00 AM - 9:00 AM"),
	FOUR("9:00 AM - 10:00 AM"),
	FIVE("10:00 AM - 11:00 AM"),
	SIX("11:00 AM - 12:00 PM"),
	SEVEN("12:00 PM - 1:00 PM"),
	EIGHT("1:00 PM - 2:00 PM"),
	NINE("2:00 PM - 3:00 PM"),
	TEN("3:00 PM - 4:00 PM"),
	ELEVEN("4:00 PM - 5:00 PM"),
	TWELVE("5:00 PM - 6:00 PM"),
	THIRTEEN("6:00 PM - 7:00 PM"),
	FOURTEEN("7:00 PM - 8:00 PM"),
	FIFTEEN("8:00 PM - 9:00 PM"),
	SIXTEEN("9:00 PM - 10:00 PM");
	
	private String slot;
	
	private Slot(String slot){
		this.slot = slot;
	}
	
	public String getSlot(){
		return this.slot;
	}
}

package com.nandeep.ofooty.service;

import java.util.List;

import com.nandeep.ofooty.modal.GCrew;
import com.nandeep.ofooty.modal.Ground;
import com.nandeep.ofooty.modal.GroundAvailability;

public interface IGroundService {
	
	public void addGround(String name, String street, String city, String state, String country, String zip, GCrew gcrew);
	
	public List<Ground> getGroundBySearchKey(String key, String value);
	
	public void enableGround(int gid);
	
	public void disableGround(Ground ground);
	
	public List<GCrew> getNewGrounds();
	
	public Long getGroundsCount();
	
	public GroundAvailability getGroundAvailabilityByDate(Ground ground, String date);
	
	public GroundAvailability updateGroundAvailabilityStatus(Ground ground, String availId, String date, String slot);
}

package com.nandeep.ofooty.service;

import com.nandeep.ofooty.modal.GCrew;


public interface IGCrewService {
	
	public GCrew saveGCrew(String firstName, String lastName, String mobile);
	
	public boolean isGCrewMobileDuplicate(String mobile);
}

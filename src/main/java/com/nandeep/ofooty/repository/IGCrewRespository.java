package com.nandeep.ofooty.repository;

import java.util.List;

import com.nandeep.ofooty.modal.GCrew;

public interface IGCrewRespository {
	
	public void save(GCrew gcrew);
	public void delete(GCrew gcrew);
	public List<GCrew> findAll();
	public GCrew findById(int gcrewId);
}

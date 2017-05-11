package com.nandeep.ofooty.repository;

import com.nandeep.ofooty.modal.MatchRequest;

public interface IMatchRequestRepository {
	
	public MatchRequest findById(int id);
	
	public void save(MatchRequest matchRequest);
	
}

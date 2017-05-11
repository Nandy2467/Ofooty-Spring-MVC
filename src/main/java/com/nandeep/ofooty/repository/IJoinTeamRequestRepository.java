package com.nandeep.ofooty.repository;

import java.util.List;

import com.nandeep.ofooty.modal.JoinTeamRequest;

public interface IJoinTeamRequestRepository {
	
	public void save(JoinTeamRequest joinTeamRequest);
	public void delete(JoinTeamRequest joinTeamRequest);
	public JoinTeamRequest findByRequestId(int requestId);
	public List<JoinTeamRequest> findAll();
	
}

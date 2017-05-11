package com.nandeep.ofooty.service;

import java.util.List;

import com.nandeep.ofooty.util.MatchRequestUtil;

public interface IMatchRequestService {
	
	public void createMatchRequest(int teamId, String msg);
	
	public void updateMatchRequest(int reqId);
	
	public List<MatchRequestUtil> getActiveRequestByTeam(int teamId);
	
	public void addPlayerToPoll(int pollid, int playerId);
}

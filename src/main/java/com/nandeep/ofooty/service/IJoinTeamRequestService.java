package com.nandeep.ofooty.service;

import java.util.List;

import com.nandeep.ofooty.modal.JoinTeamRequest;
import com.nandeep.ofooty.modal.Player;

public interface IJoinTeamRequestService {

	public void createJoinTeamRequest(Player sentBy, String teamId);
	
	public boolean approveJoinTeamRequest(int joinTeamRequestId, int payerId);
	
	public boolean rejectJoinTeamRequest(int joinTeamRequestId, int playerId);
	
	public List<JoinTeamRequest> getJoinRequestByPlayer(Player player);
	
	public List<JoinTeamRequest> getJoinRequestByTeam(String teamid);
	
}

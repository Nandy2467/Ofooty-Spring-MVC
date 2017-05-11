package com.nandeep.ofooty.service;

import java.util.List;

import com.nandeep.ofooty.modal.Player;
import com.nandeep.ofooty.modal.Team;
import com.nandeep.ofooty.util.TeamList;

public interface ITeamService {
	
	public Team createTeam(String teamName, Player manager, String zip, String city, String state, String country);
	
	public Team getTeamByTeamName(String teamName);
	
	public void deleteTeam(Team team);
	
	public List<Team> getTeamsByZip(String zip);
	
	public Long getTeamsCount();
	
	public boolean checkTeamExists(String teamname);
	
	public List<TeamList> getTeamsBySearchKey(String key, String val, int playerId);
	
	public Team getTeamById(int teamId);
	
	public void removePlayer(int teamId, int playerId);
	
}

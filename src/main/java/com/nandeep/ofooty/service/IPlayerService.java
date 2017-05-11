package com.nandeep.ofooty.service;

import java.util.List;

import com.nandeep.ofooty.modal.Player;
import com.nandeep.ofooty.util.TeamList;

public interface IPlayerService {
	
	public Player savePlayer(String firstName, String lastName, String mobile);
	
	public boolean isPlayerMobileDuplicate(String mobile);
	
	public Long getPlayersCount();
	
	public List<TeamList> getAllTeamByPlayer(Player player);
	
}

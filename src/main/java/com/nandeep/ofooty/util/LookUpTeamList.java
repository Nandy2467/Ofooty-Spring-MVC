package com.nandeep.ofooty.util;

public abstract class LookUpTeamList {
	
	@SuppressWarnings("unused")
	private TeamList teamList;
	
	public abstract TeamList createTeamList();
	
	public void setTeamList(TeamList teamList){
		this.teamList = teamList;
	}
}

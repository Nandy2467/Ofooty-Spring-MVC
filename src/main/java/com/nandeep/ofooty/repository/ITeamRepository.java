package com.nandeep.ofooty.repository;

import java.util.List;

import com.nandeep.ofooty.modal.Team;

public interface ITeamRepository {
	
	public void save(Team team);
	public List<Team> findAll();
	public Team findByTeamName(String teamName);
	public void delete(Team team);
	public Team findById(int teamId);
}

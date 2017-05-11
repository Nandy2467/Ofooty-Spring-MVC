package com.nandeep.ofooty.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nandeep.ofooty.modal.Address;
import com.nandeep.ofooty.modal.Player;
import com.nandeep.ofooty.modal.Team;
import com.nandeep.ofooty.repository.PlayerRepository;
import com.nandeep.ofooty.repository.TeamRepository;
import com.nandeep.ofooty.util.LookUpTeamList;
import com.nandeep.ofooty.util.TeamList;

@Service
@Transactional
public class TeamService implements ITeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private LookUpTeamList lookUpTeamList;
	
	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public Team createTeam(String teamName, Player manager, String zip, String city, String state, String country) {

		// save team
		Team team = new Team();
		team.setTeamName(teamName);
		team.setManager(manager);
		
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setCountry(country);
		address.setStreet("NA");
		address.setZip(zip);
		
		team.setAddress(address);
		team.getPlayers().add(manager);
		teamRepository.save(team);
		
		// add player to saved team and return the team
		team = teamRepository.findByTeamName(teamName);
		return team;
	}

	@Override
	public Team getTeamByTeamName(String teamName) {
		Team team = teamRepository.findByTeamName(teamName);
		return team;
	}

	@Override
	public void deleteTeam(Team team) {
		teamRepository.delete(team);
	}

	@Override
	public List<Team> getTeamsByZip(String zip){
		List<Team> teams = teamRepository.findByZip(zip);
		return teams;
	}

	@Override
	public Long getTeamsCount() {
		Long count = teamRepository.getTableCount();
		return count;
	}

	@Override
	public boolean checkTeamExists(String teamname) {
		Team team = teamRepository.findByTeamName(teamname);
		if(team == null){
			return false;
		} else{
			return true;
		}
	}

	@Override
	public List<TeamList> getTeamsBySearchKey(String key, String val, int playerID) {
		
		List<TeamList> teamList = new ArrayList<>();
		List<Team> teams = teamRepository.findBySearchKey(key, val);
		if(teams.size() > 0){
			for(Team t : teams){
				if(t.getPlayers().contains(playerRepository.findById(playerID))){
					// do not load that team
				} else {
					TeamList tl = lookUpTeamList.createTeamList();
					tl.setTeamId(t.getTeamId());
					tl.setTeamName(t.getTeamName());
					tl.setManager(t.getManager().getFirstName() + " " + t.getManager().getLastName());
					tl.setAddress(t.getAddress().getCity() + ", " + t.getAddress().getState() + " - "+ t.getAddress().getZip() + ", "+ t.getAddress().getCountry() + " Ph:" + t.getManager().getMobile());
					tl.setTeamSize(t.getPlayers().size());
					teamList.add(tl);
				}
			}
		}
		return teamList;
	}

	@Override
	public Team getTeamById(int teamId) {
		Team team = teamRepository.findById(teamId);
		return team;
	}

	@Override
	public void removePlayer(int teamId, int playerId) {
		Team team = teamRepository.findById(teamId);
		Player player = playerRepository.findById(playerId);
		team.getPlayers().remove(player);
	}
	
}

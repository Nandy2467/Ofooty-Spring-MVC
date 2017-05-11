package com.nandeep.ofooty.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nandeep.ofooty.modal.Player;
import com.nandeep.ofooty.modal.Team;
import com.nandeep.ofooty.repository.PlayerRepository;
import com.nandeep.ofooty.util.LookUpTeamList;
import com.nandeep.ofooty.util.TeamList;

@Service
@Transactional
public class PlayerService implements IPlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private LookUpTeamList lookUpTeamList;
	
	@Override
	public Player savePlayer(String firstName, String lastName, String mobile) {
		// Save Player
		Player player = new Player();
		player.setFirstName(firstName);
		player.setLastName(lastName);
		player.setMobile(mobile);
		playerRepository.save(player);
		
		player = playerRepository.findByMobile(mobile);
		return player;
	}

	@Override
	public boolean isPlayerMobileDuplicate(String mobile) {
		Player player = playerRepository.findByMobile(mobile);
		if(player == null){
			return false;
		}
		return true;
	}

	@Override
	public Long getPlayersCount() {
		Long count = playerRepository.getTableCount();
		return count;
	}

	@Override
	public List<TeamList> getAllTeamByPlayer(Player player) {
		
		List<TeamList> teamsOfPlayer = new ArrayList<>();
		Player latestPlayer = playerRepository.findById(player.getActorId());
		for(Team t : latestPlayer.getTeams()){
			TeamList teamList = lookUpTeamList.createTeamList();
			teamList.setTeamId(t.getTeamId());
			teamList.setManager(t.getManager().getFirstName() + " " + t.getManager().getLastName());
			teamList.setTeamName(t.getTeamName());
			teamList.setTeamSize(t.getPlayers().size());
			teamList.setAddress(t.getAddress().getCity() + ", " + t.getAddress().getState() + " - "+ t.getAddress().getZip() + ", "+ t.getAddress().getCountry());
			teamsOfPlayer.add(teamList);
		}
		return teamsOfPlayer;
	}


}

package com.nandeep.ofooty.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nandeep.ofooty.modal.JoinTeamRequest;
import com.nandeep.ofooty.modal.Player;
import com.nandeep.ofooty.modal.Team;
import com.nandeep.ofooty.repository.JoinTeamRequestRepository;
import com.nandeep.ofooty.repository.PlayerRepository;
import com.nandeep.ofooty.repository.TeamRepository;
import com.nandeep.ofooty.util.JoinTeamRequestStatus;

@Service
@Transactional
public class JoinTeamRequestService implements IJoinTeamRequestService {
	
	@Autowired
	private JoinTeamRequestRepository joinTeamRequestRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Override
	public List<JoinTeamRequest> getJoinRequestByPlayer(Player player) {
		List<JoinTeamRequest> jtRequest =  new ArrayList<>();
		for(JoinTeamRequest jtr : joinTeamRequestRepository.findAll()){
			if(player.getActorId() == jtr.getSentBy().getActorId()){
				jtRequest.add(jtr);
			}
		}
		return jtRequest;
	}

	@Override
	public List<JoinTeamRequest> getJoinRequestByTeam(String teamid) {
		Team team = teamRepository.findById(Integer.valueOf(teamid));
		List<JoinTeamRequest> jtRequest =  new ArrayList<>();
		for(JoinTeamRequest jtr : joinTeamRequestRepository.findAll()){
			if( team.getTeamId() == jtr.getSentTo().getTeamId() && jtr.getStatus().equals(JoinTeamRequestStatus.OPEN)){
				jtRequest.add(jtr);
			}
		}
		return jtRequest;
	}

	@Override
	public void createJoinTeamRequest(Player sentBy, String sentTo) {
		JoinTeamRequest jtr = new JoinTeamRequest();
		jtr.setSentBy(playerRepository.findById(sentBy.getActorId()));
		jtr.setSentTo(teamRepository.findById(Integer.valueOf(sentTo)));
		jtr.setStatus(JoinTeamRequestStatus.OPEN);
		joinTeamRequestRepository.save(jtr);
	}

	@Override
	public boolean approveJoinTeamRequest(int joinTeamRequestId, int playerId) {
		JoinTeamRequest jtr = joinTeamRequestRepository.findByRequestId(joinTeamRequestId);
		if(jtr.getSentTo().getManager().getActorId() == playerId){
			Player playerTobeAdded = playerRepository.findById(jtr.getSentBy().getActorId());
			Team teamToAddNewPlayer = teamRepository.findById(jtr.getSentTo().getTeamId());
			teamToAddNewPlayer.getPlayers().add(playerTobeAdded);
			jtr.setStatus(JoinTeamRequestStatus.APPROVED);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean rejectJoinTeamRequest(int joinTeamRequestId, int playerId) {
		JoinTeamRequest jtr = joinTeamRequestRepository.findByRequestId(joinTeamRequestId);
		if(jtr.getSentTo().getManager().getActorId() == playerId){
			jtr.setStatus(JoinTeamRequestStatus.REJECTED);
			return true;
		}
		return false;
	}
	
}

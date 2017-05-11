package com.nandeep.ofooty.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nandeep.ofooty.modal.MatchRequest;
import com.nandeep.ofooty.modal.Player;
import com.nandeep.ofooty.modal.Team;
import com.nandeep.ofooty.repository.MatchRequestRepository;
import com.nandeep.ofooty.repository.PlayerRepository;
import com.nandeep.ofooty.repository.TeamRepository;
import com.nandeep.ofooty.util.LookUpMatchRequestUtil;
import com.nandeep.ofooty.util.MatchRequestStatus;
import com.nandeep.ofooty.util.MatchRequestUtil;

@Service
@Transactional
public class MatchRequestService implements IMatchRequestService {

	@Autowired
	private MatchRequestRepository mrRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private LookUpMatchRequestUtil lookUpMRUtil;
	
	@Autowired
	private PlayerRepository playerRepo;
	
	@Override
	public void createMatchRequest(int teamId, String msg) {
		MatchRequest mr = new MatchRequest();
		mr.setReqStatus(MatchRequestStatus.OPEN);
		mr.setReqMessage(msg);
		Team team = teamRepo.findById(teamId);
		mr.setTeam(team);
		mrRepo.save(mr);
	}

	@Override
	public void updateMatchRequest(int reqId) {
		MatchRequest mr = mrRepo.findById(reqId);
		mr.setReqStatus(MatchRequestStatus.CLOSED);
	}

	@Override
	public List<MatchRequestUtil> getActiveRequestByTeam(int teamId) {
		List<MatchRequestUtil> mrUtilList = new ArrayList<>();
		List<MatchRequest> mrList = mrRepo.getActiveMatchRequest(teamId);
		if(mrList.size() > 0){
			for(MatchRequest m : mrList){
				MatchRequestUtil mru = lookUpMRUtil.createMRUtil();
				mru.setMatchRequest(m);
				mru.setPlayerCount(m.getPlayersList().size());
				mrUtilList.add(mru);
			}
		}
		return mrUtilList;
	}

	@Override
	public void addPlayerToPoll(int pollid, int playerId) {
		Player player = playerRepo.findById(playerId);
		MatchRequest mr = mrRepo.findById(pollid);
		boolean flag = false;
		for(Player p : mr.getPlayersList()){
			if(p.getActorId() == playerId){
				flag = true;
			}
		}
		
		if(flag){
			// do nothing
		} else {
			mr.getPlayersList().add(player);
		}
	}

}

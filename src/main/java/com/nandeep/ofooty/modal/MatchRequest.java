package com.nandeep.ofooty.modal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nandeep.ofooty.util.MatchRequestStatus;


@Entity
@Table(name="matchrequest")
public class MatchRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mrid")
	private int mrId;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="team")
	private Team team;
	
	@Enumerated(EnumType.STRING)
	@Column(name="reqstatus")
	private MatchRequestStatus reqStatus;
	
	@Column(name="reqmessage")
	private String reqMessage;
	
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="matchrequest_players", joinColumns=@JoinColumn(name="matchrequest"),
	inverseJoinColumns=@JoinColumn(name="player"))
	private List<Player> playersList = new ArrayList<>();


	public MatchRequest() {
	}


	public int getMrId() {
		return mrId;
	}


	public void setMrId(int mrId) {
		this.mrId = mrId;
	}


	public Team getTeam() {
		return team;
	}


	public void setTeam(Team team) {
		this.team = team;
	}

	public MatchRequestStatus getReqStatus() {
		return reqStatus;
	}


	public void setReqStatus(MatchRequestStatus reqStatus) {
		this.reqStatus = reqStatus;
	}


	public String getReqMessage() {
		return reqMessage;
	}


	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}


	public List<Player> getPlayersList() {
		return playersList;
	}


	public void setPlayersList(List<Player> playersList) {
		this.playersList = playersList;
	} 
		
	
}

package com.nandeep.ofooty.modal;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nandeep.ofooty.util.JoinTeamRequestStatus;

@Entity
@Table(name = "jointeamrequests")
public class JoinTeamRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "requestid")
	private int requestId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "sentby")
	@JsonManagedReference
	private Player sentBy;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "sentto")
	@JsonManagedReference
	private Team sentTo;

	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private JoinTeamRequestStatus status;
	
	public JoinTeamRequest() {
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public Player getSentBy() {
		return sentBy;
	}

	public void setSentBy(Player sentBy) {
		this.sentBy = sentBy;
	}

	public Team getSentTo() {
		return sentTo;
	}

	public void setSentTo(Team sentTo) {
		this.sentTo = sentTo;
	}

	public JoinTeamRequestStatus getStatus() {
		return status;
	}

	public void setStatus(JoinTeamRequestStatus status) {
		this.status = status;
	}
}

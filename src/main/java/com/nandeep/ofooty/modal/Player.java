package com.nandeep.ofooty.modal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;



@Entity
@DiscriminatorValue(value="PLAYER")
@Table(name="players")
public class Player extends Actor {


	@Column(name="firstname", length=50, nullable=false)
	private String firstName;
	
	@Column(name="lastname", length=50, nullable=false)
	private String lastName;

	@Column(name="mobile", length=10, unique=true)
	private String mobile;

	
	@ManyToMany(mappedBy="players", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference
	private List<Team> teams = new ArrayList<>();

	public Player() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	
}

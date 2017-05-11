package com.nandeep.ofooty.modal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "gcrews")
@DiscriminatorValue(value="GCREW")
public class GCrew extends Actor {

	@Column(name = "firstname", length = 50, nullable = false)
	private String firstName;

	@Column(name = "lastname", length = 50, nullable = false)
	private String lastName;

	@Column(name = "mobile", length = 10, unique = true)
	private String mobile;

	@OneToOne(mappedBy = "gcrew")
	@JsonManagedReference
	private Ground ground;
	

	public GCrew() {
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

	public Ground getGround() {
		return ground;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

}

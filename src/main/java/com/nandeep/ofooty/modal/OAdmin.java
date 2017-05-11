package com.nandeep.ofooty.modal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "oadmins")
@DiscriminatorValue(value= "OADMIN")
public class OAdmin extends Actor {

	@Column(name = "firstname", length = 50, nullable = false)
	private String firstName;

	@Column(name = "lastname", length = 50, nullable = false)
	private String lastName;

	@Column(name = "mobile", length = 10, unique = true)
	private String mobile;

	public OAdmin() {
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

}

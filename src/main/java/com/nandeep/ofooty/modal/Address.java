package com.nandeep.ofooty.modal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	@Column(name="street", length=500)
	private String street;
	
	@Column(name="city", length=50)
	private String city;
	
	@Column(name="state", length=50)
	private String state;
	
	@Column(name="country", length=50)
	private String country;
	
	@Column(name="zip")
	private String zip;
	
	public Address(){
		
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
}

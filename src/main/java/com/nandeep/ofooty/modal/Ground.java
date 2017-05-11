package com.nandeep.ofooty.modal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "grounds")
public class Ground {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "groundid")
	private int groundId;

	@Column(name = "groundname", nullable = false)
	private String groundName;

	@Embedded
	private Address address;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "gcrew")
	@JsonBackReference
	private GCrew gcrew;
	
	@Column(name="isenabled")
	private boolean isEnabled;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="ground_groundavailability", joinColumns=@JoinColumn(name="ground"),
	inverseJoinColumns=@JoinColumn(name="groundavailability"))
	@JsonBackReference
	private List<GroundAvailability> groundAvailabilityList = new ArrayList<>();
	
	
	public Ground() {
	}

	public int getGroundId() {
		return groundId;
	}

	public void setGroundId(int groundId) {
		this.groundId = groundId;
	}

	public String getGroundName() {
		return groundName;
	}

	public void setGroundName(String groundName) {
		this.groundName = groundName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public GCrew getGcrew() {
		return gcrew;
	}

	public void setGcrew(GCrew gcrew) {
		this.gcrew = gcrew;
	}

	/*public boolean isEnabled() {
		return isEnabled;
	}*/
	
	public boolean getIsEnabled() {
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public List<GroundAvailability> getGroundAvailabilityList() {
		return groundAvailabilityList;
	}

	public void setGroundAvailabilityList(List<GroundAvailability> groundAvailabilityList) {
		this.groundAvailabilityList = groundAvailabilityList;
	}
	
}

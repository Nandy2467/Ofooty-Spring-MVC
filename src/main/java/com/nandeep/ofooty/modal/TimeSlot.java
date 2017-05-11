package com.nandeep.ofooty.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nandeep.ofooty.util.Slot;
import com.nandeep.ofooty.util.TimeSlotStatus;



@Entity
@Table(name="timeslots")
public class TimeSlot {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="timeslotid")
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="slot")
	private Slot slot;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private TimeSlotStatus status;

	public TimeSlot() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public TimeSlotStatus getStatus() {
		return status;
	}

	public void setStatus(TimeSlotStatus status) {
		this.status = status;
	}	
	
}

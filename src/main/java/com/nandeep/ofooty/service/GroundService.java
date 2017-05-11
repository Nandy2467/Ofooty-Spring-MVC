package com.nandeep.ofooty.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nandeep.ofooty.modal.Address;
import com.nandeep.ofooty.modal.GCrew;
import com.nandeep.ofooty.modal.Ground;
import com.nandeep.ofooty.modal.GroundAvailability;
import com.nandeep.ofooty.modal.TimeSlot;
import com.nandeep.ofooty.repository.GroundRepository;
import com.nandeep.ofooty.util.EmailSender;
import com.nandeep.ofooty.util.Slot;
import com.nandeep.ofooty.util.TimeSlotStatus;

@Service
@Transactional
public class GroundService implements IGroundService {

	@Autowired
	private GroundRepository groundRepository;
	
	@Override
	public void addGround(String name, String street, String city, String state, String country, String zip,
			GCrew gcrew) {
		Ground ground = new Ground();
		ground.setGroundName(name);
		
		Address address = new Address();
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setCountry(country);
		address.setZip(zip);
		
		ground.setAddress(address);
		ground.setGcrew(gcrew);
		ground.setEnabled(false);
		
		groundRepository.save(ground);
		
		// email sender
		EmailSender.sendEmail("New Ground", "New Ground Request");
		
	}

	@Override
	public List<Ground> getGroundBySearchKey(String key, String value) {
		List<Ground> grounds = groundRepository.findGroundsByKeyValueSearch(key, value);
		return grounds;
	}

	@Override
	public void disableGround(Ground ground) {
		groundRepository.reject(ground);
	}

	@Override
	public Long getGroundsCount() {
		Long count = groundRepository.getTableCount();
		return count;
	}

	@Override
	public List<GCrew> getNewGrounds() {
		List<Ground> grounds = groundRepository.getGroundsNotEnabled();
		List<GCrew> newgroundsList = new ArrayList<>();
		for(Ground g : grounds){
			newgroundsList.add(g.getGcrew());
		}
		return newgroundsList;
	}

	@Override
	public void enableGround(int gid) {
		Ground ground = groundRepository.findByID(gid);
		if(ground != null){
			ground.setEnabled(true);
		}
	}

	@Override
	public GroundAvailability getGroundAvailabilityByDate(Ground ground, String date) {
		
		// flag to check whether date is already set
		Ground latestGround = groundRepository.findByID(ground.getGroundId());
		boolean flag = false;
		GroundAvailability gaPreExist = null;
		for(GroundAvailability g : latestGround.getGroundAvailabilityList()){
			if(g.getDate().equals(date)){
				gaPreExist = g;
				flag = true;
			}
		}
		
		if(!flag){
			// set new availability
			GroundAvailability ga = new GroundAvailability();
			ga.setDate(date);
			for(Slot s : Slot.values()){
				TimeSlot ts = new TimeSlot();
				ts.setSlot(s);
				ts.setStatus(TimeSlotStatus.CLOSED);
				ga.getTimeSlots().add(ts);
			}
			latestGround.getGroundAvailabilityList().add(ga);
			return ga;
		} else {
			return gaPreExist;
		}
		
	}

	@Override
	public GroundAvailability updateGroundAvailabilityStatus(Ground ground, String availId, String date, String slot) {
		Ground latestGround = groundRepository.findByID(ground.getGroundId());
		GroundAvailability groundavail = null;
		for(GroundAvailability ga : latestGround.getGroundAvailabilityList()){
			if(ga.getId() == Integer.valueOf(availId) && ga.getDate().equals(date) ){
				groundavail = ga;
			}
		}
		
		TimeSlot tsToUpdate = null;
		if(groundavail != null){
			for(TimeSlot ts : groundavail.getTimeSlots()){
				if(ts.getSlot().name().equals(slot)){
					tsToUpdate = ts;
				}
			}
		}
		
		if(tsToUpdate != null && !(tsToUpdate.getStatus().equals(TimeSlotStatus.BOOKED))){
			if(tsToUpdate.getStatus().name().equals(TimeSlotStatus.CLOSED.name())){
				tsToUpdate.setStatus(TimeSlotStatus.OPEN);
			} else {
				tsToUpdate.setStatus(TimeSlotStatus.CLOSED);
			}
		}
		return groundavail;
	}

}

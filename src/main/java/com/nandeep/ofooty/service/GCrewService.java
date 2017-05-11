package com.nandeep.ofooty.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nandeep.ofooty.modal.GCrew;
import com.nandeep.ofooty.repository.GCrewRepository;

@Service
@Transactional
public class GCrewService implements IGCrewService{

	@Autowired
	private GCrewRepository gcrewRepository;
	
	@Override
	public GCrew saveGCrew(String firstName, String lastName, String mobile) {
		GCrew gCrew = new GCrew();
		gCrew.setFirstName(firstName);
		gCrew.setLastName(lastName);
		gCrew.setMobile(mobile);
		gcrewRepository.save(gCrew);
		return gCrew;
	}

	@Override
	public boolean isGCrewMobileDuplicate(String mobile) {
		GCrew gcrew = gcrewRepository.findByMobile(mobile);
		if(gcrew == null){
			return false;
		}
		return true;
	}

}

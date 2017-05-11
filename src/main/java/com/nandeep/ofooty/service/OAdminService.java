package com.nandeep.ofooty.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nandeep.ofooty.modal.OAdmin;
import com.nandeep.ofooty.repository.OAdminRepository;

@Service
@Transactional
public class OAdminService implements IOAdminService{

	@Autowired
	private OAdminRepository oAdminRepository;
	
	@Override
	public OAdmin saveOAdmin(String firstName, String lastName, String mobile) {
		OAdmin oAdmin = new OAdmin();
		oAdmin.setFirstName(firstName);
		oAdmin.setLastName(lastName);
		oAdmin.setMobile(mobile);
		oAdminRepository.save(oAdmin);
		return oAdmin;
	}

	@Override
	public boolean isOAdminMobileDuplicate(String mobile) {
		OAdmin oadmin = oAdminRepository.findByMobile(mobile);
		if(oadmin == null){
			return false;
		}
		return true;
	}

}

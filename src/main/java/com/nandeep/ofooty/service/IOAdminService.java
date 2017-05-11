package com.nandeep.ofooty.service;

import com.nandeep.ofooty.modal.OAdmin;


public interface IOAdminService {

	public OAdmin saveOAdmin(String firstName, String lastName, String mobile);
	
	public boolean isOAdminMobileDuplicate(String mobile);
}

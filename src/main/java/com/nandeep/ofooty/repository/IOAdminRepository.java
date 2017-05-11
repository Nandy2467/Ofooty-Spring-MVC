package com.nandeep.ofooty.repository;

import java.util.List;

import com.nandeep.ofooty.modal.OAdmin;


public interface IOAdminRepository {
	public void save(OAdmin oadmin);
	public void delete(OAdmin oadmin);
	public List<OAdmin> findAll();
	public OAdmin findById(int oadminId);
}

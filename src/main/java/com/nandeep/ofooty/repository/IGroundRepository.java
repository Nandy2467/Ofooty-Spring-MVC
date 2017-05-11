package com.nandeep.ofooty.repository;

import java.util.List;

import com.nandeep.ofooty.modal.Ground;

public interface IGroundRepository {
	
	public void save(Ground ground);
	public List<Ground> findAll();
	public Ground findByGroundName(String groundName);
	public void delete(Ground ground);
	public void approve(Ground ground);
	public void reject(Ground ground);
	public List<Ground> findAllFalseGrounds();
	public List<Ground> findAllTrueGrounds();
	public Ground findByID(int id);
}

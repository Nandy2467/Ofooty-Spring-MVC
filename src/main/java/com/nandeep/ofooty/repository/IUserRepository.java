package com.nandeep.ofooty.repository;

import java.util.List;

import com.nandeep.ofooty.modal.User;

public interface IUserRepository {
	
	public void save(User user);
	public User findByUserName(String userName);
	public void delete(User user);
	public List<User> findAll();
	
}

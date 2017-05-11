package com.nandeep.ofooty.service;

import com.nandeep.ofooty.modal.Actor;
import com.nandeep.ofooty.modal.User;
import com.nandeep.ofooty.util.Role;

public interface IUserService {
	
	public User saveUser(String userName, String password, Role role, Actor actor);
	public User getUserByUserName(String userName);
	
	public boolean checkDuplicateUserName(String userName);
}	

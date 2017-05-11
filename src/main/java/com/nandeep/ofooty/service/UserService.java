package com.nandeep.ofooty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nandeep.ofooty.modal.Actor;
import com.nandeep.ofooty.modal.User;
import com.nandeep.ofooty.repository.UserRepository;
import com.nandeep.ofooty.util.Role;

@Service
@Transactional
public class UserService implements IUserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User saveUser(String userName, String password, Role role, Actor actor) {
		// Save User
		User user = new User();
		user.setUserName(userName);
		user.setPassword(passwordEncoder.encode(password));
		user.setUserRole(role);;
		user.setActor(actor);
		userRepository.save(user);
		
		// add role to the saved User and return
		user = userRepository.findByUserName(userName);
		return user;
	}
	
	@Override
	public User getUserByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		return user;
	}

	@Override
	public boolean checkDuplicateUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		if(user == null){
			return false;
		}
		return true;
	}
	
}

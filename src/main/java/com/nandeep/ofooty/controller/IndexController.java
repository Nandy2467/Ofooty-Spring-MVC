package com.nandeep.ofooty.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nandeep.ofooty.modal.User;
import com.nandeep.ofooty.service.IUserService;
import com.nandeep.ofooty.util.Role;


@Controller
public class IndexController {
	
	@Autowired
	private IUserService iuserService;
	
	/***
	 * Redirect to index page
	 * @return
	 */
	@RequestMapping(value="/")
	public String goIndex(){
		return "jsp/log-in";
	}
	
	/***
	 * Decide welcome page based on logged in user
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/welcome")
	public String goWelcome(Principal principal){
		String redirectURL = "";
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Role role = user.getUserRole();
		if(role.equals(Role.PLAYER)){
			redirectURL = "/welcome-player";
		}
		if(role.equals(Role.GCREW)){
			redirectURL= "/welcome-gcrew";
		}
		if(role.equals(Role.OADMIN)){
			redirectURL= "/welcome-oadmin";
		}
		return "redirect:"+ redirectURL;
	}
	
	/***
	 * Custom access denied page
	 * @param model
	 * @param ex
	 * @return
	 */
	@RequestMapping(value="/access-issue")
	public String goAccessDeined(Model model, Exception ex){
		return "thymeleaf/noaccess";
	}
	
}

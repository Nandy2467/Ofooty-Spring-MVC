package com.nandeep.ofooty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nandeep.ofooty.modal.GCrew;
import com.nandeep.ofooty.modal.Player;
import com.nandeep.ofooty.modal.User;
import com.nandeep.ofooty.service.IGCrewService;
import com.nandeep.ofooty.service.IOAdminService;
import com.nandeep.ofooty.service.IPlayerService;
import com.nandeep.ofooty.service.IUserService;
import com.nandeep.ofooty.util.Role;

@Controller
public class UserController {

	@Autowired
	private IUserService iUserService;

	@Autowired
	private IPlayerService iPlayerService;

	@Autowired
	private IGCrewService iGCrewService;

	@Autowired
	private IOAdminService iOAdminService;

	/***
	 * Method to check user name already exists.
	 * 
	 * @param username
	 * @return success or error message
	 */
	@RequestMapping(value = "/username-check", method = RequestMethod.POST, produces = "text/plain")
	@ResponseBody
	public String isUserNameDuplicate(@RequestParam String username) {
		try {
			boolean isDuplicate = iUserService.checkDuplicateUserName(username);
			if (isDuplicate) {
				return "Username already in use. Try another.";
			} else {
				return "";
			}
		} catch (Exception e) {
			System.out.println("Unable to check duplicate user name");
			return "";
		}
	}

	/***
	 * Method to check mobile already exists.
	 * 
	 * @param mobile
	 * @return success or error message 
	 */
	@RequestMapping(value = "/mobile-check", method = RequestMethod.POST, produces = "text/plain")
	@ResponseBody
	public String isMobileDuplicate(@RequestParam String mobile) {

		try {
			boolean inPlayers = iPlayerService.isPlayerMobileDuplicate(mobile);
			boolean inGCrews = iGCrewService.isGCrewMobileDuplicate(mobile);
			boolean inOAdmins = iOAdminService.isOAdminMobileDuplicate(mobile);
			if (inPlayers || inGCrews || inOAdmins) {
				return "Mobile already in use. Try another.";
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	/***
	 * Method to register player and user account for the registered player 
	 * @param firstname
	 * @param lastname
	 * @param mobile
	 * @param username
	 * @param password
	 * @return success or error message
	 */
	@RequestMapping(value = "/register-player", method = RequestMethod.POST, produces = "text/plain")
	@ResponseBody
	public String registerPlayer(@RequestParam String firstname, @RequestParam String lastname,
			@RequestParam String mobile, @RequestParam String username, @RequestParam String password) {
		try {
			Player player = iPlayerService.savePlayer(firstname, lastname, mobile);
			User user = iUserService.saveUser(username, password, Role.PLAYER, player);
			System.out.println(user.getUserName() + " User Account Registered");
			return "Player Registration Successfull.";
		} catch (Exception e) {
			return "Unable to process your request now. Try later.";
		}
	}
	
	
	
	/***
	 * Method to register player and user account for the registered player 
	 * @param firstname
	 * @param lastname
	 * @param mobile
	 * @param username
	 * @param password
	 * @return success or error message
	 */
	@RequestMapping(value = "/register-gcrew", method = RequestMethod.POST, produces = "text/plain")
	@ResponseBody
	public String registerGCrew(@RequestParam String firstname, @RequestParam String lastname,
			@RequestParam String mobile, @RequestParam String username, @RequestParam String password) {
		try {
			GCrew gcrew = iGCrewService.saveGCrew(firstname, lastname, mobile);
			User user = iUserService.saveUser(username, password, Role.GCREW, gcrew);
			System.out.println(user.getUserName() + " User Account Registered");
			return "GCrew Registration Successfull.";
		} catch (Exception e) {
			return "Unable to process your request now. Try later.";
		}
	}

}

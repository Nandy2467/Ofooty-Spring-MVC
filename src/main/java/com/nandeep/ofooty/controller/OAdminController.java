package com.nandeep.ofooty.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nandeep.ofooty.modal.GCrew;
import com.nandeep.ofooty.modal.OAdmin;
import com.nandeep.ofooty.modal.User;
import com.nandeep.ofooty.service.IGroundService;
import com.nandeep.ofooty.service.IPlayerService;
import com.nandeep.ofooty.service.ITeamService;
import com.nandeep.ofooty.service.IUserService;
import com.nandeep.ofooty.util.KeyFacts;

@Controller
public class OAdminController {

	@Autowired
	private IUserService iuserService;

	@Autowired
	private IPlayerService iplayerService;

	@Autowired
	private IGroundService igroundService;

	@Autowired
	private ITeamService iteamService;

	@Autowired
	private KeyFacts keyfacts;

	/**
	 * Method to display landing page for Oadmin
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/welcome-oadmin")
	@PreAuthorize("hasAnyAuthority('OADMIN')")
	public String goGCrewHome(Model model, Principal principal) {
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		OAdmin oadmin = (OAdmin) user.getActor();
		if (oadmin != null) {
			model.addAttribute("oadmin", " " + oadmin.getFirstName() + " " + oadmin.getLastName());
		}
		return "jsp/oadmin";
	}

	/***
	 * Method get key facts: player count, team count and ground count
	 * 
	 * @return
	 */
	@RequestMapping(value = "/keyfacts-update", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('OADMIN')")
	public KeyFacts getUpdatedKeyFacts() {
		keyfacts.setPlayerCount(iplayerService.getPlayersCount());
		keyfacts.setTeamCount(iteamService.getTeamsCount());
		keyfacts.setGroundCount(igroundService.getGroundsCount());
		return keyfacts;
	}

	/***
	 * Method to get all new grounds for approval
	 * @return
	 */
	@RequestMapping(value = "/get-newgrounds", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('OADMIN')")
	public List<GCrew> getNewGrounds() {
		List<GCrew> newGrounds = igroundService.getNewGrounds();
		return newGrounds;
	}

	
	/***
	 * Method to enable ground
	 * @param gid
	 * @return
	 */
	@RequestMapping(value="/enable-newgrounds",method = RequestMethod.POST, produces = "text/plain")
	@ResponseBody
	public String approveGroundRequest(@RequestParam String gid){
		igroundService.enableGround(Integer.valueOf(gid));
		return "Ground was successfully approved.";
	}
	
}

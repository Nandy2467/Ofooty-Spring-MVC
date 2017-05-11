package com.nandeep.ofooty.controller;

import java.io.File;
import java.security.Principal;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nandeep.ofooty.modal.Player;
import com.nandeep.ofooty.modal.Team;
import com.nandeep.ofooty.modal.User;
import com.nandeep.ofooty.service.IJoinTeamRequestService;
import com.nandeep.ofooty.service.IMultipartFileUploadService;
import com.nandeep.ofooty.service.IPlayerService;
import com.nandeep.ofooty.service.ITeamService;
import com.nandeep.ofooty.service.IUserService;
import com.nandeep.ofooty.util.ProfilePicPath;
import com.nandeep.ofooty.util.Role;
import com.nandeep.ofooty.util.TeamList;

@Controller
public class PlayerController {

	@Autowired
	private IUserService iuserService;
	
	@Autowired
	private ITeamService iteamService;
	
	@Autowired
	private IPlayerService iplayerService;
	
	@Autowired
	private IJoinTeamRequestService ijtrService;
	
	@Autowired
	private IMultipartFileUploadService imfUploadService;
	
	@Autowired
	private ProfilePicPath proPicPath;
	
	/***
	 * Method to load welcome page for player 
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/welcome-player")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	public String goPlayerHome(Model model, Principal principal){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		if (player != null) {
			model.addAttribute("player", " " + player.getFirstName() + " " + player.getLastName());
		}
		
		File propic = imfUploadService.load(Role.PLAYER.toString() + player.getActorId());
		if(propic.exists()){
			String src = proPicPath.getProPicPath() + Role.PLAYER.toString() + player.getActorId() + proPicPath.getPicType();
			model.addAttribute("propic", src);
		} else {
			String src = proPicPath.getProPicPath() + Role.PLAYER.toString() + proPicPath.getPicType();
			model.addAttribute("propic", src);
		}
		
		return "jsp/player";
	}
	
	/***
	 * method to check duplicate team name
	 * @param teamname
	 * @return
	 */
	@RequestMapping(value="/teamname-check", method= RequestMethod.POST, produces="text/plain")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	public String validateDuplicateTeamName(@RequestParam String teamname){
		try{
			boolean teamExists = iteamService.checkTeamExists(teamname);
			if(teamExists){
				return "Team Name already in use. Try another.";
			} else {
				return "";
			}
		}catch (HibernateException e) {
			return "";
		}
	}
	
	/***
	 * Method to create new team
	 * @param teamname
	 * @param zip
	 * @param city
	 * @param state
	 * @param country
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/create-team", method= RequestMethod.POST, produces="text/plain")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	public String createTeam(@RequestParam String teamname, @RequestParam String zip,
			@RequestParam String city, @RequestParam String state,
			@RequestParam String country, Principal principal){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		Team team	= iteamService.createTeam(teamname, player, zip, city, state, country);
		if(team != null){
			return "Team creation successfull.";
		} else{
			return "Unable to create team now. Try later.";
		}
	}
	
	/***
	 * Method to teams by player
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/get-myteams", method= RequestMethod.POST, produces="application/json")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	public List<TeamList> getsTeamByPlayer(Principal principal){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		List<TeamList> myTeams = iplayerService.getAllTeamByPlayer(player); 
		return myTeams;
	}
	
	
	/**
	 * Method to get team by search
	 * @param key
	 * @param val
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/search-team", method= RequestMethod.POST, produces="application/json")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	public List<TeamList> searchTeamByKeyVal(@RequestParam String key, @RequestParam String val, Principal principal){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		List<TeamList> searchResult = iteamService.getTeamsBySearchKey(key, val, player.getActorId());
		return searchResult;
	}

	
	/**
	 * Method to join team
	 * @param teamid
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/join-team", method= RequestMethod.POST, produces="text/plain")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	public String joinTeamRequest(@RequestParam String teamid, Principal principal){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		try{
			ijtrService.createJoinTeamRequest(player, teamid);
			return "Request sent successfully.";
		}catch (Exception e) {
			return "Unable to send request now. Try later.";
		}
	}
	
	/**
	 * Method to upload pic
	 * @param file
	 * @param principal
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/upload-pic", method=RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	public String uploadProfilePicture(@RequestParam("file") MultipartFile file, Principal principal, RedirectAttributes redirectAttributes){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		
		imfUploadService.store(file, player.getActorId());
		
		return "redirect:/welcome-player";
	}
	
}

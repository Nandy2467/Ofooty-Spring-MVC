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

import com.nandeep.ofooty.modal.JoinTeamRequest;
import com.nandeep.ofooty.modal.Player;
import com.nandeep.ofooty.modal.Team;
import com.nandeep.ofooty.modal.User;
import com.nandeep.ofooty.service.IJoinTeamRequestService;
import com.nandeep.ofooty.service.IMatchRequestService;
import com.nandeep.ofooty.service.ITeamService;
import com.nandeep.ofooty.service.IUserService;
import com.nandeep.ofooty.util.MatchRequestUtil;

@Controller
public class TeamController {

	@Autowired
	private IUserService iuserService;

	@Autowired
	private ITeamService iteamService;

	@Autowired
	private IJoinTeamRequestService ijtrService;
	
	@Autowired
	private IMatchRequestService imrService;

	/**
	 * Method to load team page based on manager and player 
	 * @param tid
	 * @param principal
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/in-team", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	public String goToTeamHome(@RequestParam String tid, Principal principal, Model model) {
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		Team team = iteamService.getTeamById(Integer.valueOf(tid));
		if (team.getManager().getActorId() == player.getActorId()) {
			model.addAttribute("isManager", true);
		}
		model.addAttribute("player", player);
		model.addAttribute("team", team);
		return "jsp/team";
	}

	/***
	 * Method to new player request
	 * @param teamid
	 * @return
	 */
	@RequestMapping(value = "/get-joinrequests", method = RequestMethod.POST, produces = "application/json")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	@ResponseBody
	public List<JoinTeamRequest> getNewJoinRequest(@RequestParam String teamid) {
		List<JoinTeamRequest> jtrList = ijtrService.getJoinRequestByTeam(teamid);
		return jtrList;
	}

	/**
	 * method to accept new player request
	 * @param reqid
	 * @param principal
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accept-joinrequests", method = RequestMethod.POST, produces = "text/plain")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	@ResponseBody
	public String acceptJoinTeamRequest(@RequestParam int reqid, Principal principal, Model model) {
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		boolean res = ijtrService.approveJoinTeamRequest(reqid, player.getActorId());
		if (res) {
			return "Player joined successfully.";
		} else {
			return "Player unable to join now. Try later";
		}
	}

	/***
	 * Method to reject new player request
	 * @param reqid
	 * @param principal
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reject-joinrequests", method = RequestMethod.POST, produces = "text/plain")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	@ResponseBody
	public String rejectJoinTeamRequest(@RequestParam int reqid, Principal principal, Model model) {
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		boolean res = ijtrService.rejectJoinTeamRequest(reqid, player.getActorId());
		if (res) {
			return "Player rejected successfully.";
		} else {
			return "Unable to reject player now. Try later";
		}
	}

	
	/**
	 * method to get existing player
	 * @param teamid
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/get-oldplayers", method = RequestMethod.POST, produces = "application/json")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	@ResponseBody
	public List<Player> oldPlayersOfTeam(@RequestParam int teamid, Principal principal) {
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		Player player = (Player) user.getActor();
		Team team = iteamService.getTeamById(teamid);
		List<Player> oldplayers = team.getPlayers();
		oldplayers.remove(team.getManager());
		if (team.getManager().getActorId() == player.getActorId()) {
			return oldplayers;
		} else {
			return null;
		}
	}

	/**
	 * method to remove existing player
	 * @param teamid
	 * @param pid
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/remove-oldplayer", method = RequestMethod.POST, produces = "text/plain")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	@ResponseBody
	public String removeOldPlayer(@RequestParam int teamid, @RequestParam int pid, Principal principal) {
		try {
			String userName = principal.getName();
			User user = iuserService.getUserByUserName(userName);
			Player player = (Player) user.getActor();
			Team team = iteamService.getTeamById(teamid);
			if (team.getManager().getActorId() == player.getActorId()) {
				iteamService.removePlayer(teamid, pid);
			}
			return "Player removed successfully.";
		} catch (Exception e) {
			return "Unable to process your request. Try later.";
		}
	}

	
	/***
	 * Method to create Poll request
	 * @param teamid
	 * @param reqmsg
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/create-poll", method = RequestMethod.POST, produces = "text/plain")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	@ResponseBody
	public String createNewPoll(@RequestParam int teamid, @RequestParam String reqmsg, Principal principal) {
		try {
			String userName = principal.getName();
			User user = iuserService.getUserByUserName(userName);
			Player player = (Player) user.getActor();
			Team team = iteamService.getTeamById(teamid);
			if (team.getManager().getActorId() == player.getActorId()) {
				imrService.createMatchRequest(teamid, reqmsg);
			}
			return "Poll created successfully.";
		} catch (Exception e) {
			return "Unable to process your request. Try later.";
		}
	}
	
	
	/***
	 * Method to get all active request
	 * @param teamid
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/get-activepolls", method = RequestMethod.POST, produces = "application/json")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	@ResponseBody
	public List<MatchRequestUtil> getActivePolls(@RequestParam int teamid, Principal principal) {
		try {
			String userName = principal.getName();
			User user = iuserService.getUserByUserName(userName);
			@SuppressWarnings("unused")
			Player player = (Player) user.getActor();
			List<MatchRequestUtil> activeMRList = imrService.getActiveRequestByTeam(teamid);
			return activeMRList;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * Method to close poll
	 * @param teamid
	 * @param mrid
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/close-poll", method = RequestMethod.POST, produces = "text/plain")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	@ResponseBody
	public String closePoll(@RequestParam int teamid, @RequestParam int mrid, Principal principal) {
		try {
			String userName = principal.getName();
			User user = iuserService.getUserByUserName(userName);
			Player player = (Player) user.getActor();
			Team team = iteamService.getTeamById(teamid);
			if (team.getManager().getActorId() == player.getActorId()) {
				imrService.updateMatchRequest(mrid);
				return "Poll closed successfully.";
			} else {
				return "Unable to process your request. Try later.";
			}
			
		} catch (Exception e) {
			return "Unable to process your request. Try later.";
		}
	}
	
	/***
	 * Method to join the players consent to the poll
	 * @param pollid
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/join-activepolls", method = RequestMethod.POST, produces = "text/plain")
	@PreAuthorize("hasAnyAuthority('PLAYER')")
	@ResponseBody
	public String joinPoll(@RequestParam int pollid, Principal principal) {
		try {
			String userName = principal.getName();
			User user = iuserService.getUserByUserName(userName);
			Player player = (Player) user.getActor();
			imrService.addPlayerToPoll(pollid, player.getActorId());
			return "done";
		} catch (Exception e) {
			return "Unable to process your request. Try later.";
		}
	}
	
}

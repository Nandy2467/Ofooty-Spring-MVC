package com.nandeep.ofooty.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nandeep.ofooty.modal.GCrew;
import com.nandeep.ofooty.modal.Ground;
import com.nandeep.ofooty.modal.GroundAvailability;
import com.nandeep.ofooty.modal.User;
import com.nandeep.ofooty.service.IGroundService;
import com.nandeep.ofooty.service.IUserService;
import com.nandeep.ofooty.util.NextDate;


@Controller
public class GCrewController {

	@Autowired
	private IUserService iuserService;
	
	@Autowired
	private IGroundService iGroundService;
	
	@Autowired
	private NextDate nextDates;
	
	
	/***
	 * Load welcome page for Gcrew
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/welcome-gcrew")
	@PreAuthorize("hasAnyAuthority('GCREW')")
	public String goGCrewHome(Model model, Principal principal){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		GCrew gcrew  = (GCrew) user.getActor();
		if(gcrew != null){
			model.addAttribute("gcrew", " " + gcrew.getFirstName() + " " + gcrew.getLastName());
		}
		
		// check for ground
		Ground ground = gcrew.getGround();
		if(ground == null){
			// add nothing to model
		} else {
			model.addAttribute("ground", ground);
		}
		
		return "jsp/gcrew";
	}
	
	/***
	 * Method to add ground
	 * @param grdname
	 * @param street
	 * @param city
	 * @param state
	 * @param country
	 * @param zip
	 * @param principal
	 * @return
	 */
	@PostMapping("/add-ground")
	@PreAuthorize("hasAnyAuthority('GCREW')")
	public String addGroundAndGoGCrewHome(@RequestParam String grdname, 
			@RequestParam String street, @RequestParam String city,
			@RequestParam String state, @RequestParam String country,
			@RequestParam String zip, Principal principal){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		GCrew gcrew  = (GCrew) user.getActor();
		iGroundService.addGround(grdname, street, city, state, country, zip, gcrew);
		return "redirect:/welcome-gcrew";
	}
	
	
	/***
	 * Method to generate new dates 
	 * @return
	 */
	@RequestMapping(value="/next-dates", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('GCREW')")
	public List<String> getNextDates(){		
		return nextDates.getDateList();
	}
	
	/***
	 * Method to provide availability map for a date
	 * @param date
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/get-availmap", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('GCREW')")
	public GroundAvailability getGroundAvailMap(@RequestParam String date, Principal principal){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		GCrew gcrew  = (GCrew) user.getActor();
		GroundAvailability ga = iGroundService.getGroundAvailabilityByDate(gcrew.getGround(), date);
		return ga;
	}
	
	
	/**
	 * Method to update availability time slot
	 * @param id
	 * @param date
	 * @param slot
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/update-timeslot", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('GCREW')")
	public GroundAvailability updateGroundAvailMap(@RequestParam String id,
			@RequestParam String date, @RequestParam String slot, Principal principal){
		String userName = principal.getName();
		User user = iuserService.getUserByUserName(userName);
		GCrew gcrew  = (GCrew) user.getActor();
		GroundAvailability ga = iGroundService.updateGroundAvailabilityStatus(gcrew.getGround(), id, date, slot);
		return ga;
	}
	
}



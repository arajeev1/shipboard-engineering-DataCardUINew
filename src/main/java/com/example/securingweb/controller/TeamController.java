package com.example.securingweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.securingweb.entities.Team;
import com.example.securingweb.repository.TeamRepository;
import com.example.securingweb.validators.TeamValidator;

import jakarta.validation.Valid;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class TeamController extends BaseController{
	
	@Autowired private TeamRepository teamRepository;
	@Autowired private TeamValidator teamValidator;


	private static final String viewPrefix = "teams/";
	@Override
	protected String getHeaderTitle() {
		return "Manage Employees Teams";
	}
	
	
	@GetMapping(value="/teams")
	public String listTeam(Model model){
		List<Team> teams = teamRepository.findAll();
		model.addAttribute("teams", teams);
		return viewPrefix+"teams";
		
	}
	
	@GetMapping(value="/teams/new")
	public String createNewTeam(Model model){
		
		Team team = new Team();
		model.addAttribute("team", team);
		
		return viewPrefix+"create_team";
	}
	
	
	@PostMapping(path = "/teams/new") 
	public String newEventEntry( @Valid @ModelAttribute("team") Team team, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes){
		
		teamValidator.validate(team, result);
		if(result.hasErrors()){
			logger.debug("Team validate function has the error ---- REHAN");
			return viewPrefix+"create_team";
		}
		
		teamRepository.save(team);
		redirectAttributes.addFlashAttribute("info", "Team created successfully");
		return "redirect:/teams";
	}
	
	
	
	@GetMapping(path = "/teams/{id}") 
	public String editTeamEntryGet(@PathVariable Integer id, Model model){
		Team team = teamRepository.getOne(id);
		model.addAttribute("team", team);
		
		return viewPrefix+"edit_team";
	}
	
	
	@PostMapping(path = "/teams/{id}") 
	public String editTeamEntryPost(Model model, @ModelAttribute("team") Team team, RedirectAttributes redirectAttributes){
		
		teamRepository.save(team);
		redirectAttributes.addFlashAttribute("info", "Event modified successfully");
		return "redirect:/teams";
	}
	
	
	@GetMapping(value="/teams/del/{id}")
	public String deleteEventEntry(@Valid @ModelAttribute("team") Team team, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id)
	{
		team = teamRepository.getOne(id);
//		statusRepository.delete(empStatus);
//		statusValidator.validate(employeeStatus, result);
//		if(employeeScheduleRepository.findbyStatus(employeeStatus.getId()) != null){
		try {
			//			return "redirect:/events";
			//		}
			teamRepository.delete(team);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Team " + team.getName()+ " cannot be Deleted. It belongs to the Calender!!");
		}
		return "redirect:/teams";
	}
}



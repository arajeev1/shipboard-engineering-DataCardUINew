package com.example.securingweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.securingweb.entities.Application;
import com.example.securingweb.entities.Lab;
import com.example.securingweb.entities.Role;
import com.example.securingweb.entities.User;
import com.example.securingweb.repository.ApplicationRepository;
import com.example.securingweb.repository.LabRepository;
import com.example.securingweb.repository.RoleRepository;
import com.example.securingweb.repository.UserRepository;
import com.example.securingweb.validators.ApplicationValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class ApplicationController //extends BaseController{
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/*****
	@Autowired private ApplicationRepository appRepository;
	@Autowired private ApplicationValidator appValidator;
	//@Autowired protected SecurityService securityService;
	@Autowired protected RoleRepository roleRepository;
	@Autowired protected UserRepository userRepository;
	@Autowired protected LabRepository labRepository;
	
	***/

	private static final String viewPrefix = "applications/";
	
	/*
	 * @Override protected String getHeaderTitle() { return
	 * "Manage Employees Applications"; }
	 */
	
	/****
	@RequestMapping(value="/applications", method=RequestMethod.GET)
	public String listApplication(Model model){
		List<Application> apps = appRepository.findAll();
		model.addAttribute("apps", apps);
		return viewPrefix+"applications";
		
	}
	
	@RequestMapping(value="/applications/new", method=RequestMethod.GET)
	public String createNewApplication(Model model){
		
		Application app = new Application();
		model.addAttribute("app", app);
		List<User> allUsers = allUser();
		model.addAttribute("allUsers", allUsers);
		
		return viewPrefix+"create_application";
	}
	
	***/
	
	/***
	@RequestMapping(value="/applications/new", method=RequestMethod.POST)
	public String newEventEntry( @Valid @ModelAttribute("app") Application app, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes,
			 HttpServletRequest request )
	{
		String[] selUsers= request.getParameterValues("selectedOwners");
		List<User> setUsers = new ArrayList<>();
		for (String selUser : selUsers) {
			User user = userRepository.findById(Integer.parseInt(selUser)).get();
			setUsers.add(user);
		}
		app.setOwners(setUsers);
		
		logger.debug("Application Select2 selections are :{}", selUsers);
		appValidator.validate(app, result);
		if(result.hasErrors()){
			logger.debug("Application validate function has the error ---- REHAN");
			return viewPrefix+"create_application";
		}
		
		appRepository.save(app);
		redirectAttributes.addFlashAttribute("info", "Application created successfully");
		return "redirect:/applications";
	}
	
	private List<User> allUser() {
		List<User> allUsers = null;
		User user = userRepository.findById(getCurrentUser().getUser().getId()).get();
		List<Role> usersRoles = user.getRoles();
		logger.debug("Rehan is outside IF :{}", user.getName());
		boolean admin = false;
		for (Role role : usersRoles) {
			if(role.getId().equals(1)||role.getId().equals(2)){
				logger.debug("Siddiqui is inside IF list users :{}", role.getId());
				admin = true;
			}
		}
		if(admin == true){
			allUsers = this.securityService.getAllUsers();	
			logger.debug("ADD all users");
		}else{				
			allUsers = new ArrayList<>();
			allUsers.add(getCurrentUser().getUser());
			logger.debug("ADD current users :{}",getCurrentUser().getUser().getName());
		}
		return allUsers;
	}
	
	@RequestMapping(value="/applications/{id}", method=RequestMethod.GET)
	public String editApplicationEntryGet(@PathVariable Integer id, Model model, HttpServletRequest request){
		Application app = appRepository.getOne(id);
		model.addAttribute("app", app);
		List<User> allUsers = allUser();
		model.addAttribute("allUsers", allUsers);
		
		List<User> allOwners = app.getOwners();
		request.setAttribute("allOwners", allOwners);;

		List<Lab> allLabs = labRepository.findAll(); 
		model.addAttribute("allLabs", allLabs);
		
		return viewPrefix+"edit_application";
	}
	
	@RequestMapping(value="/applications/{id}", method=RequestMethod.POST)
	public String editApplicationEntryPost(Model model, @ModelAttribute("app") Application app, RedirectAttributes redirectAttributes){
		
		appRepository.save(app);
		redirectAttributes.addFlashAttribute("info", "Application modified successfully");
		return "redirect:/applications";
	}
	
	@RequestMapping(value="/applications/del/{id}", method=RequestMethod.GET)
	public String deleteEventEntry(@Valid @ModelAttribute("app") Application app, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id){
		app = appRepository.getOne(id);
//		statusRepository.delete(empStatus);
//		statusValidator.validate(employeeStatus, result);
//		if(employeeScheduleRepository.findbyStatus(employeeStatus.getId()) != null){
		try {
			//			return "redirect:/events";
			//		}
			appRepository.delete(app);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Application " + app.getName()+ " cannot be Deleted. It belongs to the Calender!!");
		}
				return "redirect:/applications";
	}
	****/
}



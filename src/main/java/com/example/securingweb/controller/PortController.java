package com.example.securingweb.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.securingweb.entities.Port;
import com.example.securingweb.entities.Region;
import com.example.securingweb.repository.PortRepository;
import com.example.securingweb.repository.RegionRepository;
import com.example.securingweb.validators.PortValidator;

import jakarta.validation.Valid;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class PortController extends BaseController{
	
	@Autowired private PortRepository portRepository;
	@Autowired private RegionRepository regionRepository;
	@Autowired private PortValidator portValidator;
//	@Autowired private EmployeeScheduleRepository employeeScheduleRepository;

	private static final String viewPrefix = "ports/";
	@Override
	protected String getHeaderTitle() {
		return "Manage Ports";
	}
	
	@RequestMapping(value="/ports", method=RequestMethod.GET)
	public String listStatus(Model model){
		List<Port> ports = portRepository.findAll();
		model.addAttribute("ports", ports);
		return viewPrefix+"ports";
		
	}
	
	@RequestMapping(value="/ports/new", method=RequestMethod.GET)
	public String createNewPort(Model model){
		
		Port port = new Port();
		model.addAttribute("port", port);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions",regions);
		
		return viewPrefix+"create_port";
	}
	
	@RequestMapping(value="/ports/new", method=RequestMethod.POST)
	public String newPortEntry( @Valid @ModelAttribute("port") Port port, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes){
		
		portValidator.validate(port, result);
		if(result.hasErrors()){
			logger.debug("validate function has the error ---- REHAN");
			return viewPrefix+"create_port";
		}
		
		portRepository.save(port);
		redirectAttributes.addFlashAttribute("info", "Port created successfully");
		return "redirect:/ports";
	}
	
	@RequestMapping(value="/ports/{id}", method=RequestMethod.GET)
	public String editPortEntryGet(@PathVariable Integer id, Model model){
		Port port = portRepository.getOne(id);
		model.addAttribute("port", port);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions",regions);
		
		return viewPrefix+"edit_port";
	}
	
	@RequestMapping(value="/ports/{id}", method=RequestMethod.POST)
	public String editPortEntryPost(Model model, @ModelAttribute("port") Port port, RedirectAttributes redirectAttributes){
		
		portRepository.save(port);
		redirectAttributes.addFlashAttribute("info", "Port modified successfully");
		return "redirect:/ports";
	}
	
	@RequestMapping(value="/ports/del/{id}", method=RequestMethod.GET)
	public String deletePortEntry(@Valid @ModelAttribute("port") Port port, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id){
		port = portRepository.getOne(id);
//		portRepository.delete(port);
//		portValidator.validate(port, result);
//		if(employeeScheduleRepository.findbyStatus(port.getId()) != null){
try {
	//			return "redirect:/ports";
	//		}
	portRepository.delete(port);
} catch (Exception e) {
	redirectAttributes.addFlashAttribute("error", "Port " + port.getCode()+ " cannot be Deleted. It belongs to the Calender!!");
}
redirectAttributes.addFlashAttribute("info", "Port " + port.getCode()+ " deleted successfully");

		return "redirect:/ports";
	}
}



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

import com.example.securingweb.entities.ShipClass;
import com.example.securingweb.repository.ShipClassRepository;
import com.example.securingweb.validators.ShipClassValidator;

import jakarta.validation.Valid;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class ShipClassController extends BaseController{
	
	@Autowired private ShipClassRepository shipClassRepository;
	@Autowired private ShipClassValidator shipClassValidator;
//	@Autowired private EmployeeScheduleRepository employeeScheduleRepository;

	private static final String viewPrefix = "shipClass/";
	@Override
	protected String getHeaderTitle() {
		return "Manage ShipClass";
	}
	
	@RequestMapping(value="/shipClass", method=RequestMethod.GET)
	public String listStatus(Model model){
		List<ShipClass> shipClasses = shipClassRepository.findAll();
		model.addAttribute("shipClasses", shipClasses);
		return "shipClass/shipClass"; // /shipClasss/brnds.html
		
	}
	
	@RequestMapping(value="/shipClass/new", method=RequestMethod.GET)
	public String createNewShipClass(Model model){
		
		ShipClass shipClass = new ShipClass();
		model.addAttribute("shipClass", shipClass);
		
		return viewPrefix+"create_shipClass";
	}
	
	@RequestMapping(value="/shipClass/new", method=RequestMethod.POST)
	public String newShipClassEntry( @Valid @ModelAttribute("shipClass") ShipClass shipClass, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes){
		
		shipClassValidator.validate(shipClass, result);
		if(result.hasErrors()){
			logger.debug("validate function has the error ---- REHAN");
			return viewPrefix+"create_shipClass";
		}
		
		shipClassRepository.save(shipClass);
		redirectAttributes.addFlashAttribute("info", "Ship Class created successfully");
		return "redirect:/shipClass";
	}
	
	@RequestMapping(value="/shipClass/{id}", method=RequestMethod.GET)
	public String editShipClassEntryGet(@PathVariable Integer id, Model model){
		ShipClass shipClass = shipClassRepository.getOne(id);
		model.addAttribute("shipClass", shipClass);
		
		return viewPrefix+"edit_shipClass";
	}
	
	@RequestMapping(value="/shipClass/{id}", method=RequestMethod.POST)
	public String editShipClassEntryPost(Model model, @ModelAttribute("shipClass") ShipClass shipClass, RedirectAttributes redirectAttributes){
		
		shipClassRepository.save(shipClass);
		redirectAttributes.addFlashAttribute("info", "Ship Class modified successfully");
		return "redirect:/shipClass";
	}
	
	@RequestMapping(value="/shipClass/del/{id}", method=RequestMethod.GET)
	public String deleteShipClassEntry(@Valid @ModelAttribute("shipClass") ShipClass shipClass, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id){
		shipClass = shipClassRepository.getOne(id);
//		shipClassRepository.delete(shipClass);
//		shipClassValidator.validate(shipClass, result);
//		if(employeeScheduleRepository.findbyStatus(shipClass.getId()) != null){
try {
	//			return "redirect:/shipClasss";
	//		}
	shipClassRepository.delete(shipClass);
} catch (Exception e) {
	redirectAttributes.addFlashAttribute("error", "ShipClass " + shipClass.getCode()+ " cannot be Deleted. It belongs to the Ship!!");
	return "redirect:/shipClass";
}
redirectAttributes.addFlashAttribute("info", "Ship Class " + shipClass.getCode()+ " deleted successfully");

		return "redirect:/shipClass";
	}
}



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

import com.example.securingweb.entities.Brand;
import com.example.securingweb.entities.Schedule;
import com.example.securingweb.entities.TransmissionType;
import com.example.securingweb.repository.BrandRepository;
import com.example.securingweb.repository.ScheduleRepository;
import com.example.securingweb.repository.TransmissionTypeRepository;
import com.example.securingweb.validators.TransmissionTypeValidator;

import jakarta.validation.Valid;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class TransmissionTypeController extends BaseController{
	
	@Autowired private TransmissionTypeRepository transmissionTypeRepository;
	@Autowired private TransmissionTypeValidator transmissionTypeValidator;
	@Autowired private BrandRepository brandRepository;
//	@Autowired private EmployeeScheduleRepository employeeScheduleRepository;
	@Autowired private ScheduleRepository scheduleRepository;

	private static final String viewPrefix = "transmissionTypes/";
	@Override
	protected String getHeaderTitle() {
		return "Manage TransmissionTypes";
	}
	
	@RequestMapping(value="/transmissionTypes", method=RequestMethod.GET)
	public String listStatus(Model model){
		List<TransmissionType> transmissionTypes = transmissionTypeRepository.findAll();
		model.addAttribute("transmissionTypes", transmissionTypes);
		return "transmissionTypes/transmissionTypes"; // /TransmissionTypes/brnds.html
		
	}
	
	@RequestMapping(value="/transmissionTypes/new", method=RequestMethod.GET)
	public String createNewTransmissionType(Model model){
		
		TransmissionType transmissionType = new TransmissionType();
		model.addAttribute("transmissionType", transmissionType);
		
		List<Brand> brands = brandRepository.findAll();
		
		model.addAttribute("brands", brands);
		
		return viewPrefix+"create_transmissionType";
	}
	
	@RequestMapping(value="/transmissionTypes/new", method=RequestMethod.POST)
	public String newTransmissionTypeEntry( @Valid @ModelAttribute("transmissionType") TransmissionType transmissionType, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes){
		
		transmissionTypeValidator.validate(transmissionType, result);
		if(result.hasErrors()){
			logger.debug("validate function has the error ---- REHAN");
			return viewPrefix+"create_transmissionType";
		}
		
		transmissionTypeRepository.save(transmissionType);
		scheduleRepository.updateScheduleData(transmissionType.getId(),"transmissionType");
		scheduleRepository.insertScheduleData(transmissionType.getId(),"transmissionType");
		redirectAttributes.addFlashAttribute("info", "TransmissionType created successfully");
		return "redirect:/transmissionTypes";
	}
	
	@RequestMapping(value="/transmissionTypes/{id}", method=RequestMethod.GET)
	public String editTransmissionTypeEntryGet(@PathVariable Integer id, Model model){
		TransmissionType transmissionType = transmissionTypeRepository.getOne(id);
		model.addAttribute("transmissionType", transmissionType);
		
		List<Brand> brands = brandRepository.findAll();
		model.addAttribute("brands", brands);
		
		return viewPrefix+"edit_transmissionType";
	}
	
	@RequestMapping(value="/transmissionTypes/{id}", method=RequestMethod.POST)
	public String editTransmissionTypeEntryPost(Model model, @ModelAttribute("transmissionType") TransmissionType transmissionType, RedirectAttributes redirectAttributes){
		
		transmissionTypeRepository.save(transmissionType);
		
		/* Code to delete the existing schedule data and inserting new*/
		System.out.println("transmissionType.getId() - "+transmissionType.getId());
		List<Schedule> schd= scheduleRepository.findEventsByTransId(String.valueOf(transmissionType.getId()));
		System.out.println("schd.size() - "+ schd.size());
//		scheduleRepository.deleteInBatch(schd);
//		System.out.println("Schedule data deleted");
		scheduleRepository.updateScheduleData(transmissionType.getId(),"transmissionType");
		scheduleRepository.insertScheduleData(transmissionType.getId(),"transmissionType");
		System.out.println("Procedure executed");
		/* schedule data insert end*/
		
		redirectAttributes.addFlashAttribute("info", "TransmissionType modified successfully");
		return "redirect:/transmissionTypes";
	}
	
	@RequestMapping(value="/transmissionTypes/del/{id}", method=RequestMethod.GET)
	public String deleteTransmissionTypeEntry(@Valid @ModelAttribute("transmissionType") TransmissionType transmissionType, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id){
		transmissionType = transmissionTypeRepository.getOne(id);
//		transmissionTypeRepository.delete(transmissionType);
//		transmissionTypeValidator.validate(transmissionType, result);
//		if(employeeScheduleRepository.findbyStatus(transmissionType.getId()) != null){
		try {
			//			return "redirect:/TransmissionTypes";
			//		}
			transmissionTypeRepository.delete(transmissionType);
			scheduleRepository.updateScheduleData(transmissionType.getId(),"transmissionType");
			scheduleRepository.insertScheduleData(transmissionType.getId(),"transmissionType");
		} catch (Exception e) {
			List<Brand> brands = transmissionType.getBrand();
		//	for (Brand brand : brands) {		
		//		redirectAttributes.addFlashAttribute("error", "TransmissionType " + transmissionType.getCode()+ " cannot be Deleted. It belongs to the " + brand.getCode() + "  !!");
				redirectAttributes.addFlashAttribute("error", "TransmissionType " + transmissionType.getCode()+ " cannot be Deleted. It belongs to the Brand  !!");
		//	}
			return "redirect:/transmissionTypes";
		}
		
		redirectAttributes.addFlashAttribute("info", "TransmissionType " + transmissionType.getCode()+ " deleted successfully");
		return "redirect:/transmissionTypes";
	}
}



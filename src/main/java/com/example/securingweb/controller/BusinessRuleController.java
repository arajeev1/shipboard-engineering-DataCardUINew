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

import com.example.securingweb.entities.BusinessRule;
import com.example.securingweb.entities.PMS;
import com.example.securingweb.entities.Region;
import com.example.securingweb.entities.TransmissionType;
import com.example.securingweb.repository.BusinessRuleRepository;
import com.example.securingweb.repository.PMSRepository;
import com.example.securingweb.repository.RegionRepository;
import com.example.securingweb.repository.ScheduleRepository;
import com.example.securingweb.repository.StatusRepository;
import com.example.securingweb.repository.TransmissionTypeRepository;
import com.example.securingweb.validators.BusinessRuleValidator;

import jakarta.validation.Valid;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class BusinessRuleController extends BaseController{
	
	@Autowired private StatusRepository statusRepository;
	@Autowired private TransmissionTypeRepository transmissionTypeRepository;
	@Autowired private PMSRepository pmsRepository;
	@Autowired private RegionRepository regionRepository;
	@Autowired private BusinessRuleRepository businessRuleRepository;
	@Autowired private ScheduleRepository scheduleRepository;

	
	@Autowired private BusinessRuleValidator statusValidator;
//	@Autowired private EmployeeScheduleRepository employeeScheduleRepository;

	private static final String viewPrefix = "business_rules/";
	@Override
	protected String getHeaderTitle() {
		return "Manage Business Rule";
	}
	
	
	@GetMapping(path = "/business_rules") 
	public String listStatus(Model model){
		List<BusinessRule> business_rules = statusRepository.findAll();
		model.addAttribute("business_rules", business_rules);
		
		List<TransmissionType> transmissionTypesBR = transmissionTypeRepository.findAll();
		model.addAttribute("transmissionTypesBR", transmissionTypesBR);
		
		List<PMS> pmss = pmsRepository.findAll();
		model.addAttribute("pmss", pmss);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
	
		return viewPrefix+"business_rules";
		
	}
	
	@GetMapping(path = "/business_rules/new") 
	public String createNewEvent(Model model){
		
		BusinessRule empStatus = new BusinessRule();
		model.addAttribute("empStatus", empStatus);
		
		List<TransmissionType> transmissionTypesBR = transmissionTypeRepository.findAll();
		model.addAttribute("transmissionTypesBR", transmissionTypesBR);
		
		List<PMS> pmss = pmsRepository.findAll();
		model.addAttribute("pmss", pmss);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
				
		return viewPrefix+"create_business_rule";
	}
	
	

	@PostMapping(value="/business_rules/new")
	public String newEventEntry( @Valid @ModelAttribute("empStatus") BusinessRule businessRule, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes){

		List<TransmissionType> transmissionTypesBR = transmissionTypeRepository.findAll();
		model.addAttribute("transmissionTypesBR", transmissionTypesBR);
		
		List<PMS> pmss = pmsRepository.findAll();
		model.addAttribute("pmss", pmss);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
			
		statusValidator.validate(businessRule, result);
		if(result.hasErrors()){
			logger.debug("validate function has the error ---- REHAN");
			

			return viewPrefix+"create_business_rule";
		}
		PMS pmsBR = businessRule.getPmsBR();
		Region regionBR = businessRule.getRegionBR();
		TransmissionType transmissionTypeBR = businessRule.getTransmissionTypeBR();
		
		String pmsBROffSet = pmsBR.getOffSetPMS();
		Integer regionBROffSet = regionBR.getOffSetRegion();
		Integer transmissionTypeBROffSet = transmissionTypeBR.getOffSetTransmission();
		
		Integer compoundOffSetHours = Integer.valueOf(pmsBROffSet) + Integer.valueOf(regionBROffSet);
//		Integer compoundOffSetDays = Integer.valueOf(transmissionTypeBROffSet);
		String compoundValue = transmissionTypeBROffSet + " Day(s) and " + Integer.toString(compoundOffSetHours) + " Hour(s)";
		businessRule.setCompoundOffSet(compoundValue);
		try {
			businessRule = statusRepository.save(businessRule);
			statusRepository.flush();
			businessRuleRepository.insertAS400business_rule_map();
			scheduleRepository.updateScheduleData(businessRule.getId(),"businessRule");
			scheduleRepository.insertScheduleData(businessRule.getId(),"businessRule");
			redirectAttributes.addFlashAttribute("info", "Business Rule created successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			model.addAttribute("error", "Business Rule " + businessRule.getCode()+ " has duplicate PMS, Region and Tranmission Type combination!!");
			return viewPrefix+"create_business_rule";
		}
		return "redirect:/business_rules";
	}
	
	
	@GetMapping(path = "/business_rules/{id}") 
	public String editEventEntryGet(@PathVariable Integer id, Model model){
		BusinessRule empStatus = statusRepository.getOne(id);
		model.addAttribute("empStatus", empStatus);

		List<TransmissionType> transmissionTypesBR = transmissionTypeRepository.findAll();
		model.addAttribute("transmissionTypesBR", transmissionTypesBR);
		
		List<PMS> pmss = pmsRepository.findAll();
		model.addAttribute("pmss", pmss);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
		
		return viewPrefix+"edit_business_rule";
	}
	
	

	@PostMapping(value="/regions/new")
	@RequestMapping(value="/business_rules/{id}", method=RequestMethod.POST)
	public String editEventEntryPost(Model model, @ModelAttribute("empStatus") BusinessRule businessRule, RedirectAttributes redirectAttributes){
		
		PMS pmsBR = businessRule.getPmsBR();
		Region regionBR = businessRule.getRegionBR();
		TransmissionType transmissionTypeBR = businessRule.getTransmissionTypeBR();
		
		String pmsBROffSet = pmsBR.getOffSetPMS();
		Integer regionBROffSet = regionBR.getOffSetRegion();
		Integer transmissionTypeBROffSet = transmissionTypeBR.getOffSetTransmission();
		
		Integer compoundOffSetHours = Integer.valueOf(pmsBROffSet) + Integer.valueOf(regionBROffSet);
//		Integer compoundOffSetDays = Integer.valueOf(transmissionTypeBROffSet);
		String compoundValue = transmissionTypeBROffSet + " Day(s) and " + Integer.toString(compoundOffSetHours) + " Hour(s)";
		businessRule.setCompoundOffSet(compoundValue);
		businessRule = statusRepository.save(businessRule);
		statusRepository.flush();
		scheduleRepository.updateScheduleData(businessRule.getId(),"businessRule");
		scheduleRepository.insertScheduleData(businessRule.getId(),"businessRule");
		redirectAttributes.addFlashAttribute("info", "Business Rule modified successfully");
		return "redirect:/business_rules";
	}
	
	
	@GetMapping(path = "/regions/new") 
	@RequestMapping(value="/business_rules/del/{id}", method=RequestMethod.GET)
	public String deleteEventEntry(@Valid @ModelAttribute("empStatus") BusinessRule businessRule, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id){
		businessRule = statusRepository.getOne(id);
//		statusRepository.delete(empStatus);
//		statusValidator.validate(employeeStatus, result);
//		if(employeeScheduleRepository.findbyStatus(employeeStatus.getId()) != null){
try {
	//			return "redirect:/business_rules";
	//		}
	statusRepository.delete(businessRule);
	scheduleRepository.updateScheduleData(businessRule.getId(),"businessRule");
	scheduleRepository.insertScheduleData(businessRule.getId(),"businessRule");
} catch (Exception e) {
	redirectAttributes.addFlashAttribute("error", "Business Rule " + businessRule.getCode()+ " cannot be Deleted. It belongs to the Calender!!");
	return "redirect:/business_rules";
}
redirectAttributes.addFlashAttribute("info", "Business Rule " + businessRule.getCode()+ " deleted successfully");

		return "redirect:/business_rules";
	}
}



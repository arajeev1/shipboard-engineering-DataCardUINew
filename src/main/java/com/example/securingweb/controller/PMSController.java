package com.example.securingweb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.securingweb.entities.PMS;
import com.example.securingweb.repository.PMSRepository;
import com.example.securingweb.validators.PMSValidator;

import jakarta.validation.Valid;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class PMSController extends BaseController{
	
	@Autowired private PMSRepository PMSRepository;
	@Autowired private PMSValidator PMSValidator;

	
	//	@Autowired private EmployeeScheduleRepository employeeScheduleRepository;
	
	//	@Autowired private ScheduleRepository scheduleRepository;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String viewPrefix = "pmss/";
	
	 @Override 
	 protected String getHeaderTitle() 
	 { 
		 return "Manage PMSs"; 
	 }
	 	
	@GetMapping(path = "/pmss") 
	//@RequestMapping(value="/pmss", method=RequestMethod.GET)
	public String listPMS(Model model){
		List<PMS> pmss = PMSRepository.findAll();
		model.addAttribute("pmss", pmss);
		return viewPrefix+"pmss";
		
	}
	
	@GetMapping(path = "/pmss/new") 
	//@RequestMapping(value="/pmss/new", method=RequestMethod.GET)
	public String createNewPMS(Model model){
		
		PMS pms = new PMS();
		model.addAttribute("pms", pms);
		
		return viewPrefix+"create_pms";
	}
	
	
	@PostMapping(value="/pmss/new")
	//@RequestMapping(value="/pmss/new", method=RequestMethod.POST)
	public String newPMSEntry( @Valid @ModelAttribute("PMS") PMS PMS, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes){
		
		PMSValidator.validate(PMS, result);
		if(result.hasErrors()){
			logger.debug("validate function has the error ---- REHAN");
			return viewPrefix+"create_pms";
		}
		
		PMSRepository.save(PMS);
		redirectAttributes.addFlashAttribute("info", "PMS created successfully");
		return "redirect:/pmss";
	}
	
	
	@GetMapping(path = "/pmss/{id}") 
	//@RequestMapping(value="/pmss/{id}", method=RequestMethod.GET)
	public String editPMSEntryGet(@PathVariable Integer id, Model model){
		PMS pms = PMSRepository.getOne(id);
		model.addAttribute("pms", pms);
		
		return viewPrefix+"edit_pms";
	}

	/**
	
	@RequestMapping(value="/pmss/{id}", method=RequestMethod.POST)
	public String editPMSEntryPost(Model model, @ModelAttribute("pms") PMS pms, RedirectAttributes redirectAttributes){
		
		PMSRepository.save(pms);
		
		System.out.println("pms.getId() - "+pms.getId());
		List<Schedule> schd= scheduleRepository.findEventsByPmsId(String.valueOf(pms.getId()));
		System.out.println("schd.size() - "+ schd.size());
		scheduleRepository.updateScheduleData(pms.getId(),"pms");
		System.out.println("Procedure executed");
		
		redirectAttributes.addFlashAttribute("info", "PMS modified successfully");
		return "redirect:/pmss";
	}
	**/

	@GetMapping(path = "/pmss/del/{id}") 
	//@RequestMapping(value="/pmss/del/{id}", method=RequestMethod.GET)
	public String deletePMSEntry(@Valid @ModelAttribute("pms") PMS pms, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id)
	{
		pms = PMSRepository.getOne(id);
//		PMSRepository.delete(PMS);
//		PMSValidator.validate(PMS, result);L
		try {
			//			return "redirect:/pmss";
			//		}
			PMSRepository.delete(pms);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "PMS " + pms.getCode()+ " cannot be Deleted. It belongs to the Ship !!");
			return "redirect:/pmss";
		}
		redirectAttributes.addFlashAttribute("info", "PMS " + pms.getCode()+ " deleted successfully");
		return "redirect:/pmss";
	}
}



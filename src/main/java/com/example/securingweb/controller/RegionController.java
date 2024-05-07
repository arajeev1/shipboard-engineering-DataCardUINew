package com.example.securingweb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.securingweb.AppProperties;
import com.example.securingweb.entities.Region;
import com.example.securingweb.entities.Schedule;
//import com.datacard.repositories.EmployeeScheduleRepository;
import com.example.securingweb.repository.RegionRepository;
import com.example.securingweb.repository.ScheduleRepository;
import com.example.securingweb.validators.RegionValidator;

import jakarta.validation.Valid;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class RegionController extends BaseController
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private RegionRepository regionRepository;
	@Autowired private RegionValidator regionValidator;
//	@Autowired private EmployeeScheduleRepository employeeScheduleRepository;
	@Autowired private ScheduleRepository scheduleRepository;

	private AppProperties app;
    
    @Autowired
    public void setApp(AppProperties app) {
        this.app = app;
    }
    
	private static final String viewPrefix = "regions/";
	@Override
	protected String getHeaderTitle() {
		return "Manage Regions";
	}
	
	@GetMapping(path = "/regions") 
	//@RequestMapping(value="/regions", method=RequestMethod.GET)
	public String listStatus(Model model){
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
		model.addAttribute("app", app);
		
		return viewPrefix+"regions";
		
	}
	
	@GetMapping(path = "/regions/new") 
	//@RequestMapping(value="/regions/new", method=RequestMethod.GET)
	public String createNewRegion(Model model){
		
		Region region = new Region();
		model.addAttribute("region", region);
		model.addAttribute("app", app);
		return viewPrefix+"create_region";
	}
	
	@PostMapping(value="/regions/new")
	//@RequestMapping(value="/regions/new", method=RequestMethod.POST)
	public String newRegionEntry( @Valid @ModelAttribute("region") Region region, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes){
		
		//regionValidator.validate(region, result);
		if(result.hasErrors()){
			logger.debug("validate function has the error ---- REHAN");
			return viewPrefix+"create_region";
		}
		
		regionRepository.save(region);
		redirectAttributes.addFlashAttribute("info", "Region created successfully");
		return "redirect:/regions";
	}
	
	//@RequestMapping(value="/regions/{id}", method=RequestMethod.GET)
	@GetMapping(path = "/regions/{id}") 
	public String editRegionEntryGet(@PathVariable Integer id, Model model){
		Region region = regionRepository.getOne(id);
		model.addAttribute("region", region);
		
		return viewPrefix+"edit_region";
	}
	
	@PostMapping(path = "/regions/{id}") 
	//@RequestMapping(value="/regions/{id}", method=RequestMethod.POST)
	public String editRegionEntryPost(Model model, @ModelAttribute("region") Region region, RedirectAttributes redirectAttributes){
		
		regionRepository.save(region);
		
		/* Code to delete the existing schedule data and inserting new*/
		System.out.println("region.getId() - "+region.getId());
		List<Schedule> schd= scheduleRepository.findEventsByRegionId(String.valueOf(region.getId()));
		System.out.println("schd.size() - "+ schd.size());
//		scheduleRepository.deleteInBatch(schd);
//		System.out.println("Schedule data deleted");
//		scheduleRepository.insertScheduleData(region.getId(),"region");
		scheduleRepository.updateScheduleData(region.getId(),"region");
		System.out.println("Procedure executed");
		/* schedule data insert end*/
		
		redirectAttributes.addFlashAttribute("info", "Region modified successfully");
		return "redirect:/regions";
	}
	
	@GetMapping(path = "/regions/del/{id}") 
	//@RequestMapping(value="/regions/del/{id}", method=RequestMethod.GET)
	public String deleteRegionEntry(@Valid @ModelAttribute("region") Region region, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id){
		region = regionRepository.getOne(id);
//		regionRepository.delete(region);
//		regionValidator.validate(region, result);
//		if(employeeScheduleRepository.findbyStatus(region.getId()) != null){
try {
	//			return "redirect:/regions";
	//		}
	regionRepository.delete(region);
} catch (Exception e) {
	redirectAttributes.addFlashAttribute("error", "Region " + region.getCode()+ " cannot be Deleted. It belongs to the Port !!");
	return "redirect:/regions";
}
redirectAttributes.addFlashAttribute("info", "Region " + region.getCode()+ " deleted successfully");

		return "redirect:/regions";
	}
}



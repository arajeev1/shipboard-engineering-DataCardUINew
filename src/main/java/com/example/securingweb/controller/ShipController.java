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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.securingweb.entities.Brand;
import com.example.securingweb.entities.PMS;
import com.example.securingweb.entities.Schedule;
import com.example.securingweb.entities.Ship;
import com.example.securingweb.entities.ShipClass;
import com.example.securingweb.repository.BrandRepository;
import com.example.securingweb.repository.PMSRepository;
import com.example.securingweb.repository.ScheduleRepository;
import com.example.securingweb.repository.ShipClassRepository;
import com.example.securingweb.repository.ShipRepository;
import com.example.securingweb.validators.ShipValidator;

import jakarta.validation.Valid;
@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class ShipController extends BaseController{
	
	@Autowired private ShipRepository shipRepository;
	@Autowired private ShipClassRepository shipClassRepository;
	@Autowired private BrandRepository brandRepository;
	@Autowired private PMSRepository pmsRepository;
	@Autowired private ShipValidator shipValidator;
//	@Autowired private EmployeeScheduleRepository employeeScheduleRepository;
	
	@Autowired private ScheduleRepository scheduleRepository;


	private static final String viewPrefix = "ships/";
	@Override
	protected String getHeaderTitle() {
		return "Manage Ships";
	}
	
	@GetMapping(path = "/ships") 
	//@RequestMapping(value="/ships", method=RequestMethod.GET)
	public String listStatus(Model model){
		List<Ship> ships = shipRepository.findAll();
		model.addAttribute("ships", ships);
		return viewPrefix+"ships";
		
	}
	
	
	@GetMapping(path = "/ships/new") 
	//@RequestMapping(value="/ships/new", method=RequestMethod.GET)
	public String createNewShip(Model model){
		
		Ship ship = new Ship();
		model.addAttribute("ship", ship);
		List<Brand> brands = brandRepository.findAll();
		model.addAttribute("brands",brands);
		List<PMS> pmss = pmsRepository.findAll();
		model.addAttribute("pmss",pmss);
		List<ShipClass> classes = shipClassRepository.findAll();
		model.addAttribute("classes",classes);

		
		return viewPrefix+"create_ship";
	}
	
	

	@PostMapping(value="/ships/new")
	//@RequestMapping(value="/ships/new", method=RequestMethod.POST)
	public String newShipEntry(@Valid @ModelAttribute("ship") Ship ship, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes)
	{
		
		shipValidator.validate(ship, result);
		if(result.hasErrors()){
			logger.debug("validate function has the error ---- REHAN");
			return viewPrefix+"create_ship";
		}
		
		shipRepository.save(ship);
		redirectAttributes.addFlashAttribute("info", "Ship created successfully");
		return "redirect:/ships";
	}
	
	
	@GetMapping(path = "/ships/{id}") 
	//@RequestMapping(value="/ships/{id}", method=RequestMethod.GET)
	public String editShipEntryGet(@PathVariable Integer id, Model model){
		Ship ship = shipRepository.getOne(id);
		model.addAttribute("ship", ship);
		List<Brand> brands = brandRepository.findAll();
		model.addAttribute("brands",brands);
		List<PMS> pmss = pmsRepository.findAll();
		model.addAttribute("pmss",pmss);
		List<ShipClass> classes = shipClassRepository.findAll();
		model.addAttribute("classes",classes);

		return viewPrefix+"edit_ship";
	}
	

	@PostMapping(value="/ships/{id}")
	//@RequestMapping(value="/ships/{id}", method=RequestMethod.POST)
	public String editShipEntryPost(Model model, @ModelAttribute("ship") Ship ship, RedirectAttributes redirectAttributes){
		
		shipRepository.save(ship);
		
		/* Code to delete the existing schedule data and inserting new*/
		System.out.println("region.getId() - "+ship.getId());
		List<Schedule> schd= scheduleRepository.findEventsByShipId(String.valueOf(ship.getId()));
		System.out.println("schd.size() - "+ schd.size());
//		scheduleRepository.deleteInBatch(schd);
//		System.out.println("Schedule data deleted");
//		scheduleRepository.insertScheduleData(ship.getId(),"ship");
		scheduleRepository.updateScheduleData(ship.getId(),"ship");
		System.out.println("Procedure executed");
		/* schedule data insert end*/
		
		redirectAttributes.addFlashAttribute("info", "Ship modified successfully");
		return "redirect:/ships";
	}
	
	
	@GetMapping(path = "/ships/del/{id}") 
	//@RequestMapping(value="/ships/del/{id}", method=RequestMethod.GET)
	public String deleteShipEntry(@Valid @ModelAttribute("ship") Ship ship, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id)
	{
		ship = shipRepository.getOne(id);
//		shipRepository.delete(ship);
//		shipValidator.validate(ship, result);
//		if(employeeScheduleRepository.findbyStatus(ship.getId()) != null){
		try {
			//			return "redirect:/ships";
			//		}
			shipRepository.delete(ship);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Ship " + ship.getCode()+ " cannot be Deleted. It belongs to the Calender!!");
		}
		redirectAttributes.addFlashAttribute("info", "Ship " + ship.getCode()+ " deleted successfully");

		return "redirect:/ships";
	}
}



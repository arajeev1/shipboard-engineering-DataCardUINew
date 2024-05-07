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
import com.example.securingweb.entities.TransmissionType;
import com.example.securingweb.repository.BrandRepository;
import com.example.securingweb.repository.TransmissionTypeRepository;
import com.example.securingweb.validators.BrandValidator;

import jakarta.validation.Valid;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class BrandController extends BaseController{
	
	@Autowired private BrandRepository brandRepository;
	@Autowired private TransmissionTypeRepository transmissionTypeRepository;
	@Autowired private BrandValidator brandValidator;
//	@Autowired private EmployeeScheduleRepository employeeScheduleRepository;

	private static final String viewPrefix = "brands/";
	@Override
	protected String getHeaderTitle() {
		return "Manage Brands";
	}
	
	@RequestMapping(value="/brands", method=RequestMethod.GET)
	public String listStatus(Model model){
		List<Brand> brands = brandRepository.findAll();
		model.addAttribute("brands", brands);
		return "brands/brands"; // /brands/brnds.html
		
	}
	
	@RequestMapping(value="/brands/new", method=RequestMethod.GET)
	public String createNewBrand(Model model){
		
		Brand brand = new Brand();
		model.addAttribute("brand", brand);
		List<TransmissionType> transmissionTypes = transmissionTypeRepository.findAll();
		model.addAttribute("transmissionTypes", transmissionTypes);

		return viewPrefix+"create_brand";
	}
	
	@RequestMapping(value="/brands/new", method=RequestMethod.POST)
	public String newBrandEntry( @Valid @ModelAttribute("brand") Brand brand, 
			 BindingResult result, Model model, RedirectAttributes redirectAttributes){
		
		brandValidator.validate(brand, result);
		if(result.hasErrors()){
			logger.debug("validate function has the error ---- REHAN");
			return viewPrefix+"create_brand";
		}
		
		brandRepository.save(brand);
		redirectAttributes.addFlashAttribute("info", "Brand created successfully");
		return "redirect:/brands";
	}
	
	@RequestMapping(value="/brands/{id}", method=RequestMethod.GET)
	public String editBrandEntryGet(@PathVariable Integer id, Model model){
		Brand brand = brandRepository.getOne(id);
		model.addAttribute("brand", brand);
		List<TransmissionType> transmissionTypes = transmissionTypeRepository.findAll();
		model.addAttribute("transmissionTypes", transmissionTypes);
		return viewPrefix+"edit_brand";
	}
	
	@RequestMapping(value="/brands/{id}", method=RequestMethod.POST)
	public String editBrandEntryPost(Model model, @ModelAttribute("brand") Brand brand, RedirectAttributes redirectAttributes){
		
		brandRepository.save(brand);
		redirectAttributes.addFlashAttribute("info", "Brand modified successfully");
		return "redirect:/brands";
	}
	
	@RequestMapping(value="/brands/del/{id}", method=RequestMethod.GET)
	public String deleteBrandEntry(@Valid @ModelAttribute("brand") Brand brand, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id){
		brand = brandRepository.getOne(id);
//		brandRepository.delete(brand);
//		brandValidator.validate(brand, result);
//		if(employeeScheduleRepository.findbyStatus(brand.getId()) != null){
try {
	//			return "redirect:/brands";
	//		}
	brandRepository.delete(brand);
} catch (Exception e) {
	redirectAttributes.addFlashAttribute("error", "Brand " + brand.getCode()+ " cannot be Deleted. It belongs to a Ship !!");
	return "redirect:/brands";
}
redirectAttributes.addFlashAttribute("info", "Brand " + brand.getCode()+ " deleted successfully");

		return "redirect:/brands";
	}
}



package com.example.securingweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.securingweb.entities.AS400BusinessRuleMap;
import com.example.securingweb.repository.AS400BusinessRuleMapRepository;
import com.example.securingweb.repository.RegionRepository;
import com.example.securingweb.validators.PortValidator;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class as400BusinessRuleMapController extends BaseController{
	
	@Autowired private AS400BusinessRuleMapRepository as400BusinessRuleMapRepository;
	@Autowired private RegionRepository regionRepository;
	@Autowired private PortValidator portValidator;
//	@Autowired private EmployeeScheduleRepository employeeScheduleRepository;

	private static final String viewPrefix = "businessRuleMap/";
	@Override
	protected String getHeaderTitle() {
		return "Manage Ports";
	}
	
	@RequestMapping(value="/businessRuleMaps", method=RequestMethod.GET)
	public String listBusinessRuleMap(Model model){
		List<AS400BusinessRuleMap> as400BusinessRuleMaps = as400BusinessRuleMapRepository.findAll();
		model.addAttribute("as400BusinessRuleMaps", as400BusinessRuleMaps);
		return viewPrefix+"businessRuleMaps";
		
	}
	
	
}



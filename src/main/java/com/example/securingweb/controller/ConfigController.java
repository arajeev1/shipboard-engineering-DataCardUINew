/**
 * 
 */
package com.example.securingweb.controller;

import java.util.Date;
import java.util.List;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.securingweb.entities.EmailConfig;
import com.example.securingweb.repository.EmailConfigRepository;

import jakarta.validation.Valid;

/**
 * @author User
 *
 */
@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class ConfigController extends BaseController {

	@Autowired EmailConfigRepository emailConfRepository;
	
	private static final String viewPrefix = "config/";
	
	@Override
	protected String getHeaderTitle() {
		return "Configurations";
	}
	
	@RequestMapping(value="/emailConfig" , method=RequestMethod.GET)
	public String emailConfig(Model model)
	{
		List<EmailConfig> emailConfigs = emailConfRepository.findAll();
		if(emailConfigs.size() > 0) {
			EmailConfig emailConfig = null;
			for (EmailConfig em: emailConfigs) {
				emailConfig = em;
			}
			model.addAttribute("emailConfig", emailConfig);
		} else {
			model.addAttribute("emailConfig", new EmailConfig());
		}
		
		return viewPrefix+"emailConfig";
	}
	
	@RequestMapping(value="/emailConfig", method=RequestMethod.POST)
	public String saveEmailConfig(@Valid @ModelAttribute("emailConfig") EmailConfig emailConfig, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()){
			model.addAttribute("error", "Error occured while saving!");
			model.addAttribute("emailConfig",emailConfig);
			return viewPrefix+"emailConfig";
		}
		
		if(null != emailConfig.getId() && emailConfig.getId() > 0 ) {
			EmailConfig updateEmailConfig = emailConfRepository.getById(emailConfig.getId());
			updateEmailConfig.setEmailHost(emailConfig.getEmailHost());
			updateEmailConfig.setEmailUsername(emailConfig.getEmailUsername());
			updateEmailConfig.setEmailPassword(emailConfig.getEmailPassword());
			updateEmailConfig.setEmailPort(emailConfig.getEmailPort());
			updateEmailConfig.setCreateDate(new Date());
			updateEmailConfig.setCreatedBy("Admin");
			emailConfRepository.save(updateEmailConfig);
		} else {
			emailConfig.setCreateDate(new Date());
			emailConfig.setCreatedBy("Admin");
			emailConfRepository.save(emailConfig);
		}
		
		model.addAttribute("info", "Email settings saved successfully.");
		model.addAttribute("emailConfig",emailConfig);
		return viewPrefix+"emailConfig";
	}
}

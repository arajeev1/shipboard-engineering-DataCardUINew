/**
 * 
 */
package com.example.securingweb.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.securingweb.entities.Permission;
import com.example.securingweb.services.SecurityService;


/**
 *
 */
@Controller
//@Secured(SecurityUtil.MANAGE_PERMISSIONS)
public class PermissionController extends BaseController
{
	private static final String viewPrefix = "permissions/";
	
	@Autowired protected SecurityService securityService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Permissions";
	}
	
	

	@GetMapping(path = "/permissions") 
	//@RequestMapping(value="/permissions", method=RequestMethod.GET)
	public String listPermissions(Model model) {
		List<Permission> list = securityService.getAllPermissions();
		model.addAttribute("permissions",list);
		return viewPrefix+"permissions";
	}

}

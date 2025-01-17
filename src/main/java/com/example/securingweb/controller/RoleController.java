/**
 * 
 */
package com.example.securingweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.securingweb.entities.Permission;
import com.example.securingweb.entities.Role;
import com.example.securingweb.services.SecurityService;
import com.example.securingweb.validators.RoleValidator;

import jakarta.validation.Valid;

/**
 *
 */
@Controller
//@Secured(SecurityUtil.MANAGE_ROLES)
public class RoleController extends BaseController
{
	private static final String viewPrefix = "roles/";
	
	@Autowired protected SecurityService securityService;
	@Autowired private RoleValidator roleValidator;

	@Override
	protected String getHeaderTitle()
	{
		return "Manage Roles";
	}
	
	@ModelAttribute("permissionsList")
	public List<Permission> permissionsList(){
		return securityService.getAllPermissions();
	}
	
	@GetMapping(path = "/roles") 
	//@RequestMapping(value="/roles", method=RequestMethod.GET)
	public String listRoles(Model model) {
		List<Role> list = securityService.getAllRoles();
		model.addAttribute("roles",list);
		return viewPrefix+"roles";
	}
	
	@GetMapping(path = "/roles/new") 
	//@RequestMapping(value="/roles/new", method=RequestMethod.GET)
	public String createRoleForm(Model model) {
		Role role = new Role();
		model.addAttribute("role",role);
		//model.addAttribute("permissionsList",securityService.getAllPermissions());		
		
		return viewPrefix+"create_role";
	}

	
	@PostMapping(value="/roles")
	//@RequestMapping(value="/roles", method=RequestMethod.POST)
	public String createRole(@Valid @ModelAttribute("role") Role role, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		roleValidator.validate(role, result);
		if(result.hasErrors()){
			return viewPrefix+"create_role";
		}
		Role persistedRole = securityService.createRole(role);
		logger.debug("Created new role with id : {} and name : {}", persistedRole.getId(), persistedRole.getName());
		redirectAttributes.addFlashAttribute("info", "Role created successfully");
		return "redirect:/roles";
	}
	
	
	@GetMapping(path = "/roles/{id}") 
	//@RequestMapping(value="/roles/{id}", method=RequestMethod.GET)
	public String editRoleForm(@PathVariable Integer id, Model model) {
		Role role = securityService.getRoleById(id);
		Map<Integer, Permission> assignedPermissionMap = new HashMap<>();
		List<Permission> permissions = role.getPermissions();
		for (Permission permission : permissions)
		{
			assignedPermissionMap.put(permission.getId(), permission);
		}
		List<Permission> rolePermissions = new ArrayList<>();
		List<Permission> allPermissions = securityService.getAllPermissions();
		for (Permission permission : allPermissions)
		{
			if(assignedPermissionMap.containsKey(permission.getId())){
				rolePermissions.add(permission);
			} else {
				rolePermissions.add(null);
			}
		}
		role.setPermissions(rolePermissions);
		model.addAttribute("role",role);
		//model.addAttribute("permissionsList",allPermissions);		
		return viewPrefix+"edit_role";
	}
	
	@PostMapping(value="/roles/{id}")
	//@RequestMapping(value="/roles/{id}", method=RequestMethod.POST)
	public String updateRole(@ModelAttribute("role") Role role, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {		
		Role persistedRole = securityService.updateRole(role);
		logger.debug("Updated role with id : {} and name : {}", persistedRole.getId(), persistedRole.getName());
		redirectAttributes.addFlashAttribute("info", "Role updated successfully");
		return "redirect:/roles";
	}
	
}

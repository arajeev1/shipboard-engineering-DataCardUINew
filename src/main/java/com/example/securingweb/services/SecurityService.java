/**
 * 
 */
package com.example.securingweb.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.securingweb.entities.Permission;
import com.example.securingweb.entities.Role;
import com.example.securingweb.entities.User;
import com.example.securingweb.exceptions.AppException;
import com.example.securingweb.repository.PermissionRepository;
import com.example.securingweb.repository.RoleRepository;
import com.example.securingweb.repository.UserRepository;

/**
 *
 */
@Service
@Transactional
public class SecurityService
{
	@Autowired UserRepository userRepository;
	@Autowired PermissionRepository permissionRepository;
	@Autowired RoleRepository roleRepository;
	
	public User findUserByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
	
	public String resetPassword(String email)
	{
		User user = findUserByEmail(email);
		if(user == null)
		{
			throw new AppException("Invalid email address");
		}
		String uuid = UUID.randomUUID().toString();
		user.setPasswordResetToken(uuid);
		return uuid;
	}

	public void updatePassword(String email, String token, String password)
	{
		User user = findUserByEmail(email);
		if(user == null)
		{
			throw new AppException("Invalid email address");
		}
		if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())){
			throw new AppException("Invalid password reset token");
		}
		user.setPassword(password);
		user.setPasswordResetToken(null);
	}

	public boolean verifyPasswordResetToken(String email, String token)
	{
		User user = findUserByEmail(email);
		if(user == null)
		{
			throw new AppException("Invalid email address");
		}
		if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())){
			return false;
		}
		return true;
	}
	
	public List<Permission> getAllPermissions() {
		return permissionRepository.findAll();
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}
	

	public Role getRoleByName(String roleName)
	{
		return roleRepository.findByName(roleName);
	}
	
	public Role createRole(Role role)
	{
		Role roleByName = getRoleByName(role.getName());
		if(roleByName != null){
			throw new AppException("Role "+role.getName()+" already exist");
		}
		List<Permission> persistedPermissions = new ArrayList<>();
		List<Permission> permissions = role.getPermissions();
		if(permissions != null){
			for (Permission permission : permissions) {
				if(permission.getId() != null)
				{
					persistedPermissions.add(permissionRepository.findById(permission.getId()).get());
				}
			}
		}
		
		role.setPermissions(persistedPermissions);
		return roleRepository.save(role);
	}
	
	public Role updateRole(Role role)
	{
		Role persistedRole = getRoleById(role.getId());
		if(persistedRole == null){
			throw new AppException("Role "+role.getId()+" doesn't exist");
		}
		persistedRole.setDescription(role.getDescription());
		List<Permission> updatedPermissions = new ArrayList<>();
		List<Permission> permissions = role.getPermissions();
		if(permissions != null){
			for (Permission permission : permissions) {
				if(permission.getId() != null)
				{
					//Optional<Permission> opt permissionRepository.findOne(permission.getId());
					updatedPermissions.add(permissionRepository.findById(permission.getId()).get());
				}
			}
		}
		persistedRole.setPermissions(updatedPermissions);
		return roleRepository.save(persistedRole);
	}
	

	public Role getRoleById(Integer id) 
	{
		return roleRepository.findById(id).get();
		//return roleRepository.findOne(id);
	}
	
	public User getUserById(Integer id)
	{
		return userRepository.findById(id).get();
		//return userRepository.findOne(id);
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User createUser(User user)
	{
		User userByEmail = findUserByEmail(user.getEmail());
		if(userByEmail != null){
			throw new AppException("Email "+user.getEmail()+" already in use");
		}
		List<Role> persistedRoles = new ArrayList<>();
		List<Role> roles = user.getRoles();
		if(roles != null){
			for (Role role : roles) {
				if(role.getId() != null)
				{
					persistedRoles.add(roleRepository.findById(role.getId()).get());					
				}
			}
		}
		user.setRoles(persistedRoles);
		
		return userRepository.save(user);
	}
	
	public User updateUser(User user)
	{
		User persistedUser = getUserById(user.getId());
		if(persistedUser == null){
			throw new AppException("User "+user.getId()+" doesn't exist");
		}
		
		List<Role> updatedRoles = new ArrayList<>();
		List<Role> roles = user.getRoles();
		if(roles != null){
			for (Role role : roles) {
				if(role.getId() != null)
				{
					updatedRoles.add(roleRepository.findById(role.getId()).get());
					//updatedRoles.add(roleRepository.findOne(role.getId()));
				}
			}
		}
		persistedUser.setRoles(updatedRoles);
		return userRepository.save(persistedUser);
	}

}

/**
 * 
 */
package com.example.securingweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Role;


/**
 *
 */
public interface RoleRepository extends JpaRepository<Role, Integer>
{

	Role findByName(String name);

	//@Query("SELECT s FROM Role s WHERE s.user=?1")
	//public List<Role> findByRoles(User user);
}

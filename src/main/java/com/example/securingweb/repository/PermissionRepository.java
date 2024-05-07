/**
 * 
 */
package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Permission;


/**
 *
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer>
{

}

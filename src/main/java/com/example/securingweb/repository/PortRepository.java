/**
 * 
 */
package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Port;

/**
 *
 */
public interface PortRepository extends JpaRepository<Port, Integer>
{

	Port findByCode(String code);

}

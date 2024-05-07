/**
 * 
 */
package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.ShipClass;

/**
 *
 */
public interface ShipClassRepository extends JpaRepository<ShipClass, Integer>
{

	ShipClass findByCode(String code);

}

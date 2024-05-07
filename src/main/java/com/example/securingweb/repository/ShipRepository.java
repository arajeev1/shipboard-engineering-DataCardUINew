/**
 * 
 */
package com.example.securingweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Ship;

/**
 *
 */
public interface ShipRepository extends JpaRepository<Ship, Integer>
{

	Ship findByCode(String code);
	public List<Ship> findByBrandIdOrderByCodeAsc(Integer brandId);
	public List<Ship> findAllByOrderByCodeAsc();

}

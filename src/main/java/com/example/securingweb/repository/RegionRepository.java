/**
 * 
 */
package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Region;

/**
 *
 */
public interface RegionRepository extends JpaRepository<Region, Integer>
{

	Region findByCode(String code);

}

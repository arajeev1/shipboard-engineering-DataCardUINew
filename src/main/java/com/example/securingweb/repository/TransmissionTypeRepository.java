/**
 * 
 */
package com.example.securingweb.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.TransmissionType;

/**
 *
 */
public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Integer>
{

	TransmissionType findByCode(String code);
	
	List<TransmissionType> findByBrandId(Integer brandId);

}

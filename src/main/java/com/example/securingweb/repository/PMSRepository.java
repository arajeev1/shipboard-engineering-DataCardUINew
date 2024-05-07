/**
 * 
 */
package com.example.securingweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.PMS;

/**
 *
 */
public interface PMSRepository extends JpaRepository<PMS, Integer>
{

	PMS findByCode(String code);

}

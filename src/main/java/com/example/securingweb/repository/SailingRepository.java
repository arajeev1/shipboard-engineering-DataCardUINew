/**
 * 
 */
package com.example.securingweb.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.AS400;

/**
 *
 */
public interface SailingRepository extends JpaRepository<AS400, Integer>
{

	Optional<AS400> findById(Integer id);

}

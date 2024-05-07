/**
 * 
 */
package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Brand;

/**
 *
 */
public interface BrandRepository extends JpaRepository<Brand, Integer>
{

	Brand findByCode(String code);

}

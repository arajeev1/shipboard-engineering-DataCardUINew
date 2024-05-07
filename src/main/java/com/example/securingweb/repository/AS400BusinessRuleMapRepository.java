/**
 * 
 */
package com.example.securingweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.AS400BusinessRuleMap;

/**
 *
 */
public interface AS400BusinessRuleMapRepository extends JpaRepository<AS400BusinessRuleMap, Integer>
{

	Optional<AS400BusinessRuleMap> findById(Integer id);
	
//	@Query("select t1.*, t5.code from as400 t1, ship t2, brand_transmission_type t3, port t4, business_rule t5 where t1.ship = t2.code and t2.brand_id = t3.brand_id and t4.code = t1.embarking_port and t5.pms_id = t2.pms_id and t5.region_id = t4.region_id and t5.transmission_type_id = t3.transmission_type_id")
//	public List<AS400BusinessRuleMap> findTomEvents();

}

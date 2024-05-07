/**
 * 
 */
package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.example.securingweb.entities.BusinessRule;
import com.example.securingweb.entities.PMS;
import com.example.securingweb.entities.Region;
import com.example.securingweb.entities.TransmissionType;

/**
 *
 */
public interface BusinessRuleRepository extends JpaRepository<BusinessRule, Integer>
{

	BusinessRule findByCode(String code);
	
	@Query("SELECT br FROM BusinessRule br WHERE br.pmsBR =?1 and br.regionBR =?2 and br.transmissionTypeBR =?3")
	public BusinessRule findBusinessRuleByPmsRegionTransmission(PMS pms_id, Region region_id, TransmissionType transmission_id);

	@Procedure
	void insertAS400business_rule_map();
}

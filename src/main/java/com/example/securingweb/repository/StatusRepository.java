package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.example.securingweb.entities.BusinessRule;

public interface StatusRepository extends JpaRepository<BusinessRule, Integer>{

	@Procedure
	void insertScheduleData(Integer paramID, String paramEvent);
}

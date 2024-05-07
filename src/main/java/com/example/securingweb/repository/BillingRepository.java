package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Billing;

public interface BillingRepository extends JpaRepository<Billing, String>{
	
	Billing findByBillingNo(Integer billingNo);

}

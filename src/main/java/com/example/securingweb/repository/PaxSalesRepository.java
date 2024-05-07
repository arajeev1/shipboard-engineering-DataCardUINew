package com.example.securingweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.PaxSales;

public interface PaxSalesRepository extends JpaRepository<PaxSales, Integer>{
	
	List<PaxSales> findByBillingNo(Integer billingNo);

}

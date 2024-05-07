package com.example.securingweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Lab;

public interface LabRepository extends JpaRepository<Lab, Integer>{
	
	Lab findByName(String name);

}

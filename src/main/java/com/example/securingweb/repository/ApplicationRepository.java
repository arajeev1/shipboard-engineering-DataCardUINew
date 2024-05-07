package com.example.securingweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	
	Application findByName(String name);

}

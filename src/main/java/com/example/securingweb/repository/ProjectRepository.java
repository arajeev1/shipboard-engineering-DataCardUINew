package com.example.securingweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	Project findByName(String name);

}

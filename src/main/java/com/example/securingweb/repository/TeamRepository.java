package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Integer>{
	
	Team findByName(String name);

}

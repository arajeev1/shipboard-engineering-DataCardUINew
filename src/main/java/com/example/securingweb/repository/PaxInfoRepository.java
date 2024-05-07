package com.example.securingweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.PaxInfo;

public interface PaxInfoRepository extends JpaRepository<PaxInfo, String>{
	
	PaxInfo findByCardNo(String cardNo);

}

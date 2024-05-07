/**
 * 
 */
package com.example.securingweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.entities.EmailConfig;

/**
 * @author User
 *
 */
public interface EmailConfigRepository extends JpaRepository<EmailConfig, Integer> {

	EmailConfig findByEmailHost(String emailHost);
	
	/*@Query("SELECT e FROM EmailConfig e WHERE emailHost !=:emailHost")
	public EmailConfig findFirstByOrderByIdDesc(@Param("emailHost") String emailHost, Pageable pageable);*/
	
}

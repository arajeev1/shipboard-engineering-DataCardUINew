/**
 * 
 */
package com.example.securingweb.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.securingweb.entities.Itinerary;

/**
 *
 */
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer>
{

	Optional<Itinerary> findById(Integer id);
	
	@Query("SELECT s FROM Itinerary s WHERE s.CruisePackageCode =?1 and s.sailDate =?2")
	public List<Itinerary> findItineraryByCruisePackageCodesailDate(String CruisePackageCode, String sailDate);
}

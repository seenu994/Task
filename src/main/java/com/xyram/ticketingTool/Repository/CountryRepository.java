package com.xyram.ticketingTool.Repository;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.City;
import com.xyram.ticketingTool.entity.Country;
 
@Repository
@Transactional
public interface CountryRepository  extends JpaRepository<Country, String>{

	
	@Query("Select c from Country c where c.countryId =:countryId")
	Country getCountryById(String countryId);

	
	@Query("Select distinct new map(c.countryId as countryId, c.countryName as countryName, c.countryStatus as countryStatus) from Country c")
	Page<Map> getAllCountry(Pageable pageable); 

	
}

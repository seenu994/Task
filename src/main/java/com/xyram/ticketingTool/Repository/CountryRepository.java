package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.City;
import com.xyram.ticketingTool.entity.Country;
 
@Repository
@Transactional
public interface CountryRepository  extends JpaRepository<Country, String>{

	
	@Query("Select c from Country c where c.countryId =:countryId")
	Country getCountryById(String countryId);

	
	@Query("Select distinct new map(c.countryId as countryId, c.countryName as countryName, c.countryStatus as countryStatus) from Country c")
	Page<Map> getAllCountry(Pageable pageable); 

	
	@Query("Select distinct c from Country c where c.countryId=:countryId")
	Country getCountryById1(String countryId);
	
	
	@Query("Select distinct c from Country c where c.countryCode=:countryCode")
	Country getCountryCode(String countryCode);

	@Query("Select distinct c from Country c where c.countryCode=:countryCode")
	String getCountryCodes(String countryCode);
	
	@Query("select c from Country c where c.countryName =:countryName")
	Country getCountryName(String countryName);



	@Query("select distinct new map(c.countryName as countryName,c.countryId as countryId,c.countryCode as countryCode) from Country c where "
			  + "lower(c.countryName) LIKE %:searchString% ")
	List<Map> searchCountry(String searchString);
}

//@Query("select c.countryId as countryId from Country c where c.countryName=:countryName")
//	String getCountryId(String countryName);



/*@Query("Select c.countryId from Country c where c.countryName =:countryName ")
String getCountryNames(String countryName);

@Query("Select c from Country c where c.countryName =:country ")
Country getCountry(String country);


@Query("Select c from Country c where c.countryCode =:country ")
Country getCountries(String country);

@Query("Select c.countryId from Country c where c.countryCode =:countryCode ")

String getCountryCodes(String countryCode);
}*/

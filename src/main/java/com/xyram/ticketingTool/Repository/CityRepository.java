package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.City;

@Repository
@Transactional

public interface CityRepository  extends JpaRepository<City, String>{
	
	
	@Query("Select c from City c where c.cityId =:cityId")
	City getCityById(String cityId);

	
	@Query("Select distinct new map(c.cityId as cityId, c.cityName as cityName, c.cityStatus as cityStatus , c.countryCode as countryCode) from City c")
	Page<Map> getAllCity(Pageable pageable);

	@Query("select c from City c where c.cityName =:cityName")
	City getCityName(String cityName);

//	@Query("select distinct new map(c.cityName as cityName, c.cityId as cityId) from City c where lower(c.cityName) LIKE %:searchString% ")
//	List<City> searchCity(String searchString);
	
	@Query("select distinct new map(c.cityId as cityId,c.cityName as cityName,c.countryCode as countryCode) from City c where "
			+ "lower(c.cityName) LIKE %:searchString% ")
			
	List<Map> searchCity(String searchString);

}

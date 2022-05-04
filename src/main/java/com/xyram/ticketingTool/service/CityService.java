package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.City;

public interface CityService {

	
	ApiResponse addcity(City city);
	ApiResponse editcity(City city, String cityId);
	ApiResponse getAllCity(Pageable pageable);
	ApiResponse deleteCity(String cityId);
	ApiResponse searchCity(String searchString);
}

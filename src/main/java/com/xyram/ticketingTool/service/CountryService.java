package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Country;

public interface CountryService {

	
	ApiResponse addcountry(Country country);
    ApiResponse editcountry(Country country ,String countryId);
    ApiResponse getAllcountry(Pageable pageable);
    ApiResponse deletecountry(String countryId);
    ApiResponse searchCountry(String searchString);
}

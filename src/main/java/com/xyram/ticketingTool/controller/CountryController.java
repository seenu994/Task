package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.City;
import com.xyram.ticketingTool.entity.Country;
import com.xyram.ticketingTool.service.CountryService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController
public class CountryController {
	
private final Logger logger = LoggerFactory.getLogger(CountryController.class);
	
	@Autowired
	CountryService countyService;
	
	@PostMapping(value = {AuthConstants.INFRA_ADMIN_BASEPATH + "/addCountry", 
            AuthConstants.ADMIN_BASEPATH + "/addCountry",
            AuthConstants.INFRA_USER_BASEPATH + "/addCountry"})
     public ApiResponse addCountry(@RequestBody Country country) {
     logger.info("Received request to add Country");
     return countyService.addcountry(country);
}
	
	@PutMapping(value = {AuthConstants.INFRA_ADMIN_BASEPATH + "/editCountry/{countryId}", 
            AuthConstants.ADMIN_BASEPATH + "/editCountry/{countryId}",
            AuthConstants.INFRA_USER_BASEPATH + "/editCountry/{countryId}"})
    public ApiResponse editCountry(@RequestBody Country country, @PathVariable String countryId) {
        logger.info("Received request to edit City");
        return countyService.editcountry(country, countryId);
	}
	
	@GetMapping(value = {AuthConstants.ADMIN_BASEPATH + "/getAllCountry",
	   		  AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllCountry",
	             AuthConstants.INFRA_USER_BASEPATH + "/getAllCountry"})
	     public ApiResponse getAllCountry(Pageable pageable) {
	 	        logger.info("Received request to get all country");
	 			return countyService.getAllcountry(pageable);
	 	 }
	
	@DeleteMapping(value = { AuthConstants.ADMIN_BASEPATH + "/deleteCountry/{countryId}",
 			AuthConstants.INFRA_ADMIN_BASEPATH + "/deleteCountry/{countryId}",			
 			AuthConstants.INFRA_USER_BASEPATH + "/deleteCountry/{countryId}"})
 	ApiResponse deleteCountry(@PathVariable String countryId) {
 		logger.info("Received request to delete country");
 		return countyService.deletecountry(countryId);
 	}
	
	@GetMapping(value = {AuthConstants.ADMIN_BASEPATH + "/searchCountry/{countryName}",
			 AuthConstants.INFRA_ADMIN_BASEPATH + "/searchCountry/{countryName}",
            AuthConstants.INFRA_USER_BASEPATH + "/searchCountry/{countryName}"})
	 public ApiResponse searchCountry(@PathVariable String countryName) {
			logger.info("Received request to search Country");
			return countyService.searchCountry(countryName);
	 }
		 

}

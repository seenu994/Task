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
import com.xyram.ticketingTool.service.CityService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController


public class CityController {
	
	private final Logger logger = LoggerFactory.getLogger(CityController.class);
	
	@Autowired
	CityService cityService;
	
	@PostMapping(value = {AuthConstants.INFRA_ADMIN_BASEPATH + "/addCity", 
            AuthConstants.ADMIN_BASEPATH + "/addCity",
            AuthConstants.INFRA_USER_BASEPATH + "/addCity"})
     public ApiResponse addCity(@RequestBody City city) {
     logger.info("Received request to add City");
     return cityService.addcity(city);
}
	
	@PutMapping(value = {AuthConstants.INFRA_ADMIN_BASEPATH + "/editCity/{cityId}", 
            AuthConstants.ADMIN_BASEPATH + "/editCity/{cityId}",
            AuthConstants.INFRA_USER_BASEPATH + "/editCity/{cityId}"})
    public ApiResponse editCity(@RequestBody City city, @PathVariable String cityId) {
        logger.info("Received request to edit City");
        return cityService.editcity(city, cityId);
	}
	
	 @GetMapping(value = {AuthConstants.ADMIN_BASEPATH + "/getAllCity",
   		  AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllCity",
             AuthConstants.INFRA_USER_BASEPATH + "/getAllCity"})
     public ApiResponse getAllCity(Pageable pageable) {
 	        logger.info("Received request to get all City");
 			return cityService.getAllCity(pageable);
 	 }
	 
	 @DeleteMapping(value = { AuthConstants.ADMIN_BASEPATH + "/deleteCity/{cityId}",
	 			AuthConstants.INFRA_ADMIN_BASEPATH + "/deleteCity/{cityId}",			
	 			AuthConstants.INFRA_USER_BASEPATH + "/deleteCity/{cityId}"})
	 	ApiResponse deleteCity(@PathVariable String cityId) {
	 		logger.info("Received request to delete city");
	 		return cityService.deleteCity(cityId);
	 	}

}

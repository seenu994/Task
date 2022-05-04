package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.CityRepository;
import com.xyram.ticketingTool.Repository.CountryRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.City;
import com.xyram.ticketingTool.entity.Country;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.CityService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional

public class CityServiceImpl  implements CityService {
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	CurrentUser currentUser;
	
	public ApiResponse addcity(City city) {
		ApiResponse response = new ApiResponse(false);
		response = validateCity(city); 
		if (response.isSuccess()) {
			if (city != null) {
				city.setCreatedAt(new Date());
			    city.setCreatedBy(currentUser.getUserId());
				 city.setCityStatus(true);
					cityRepository.save(city);
					response.setSuccess(true);
				response.setMessage(ResponseMessages.CITY_ADDED);
			}
			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.CITY_NOT_ADDED);
			}
		}
		return response;
	}
	
	
	private ApiResponse validateCity(City city) {
		ApiResponse response = new ApiResponse(false);
	City  cityRequest = cityRepository.getCityName(city.getCityName());
	String regex = "[a-z A-Z]+";
		if(city.getCityName() == null || city.getCityName().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"City name is mandatory");
		}
		if(!city.getCityName().matches(regex)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "city name should be character only");
		}
		
		if(cityRequest != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " CityName already exists!");
		}
		if(city.getCityName().length() < 2 || city.getCityName().length() > 15){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "city character length should be greater than 1 and less than 16");
		}
		
		
		if (city.getCountryCode() == null || city.getCountryCode().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "countryCode is mandatory");
		} else {
			
			Country country = countryRepository.getCountryCode(city.getCountryCode());
			if (country == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "countryCode is not valid");
			}
			}
		
	    response.setSuccess(true);
	    return response;
	}



	@Override
	public ApiResponse editcity(City city, String cityId) {
		
		
		ApiResponse response = new ApiResponse();
		response = validateCity(city);
		if (response.isSuccess()) {
			City cityRequest = cityRepository.getCityById(cityId);
			if (cityRequest != null) {
				
				cityRequest.setCityName(city.getCityName());
				//cityRequest.setCountryCode(city.getCountryCode());

				cityRequest.setLastUpdatedAt(new Date());
				cityRequest.setUpdatedBy(currentUser.getName());

				cityRepository.save(cityRequest);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.CITY_EDITED);

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.CITY_DETAILS_INVALID);
				// response.setContent(null);
			}

		}
		return response;

	}

	@Override
	public ApiResponse getAllCity(Pageable pageable) {
		ApiResponse response = new ApiResponse();
		Page<Map> city = cityRepository.getAllCity(pageable);
	
		Map content = new HashMap();
		content.put("city", city);
		if(content != null) {
			response.setSuccess(true);
			response.setContent(content);
			response.setMessage(ResponseMessages.CITY_LIST_RETRIVED);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}


	@Override
	public ApiResponse deleteCity(String cityId) {
		
		
		ApiResponse response = new ApiResponse(false);
		City cityObj = cityRepository.getCityById(cityId);
		if (cityObj != null) {
			
			cityRepository.delete(cityObj);
			response.setSuccess(true);
			response.setMessage("City deleted successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("city Id is not valid.");
		}
		return response;
	}


	@Override
	public ApiResponse searchCity(String searchString) {
		
		ApiResponse response = new ApiResponse(false);
		
		List<Map> city = cityRepository.searchCity(searchString);

		Map content = new HashMap();
		content.put("city", city);
		if (content != null) {			
			response.setSuccess(true);
			response.setMessage("City retrived successfully");
			response.setContent(content);
			
		} else {
			
			response.setSuccess(false);
			
			response.setMessage("Not retrived the data");
		}

		return response;
		
	}
		

		
	}
		




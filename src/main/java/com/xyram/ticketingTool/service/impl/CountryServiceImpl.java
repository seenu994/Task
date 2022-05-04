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

import com.xyram.ticketingTool.Repository.CountryRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Country;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.CountryService;
import com.xyram.ticketingTool.util.ResponseMessages;
@Service
@Transactional
public class CountryServiceImpl implements CountryService{

	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	CurrentUser currentUser;
	
	public ApiResponse addcountry(Country country) {
	
		
			ApiResponse response = new ApiResponse(false);
			response = validateCountry(country); 
			if (response.isSuccess()) {
				if (country != null) {
					
					
					country.setCreatedAt(new Date());
					country.setCreatedBy(currentUser.getUserId());
				country.setCountryStatus(true);
					countryRepository.save(country);
					response.setSuccess(true);
					response.setMessage(ResponseMessages.COUNTRY_ADDED);
				}
				else {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.COUNTRY_NOT_ADDED);
				}
			}
			return response;
		
	}

	private ApiResponse validateCountry(Country country) {
		ApiResponse response = new ApiResponse(false);
		String regex = "[a-z A-Z]+";
		Country  countryRequest = countryRepository.getCountryName(country.getCountryName());
		
		
		if (country.getCountryName() == null || country.getCountryName().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "countryName is mandatory");
		}
		
		
		if(!country.getCountryName().matches(regex)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "country name should be character only");
		}
		
		
		if(countryRequest != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " CountryName already exists!");
		}
		
		
		 if(country.getCountryName().length() < 2 || country.getCountryName().length() > 15){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "country character length should be greater than 1 and less than 16");
			}
		 
		
		// Country countryObj = countryRepository.getById(country.getCountryName());
		//String countryObjs = countryRepository.getCountryCodes(country.getCountryCode());
		if(country.getCountryCode() == null || country.getCountryCode().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"country code is mandatory");
		}
		//if(!country.equals(countryObjs)) {
			
		 if(countryRequest != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CountryCode is already exist!");
			}
		
		
		response.setSuccess(true);
		return response;
		}

	@Override
	public ApiResponse editcountry(Country country , String countryId) {
		ApiResponse response = new ApiResponse();
		response = validateCountry(country);
		if (response.isSuccess()) {
			Country countryRequest = countryRepository.getCountryById(countryId);
			if (countryRequest != null) {
				
				countryRequest.setCountryName(country.getCountryName());

				countryRequest.setLastUpdatedAt(new Date());
				countryRequest.setUpdatedBy(currentUser.getName());

				countryRepository.save(countryRequest);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.COUNTRY_EDITED);

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.COUNTRY_DETAILS_INVALID);
				// response.setContent(null);
			}

		}
		return response;

	}

	@Override
	public ApiResponse getAllcountry(Pageable pageable) {
		ApiResponse response = new ApiResponse();
		Page<Map> country = countryRepository.getAllCountry(pageable);
	
		Map content = new HashMap();
		content.put("country", country);
		if(content != null) {
			response.setSuccess(true);
			response.setContent(content);
			response.setMessage(ResponseMessages.COUNTRY_LIST_RETRIVED);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}

	@Override
	public ApiResponse deletecountry(String countryId) {
		ApiResponse response = new ApiResponse(false);
		Country countryObj = countryRepository.getCountryById(countryId);
		if (countryObj != null) {		
			countryRepository.delete(countryObj);
			response.setSuccess(true);
			response.setMessage("Country deleted successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("country Id is not valid.");
		}
		return response;
	}


	@Override
	public ApiResponse searchCountry(String searchString) {
		ApiResponse response = new ApiResponse(false);
		
		List<Map> country = countryRepository.searchCountry(searchString);

		Map content = new HashMap();
		content.put("Country", country);
		if (content != null) {			
			response.setSuccess(true);
			response.setMessage("Country retrived successfully");
			response.setContent(content);
			
		} else {
			
			response.setSuccess(false);
			
			response.setMessage("Not retrived the data");
		}

		return response;
		
	}
	
	}
	
	



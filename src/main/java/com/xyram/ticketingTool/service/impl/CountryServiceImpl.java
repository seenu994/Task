package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
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
import com.xyram.ticketingTool.entity.City;
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
		//String regex = "[a-zA-Z]+";
		//Brand brandObj = cityRepository.getCity(city.getCityName());
		if (country.getCountryName() == null || country.getCountryName().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "countryName is mandatory");
		} 
		if(country.getCountryCode() == null || country.getCountryCode().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"country code is mandatory");
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
				// softwareMasterRequest.setSoftwareId(software.getSoftwareId());
				countryRequest.setCountryName(country.getCountryName());

				//cityRequest.setLastUpdatedAt(new Date());
				//cityRequest.setUpdatedBy(currentUser.getName());

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
//			if(!brandObj.getCreatedBy().equals(currentUser.getUserId())) {
//				response.setSuccess(false);
//				response.setMessage("Not authorised to delete this brand");
//			}
			countryRepository.delete(countryObj);
			response.setSuccess(true);
			response.setMessage("Country deleted successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("country Id is not valid.");
		}
		return response;
	}
		
		

		
	}
	
	



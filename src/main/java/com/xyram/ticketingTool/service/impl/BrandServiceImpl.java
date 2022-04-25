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

import com.xyram.ticketingTool.Repository.BrandRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Brand;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.BrandService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class BrandServiceImpl implements BrandService{

	@Autowired
	BrandRepository brandRepository;
	
	@Autowired
	CurrentUser currentUser;
	
	@Override
	public ApiResponse addbrand(Brand brand) {
		ApiResponse response = new ApiResponse(false);
		response = validateAsset(brand); 
		if (response.isSuccess()) {
			if (brand != null) {
			    brand.setCreatedAt(new Date());
			    brand.setCreatedBy(currentUser.getUserId());
				brandRepository.save(brand);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.BRAND_ADDED);
			}
			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.BRAND_NOT_ADDED);
			}
		}
		return response;
	}

	private ApiResponse validateAsset(Brand brand) {
		ApiResponse response = new ApiResponse(false);

		if (brand.getBrandName() == null || brand.getBrandName().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand is mandatory");
		} 
		else if(brand.getBrandName().length() < 2){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand is not valid");
		}
		response.setSuccess(true);
		return response;
	}

	@Override
	public ApiResponse editbrand(Brand brand, String brandId) {
		ApiResponse response = new ApiResponse(false);
		
		Brand brandObj = brandRepository.getBrandById(brandId);
		if(brandObj != null) {
		   if(brand.getBrandName() != null) {
		    checkBrandName(brand.getBrandName());
		    brandObj.setBrandName(brand.getBrandName());
		    }
		   brandObj.setLastUpdatedAt(new Date());
		   brandObj.setUpdatedBy(currentUser.getUserId());
		   brandRepository.save(brandObj);
		   response.setSuccess(true);
		   response.setMessage(ResponseMessages.BRAND_EDITED);
		}
		else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.BRAND_NOT_EDITED);
		}
		return response;
	}
	private boolean checkBrandName(String brandName) {
		if(brandName.length() < 2){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand is not valid");
		}
		else {
		   return true;
		}
	}

	@Override
	public ApiResponse getAllBrand(Pageable pageable) {
		ApiResponse response = new ApiResponse();
		Page<Map> brand = brandRepository.getAllBrand(pageable);
	
		Map content = new HashMap();
		content.put("brand", brand);
		if(content != null) {
			response.setSuccess(true);
			response.setContent(content);
			response.setMessage(ResponseMessages.BRAND_LIST_RETRIVED);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}
	
}

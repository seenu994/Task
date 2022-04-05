package com.xyram.ticketingTool.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.enumType.AssetVendorEnum;
import com.xyram.ticketingTool.service.AssetvendorService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssestVendorServiceImpl implements AssetvendorService {
	//private static final AssetVendor vendorDetail = null;

	//private static final AssetVendor AssetVendorRequest = null;

	private final Logger logger = LoggerFactory.getLogger(AssestVendorServiceImpl.class);

	@Autowired
	AssetVendorRepository assetVendorRepository;

	private ApiResponse response;

	@Override
	public ApiResponse addAssestVendor(AssetVendor vendor) {
		ApiResponse response = new ApiResponse(false);

		response = validateAssetVendor(vendor);
		if (response.isSuccess()) {
			AssetVendor vendorSave = assetVendorRepository.save(vendor);
			if (vendorSave != null) {
				Map content = new HashMap<>();
				content.put("vendorDetails", vendorSave);
				response.setSuccess(true);
				response.setContent(content);

			}
			return response;
		}
		
		return response;
	}
		

	private ApiResponse validateAssetVendor(AssetVendor Vendor) {
		ApiResponse response = new ApiResponse(false);
//		String email = assetVendorRepository.filterByEmail(Vendor.getEmail());

		if (!emailValidation(Vendor.getEmail())) {
			response.setMessage(ResponseMessages.EMAIL_INVALID);

			response.setSuccess(false);
		}


		else if (Vendor.getMobileNo().length() != 10) {
			response.setMessage(ResponseMessages.MOBILE_INVALID);

			response.setSuccess(false);
		}

		else {
			response.setMessage(ResponseMessages.VENDOR_ADDED);

			response.setSuccess(true);
		}

		return response;
	}

	private boolean emailValidation(String email) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	private ApiResponse validateStatus(AssetVendorEnum AssetVendorEnum) {
		ApiResponse response = new ApiResponse(false);
		if (AssetVendorEnum == AssetVendorEnum.ACTIVE || AssetVendorEnum == AssetVendorEnum.INACTIVE) {
			response.setMessage(ResponseMessages.STATUS_UPDATE);
			response.setSuccess(true);
		}
		

		else {
			response.setMessage(ResponseMessages.USERSTATUS_INVALID);
			response.setSuccess(false);

		}

		return response;
	}

	@Override
	public ApiResponse editassetVendor(AssetVendor Vendor,String vendorId) {

		ApiResponse response = new ApiResponse();
		response = validateAssetVendor(Vendor);
		AssetVendor vendor1 = assetVendorRepository.getById(vendorId);
		if (response.isSuccess()) {
			if (vendor1 != null) 
			{
				vendor1.setAddress(Vendor.getAddress());
				vendor1.setMobileNo(Vendor.getMobileNo());
				vendor1.setEmail(Vendor.getEmail());
				vendor1.setCity(Vendor.getCity());
				vendor1.setCountry(Vendor.getCountry() );
				vendor1.setAssetVendorStatus(Vendor.getAssetVendorStatus());
				assetVendorRepository.save(vendor1);
				//AssetVendor assetVendorAdded = new AssetVendor();
				response.setSuccess(true);
				response.setMessage(ResponseMessages.VENDOR_DETAILS_EDIT);
//				Map content = new HashMap();
//				content.put("vendorId", assetVendorAdded.getAssetVendor());
//				response.setContent(content);

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.VENDOR_DETAILS_INVALID);
				//response.setContent(null);
			}

		}
		return response;

	}

	public ApiResponse validateVendorId(AssetVendor vendor) {
		ApiResponse response = new ApiResponse(false);
		if (vendor.getVendorId() != null) {
			response.setMessage("success");
			response.setSuccess(true);
			//response.setContent(null);
		} else {
			response.setMessage(ResponseMessages.VENDOR_ADDED);
			response.setSuccess(false);
			//response.setContent(null);
		}
		return response;
	}


	@Override
	public ApiResponse getAllVendorList(java.awt.print.Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}



//	public ApiResponse getAllVendorList(Pageable pageable) {

//			Page<Map> vendorList = assetVendorRepository.getVendorList(pageable);
//
//		Map content = new HashMap();
//		content.put("VendorList", vendorList);
//		ApiResponse response = new ApiResponse(true);
//		response.setSuccess(true);
//		response.setContent(content);
//		return response;
//	}
//


	
	
		
		

}


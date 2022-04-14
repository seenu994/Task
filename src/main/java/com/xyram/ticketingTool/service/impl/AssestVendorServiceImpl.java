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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.enumType.AssetVendorEnum;
import com.xyram.ticketingTool.service.AssetvendorService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssestVendorServiceImpl implements AssetvendorService {
	// private static final AssetVendor vendorDetail = null;

	// private static final AssetVendor AssetVendorRequest = null;

	//private static final AssetVendorEnum AssetVendorEnum = null;

	private final Logger logger = LoggerFactory.getLogger(AssestVendorServiceImpl.class);

	@Autowired
	AssetVendorRepository assetVendorRepository;

	private ApiResponse response;

	@Override
	public ApiResponse addAssestVendor(AssetVendor vendor) {
		ApiResponse response = new ApiResponse(false);
		

		response = validateAssetVendor(vendor);
		//System.out.println("success"+response.isSuccess());
		if (true) {
		//	AssetVendor vendorSave = assetVendorRepository.save(vendor);
			if (vendor != null) {
				AssetVendor vendorSave = assetVendorRepository.save(vendor);
//System.out.print("asdf");
				//Map content = new HashMap<>();
				//content.put("vendorDetails", vendorSave);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.VENDOR_ADDED);
				//response.setContent(content);

			}
		
		}
		return response;
	}

	private ApiResponse validateAssetVendor(AssetVendor vendor) {
		ApiResponse response = new ApiResponse(false);
		response.setSuccess(true);
//		String email = assetVendorRepository.filterByEmail(Vendor.getEmail());

		if (!emailValidation(vendor.getEmail())) {
			response.setMessage(ResponseMessages.EMAIL_INVALID);
			response.setSuccess(false);

		}

		 if (vendor.getMobileNo() == null || vendor.getMobileNo().length() != 10) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vendor Mobile number is mandatory");
			 
			//response.setMessage(ResponseMessages.MOBILE_INVALID);

			//response.setSuccess(false);
		}

		else { 
			if (vendor.getVendorName().equals(""))  {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor name is mandatory");	
			}
			if (vendor.getAddress() == null || vendor.getAddress().equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vendor Address is mandatory");
			}
			 
				if(vendor.getCity() == null || vendor.getCity().equals("")) {
			
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vendor city is mandatory");
			}
			}
			response.setMessage(ResponseMessages.VENDOR_ADDED);

			
		

		return response;
}

	private boolean emailValidation(String email) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
		
	}

//	private ApiResponse validateStatus(AssetVendorEnum assetVendorEnum) {
//		ApiResponse response = new ApiResponse(false);
//		if (assetVendorEnum == AssetVendorEnum.ACTIVE || assetVendorEnum == AssetVendorEnum.INACTIVE) {
//			response.setMessage(ResponseMessages.STATUS_UPDATE);
//			response.setSuccess(true);
//		}
//
//		else {
//			response.setMessage(ResponseMessages.VENDORSTATUS_INVALID);
//			response.setSuccess(false);
//
//		}
//
//		return response;
//	}

	@Override
	public ApiResponse editassetVendor(AssetVendor vendor, String vendorId) {

		ApiResponse response = new ApiResponse();
		response = validateAssetVendor(vendor);
		AssetVendor vendorRequest = assetVendorRepository.getById(vendorId);
		if (response.isSuccess()) {
			if (vendorRequest != null) {
				vendorRequest.setAddress(vendor.getAddress());
				vendorRequest.setMobileNo(vendor.getMobileNo());
				vendorRequest.setVendorName(vendor.getVendorName());
				vendorRequest.setEmail(vendor.getEmail());
				vendorRequest.setCity(vendor.getCity());
				vendorRequest.setCountry(vendor.getCountry());
				vendorRequest.setAssetVendorStatus(vendor.getAssetVendorStatus());
				assetVendorRepository.save(vendorRequest);
				// AssetVendor assetVendorAdded = new AssetVendor();
				response.setSuccess(true);
				response.setMessage(ResponseMessages.VENDOR_DETAILS_EDIT);
//				Map content = new HashMap();
//				content.put("vendorId", assetVendorAdded.getAssetVendor());
//				response.setContent(content);

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.VENDOR_DETAILS_INVALID);
				// response.setContent(null);
			}

		}
		return response;

	}

//	public ApiResponse validateVendorId(AssetVendor vendor) {
//		ApiResponse response = new ApiResponse(false);
//		if (vendor.getVendorId() != null) {
//			response.setMessage("success");
//			response.setSuccess(true);
//			// response.setContent(null);
//		} else {
//			response.setMessage(ResponseMessages.VENDOR_ADDED);
//			response.setSuccess(false);
//			// response.setContent(null);
//		}
//		return response;
//	}

	public ApiResponse getAllVendorList(Pageable pageable) {

		Page<Map> vendorList = assetVendorRepository.getAllVendorList(pageable);

		Map content = new HashMap();
		content.put("VendorList", vendorList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		response.setMessage(ResponseMessages.GETALL_VENDOR_LIST);
		return response;
	}
	
	public ApiResponse updateassetVendorStatus(AssetVendor vendor, String vendorId) {
		ApiResponse response = validateAssetVendor(vendor);
		if (response.isSuccess()) {
		
		AssetVendor vendorRequest = assetVendorRepository.getById(vendorId);
		
		
		//ApiResponse response = validateAssetVendor(assetVendorStatus);
		//if (response.isSuccess()) {
			//AssetVendor assetVendor = assetVendorRepository.getById(vendorId);
			if (vendor != null) {
				//vendorRequest.setAssetVendorStatus(vendor);
				assetVendorRepository.save(vendor);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSETVENDOR_STATUS_UPDATED);
				//response.setContent(null);
			
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.VENDOR_DETAILS_INVALID);
			response.setContent(null);
		}
		}
		return response;
	}
//	@Override
//	public ApiResponse updateassetVendorStatus(String vendorId, AssetVendorEnum assetVendorEnum) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public ApiResponse updateassetVendorStatus(String vendorId, AssetVendorEnum assetVendorEnum) {
		
		//ApiResponse response = validateAssetVendor(assetVendorEnum);
		ApiResponse response= new ApiResponse(false);
		//if (response.isSuccess()) {
			AssetVendor assetVendor = assetVendorRepository.getVendorById( vendorId );

			if (assetVendor != null) {

				assetVendor.setAssetVendorStatus(assetVendorEnum);
				System.out.print(assetVendorEnum.toString());
				assetVendorRepository.save(assetVendor);

				// Employee employeere=new Employee();z

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSETVENDOR_STATUS_UPDATED);
				response.setContent(null);
			
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.VENDORSTATUS_INVALID);
			response.setContent(null);
		}

		return response;
	
	

}
		
		


}

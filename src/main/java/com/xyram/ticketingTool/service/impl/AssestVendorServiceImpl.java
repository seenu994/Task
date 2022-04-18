package com.xyram.ticketingTool.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.enumType.AssetIssueStatus;
import com.xyram.ticketingTool.enumType.AssetVendorEnum;
import com.xyram.ticketingTool.enumType.SoftwareEnum;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.AssetvendorService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssestVendorServiceImpl implements AssetvendorService {
	// private static final AssetVendor vendorDetail = null;

	// private static final AssetVendor AssetVendorRequest = null;

	// private static final AssetVendorEnum AssetVendorEnum = null;

	private final Logger logger = LoggerFactory.getLogger(AssestVendorServiceImpl.class);

	@Autowired
	AssetVendorRepository assetVendorRepository;
	
	@Autowired
	CurrentUser currentUser;
	
	

	private ApiResponse response;

	@Override
	public ApiResponse addAssestVendor(AssetVendor vendor) {
		ApiResponse response = new ApiResponse(false);

		response = validateAssetVendor(vendor);
		// System.out.println("success"+response.isSuccess());
		if (true) {
			// AssetVendor vendorSave = assetVendorRepository.save(vendor);
			if (vendor != null) {
				vendor.setCreatedAt(new Date());
				vendor.setCreatedBy(currentUser.getName());
				AssetVendor vendorSave = assetVendorRepository.save(vendor);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.VENDOR_ADDED);
				// response.setContent(content);

			}

		}
		return response;
	}

	private ApiResponse validateAssetVendor(AssetVendor vendor) {
		ApiResponse response = new ApiResponse(false);
		if (vendor.getEmail() == null)  {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mail id is mandatory");
		}
			response.setSuccess(true);
//		String email = assetVendorRepository.filterByEmail(Vendor.getEmail());

		if (!emailValidation(vendor.getEmail())) {
			response.setMessage(ResponseMessages.EMAIL_INVALID);
			response.setSuccess(false);

		}

		if (vendor.getMobileNo() == null || vendor.getMobileNo().length() != 10) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor Mobile number is mandatory");

			 //response.setMessage(ResponseMessages.MOBILE_INVALID);

			// response.setSuccess(false);
		}

		else {
			if (vendor.getVendorName().equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor name is mandatory");
			}
			if (vendor.getAddress() == null || vendor.getAddress().equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor Address is mandatory");
			}

			if (vendor.getCity() == null || vendor.getCity().equals("")) {

				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor city is mandatory");
		
			}
			if ( vendor.getCountry().equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Country is mandatory");
				
			}
			//return response;
		}
		//response.setMessage(ResponseMessages.VENDOR_ADDED);

		return response;
	}

	private boolean emailValidation(String email) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		
		return matcher.find();

	}


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

	
	/* public ApiResponse editassetVendor(AssetVendor vendor, String vendorId) {

		ApiResponse response = new ApiResponse(false);
		
		
		AssetVendor assetVendor = assetVendorRepository.getAssetVendorById(vendorId);
		
		 
		if(assetVendor != null) 
	    {	
			if(vendor.getAddress() != null)
			{
				checkAddress(vendor.getAddress());
				assetVendor.setAddress(vendor.getAddress());
			}
			
			if(assetVendor.getVendorId() != null) {
				checkVendorById(vendor.getVendorId());
			}
			
			
			if(assetVendor.getVendorName()!= null)
			{
				checkVendorName(vendor.getVendorName());
				assetVendor.setVendorName(vendor.getVendorName());
			}
//			else {
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"VendorName is mandatory");
//			}
			
//			if(assetVendor.getAddress() != null) {
//				checkAddress(vendor.getAddress());
//				assetVendor.setAddress(vendor.getAddress());
//			}
//			else {
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"adress is mandatory");
//			}
			
			if(assetVendor.getCity() != null) {
				checkCity(vendor.getCity());
				assetVendor.setCity(vendor.getCity());
			}
//			else {
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"city is mandatory");
//			}
			
			if(assetVendor.getCountry() != null) {
				checkCountry(vendor.getCountry());
				assetVendor.setCountry(vendor.getCountry());
			}
			
		if(vendor.getAssetVendorStatus() != null)
			{
				checkAssetVendorStatus(vendor.getAssetVendorStatus());
				assetVendor.setAssetVendorStatus
				(vendor.getAssetVendorStatus());
			}
			
			 
			if(assetVendor.getEmail() != null) {
				emailValidation(vendor.getEmail());
				
				assetVendor.setEmail(vendor.getEmail());
			}
			
			if (vendor.getMobileNo() == null || vendor.getMobileNo().length() != 10) {
				
				assetVendor.setMobileNo(vendor.getMobileNo());
			}
			else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vendor mobile number is mandatory");
			}
			
			
			if(vendor.getVendorId() != null) {
				assetVendor.setVendorId
				(vendor.getVendorId());
		    }
			
		
			
		assetVendorRepository.save(assetVendor);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.VENDOR_DETAILS_EDIT);
			
		}

		else 
		{
			response.setSuccess(false);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid vendorId");
			
		}
       return response;
	    }
	 
	 
	 
	 
	 private boolean checkCountry(String country) {
		 if(country == null) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid country");
		 }
		 return true;
	 }
	
		
	

	private boolean checkCity(String city) {
		if(city == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid city");
		}
		 return true;
	}	
	

	private boolean checkAssetVendorStatus(AssetVendorEnum assetVendorEnum)
		{
			AssetVendor assetVendor = new AssetVendor();
			if(assetVendorEnum == null)
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AssetVensorStatus is mandetory");
			}
			else
			{
				return true;
				//assetIssue.setAssetIssueStatus(AssetIssueStatus.CLOSE);
			}
		}
	
			
		
		private boolean checkAddress(String address) {
			if(address == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Address is not valid");
			}
			return true;
		}
		
		
	

	 private boolean checkVendorName(String vendorName) {
			//AssetVendor assetVendor = assetVendorRepository.getVendorName(vendorName);
			if(vendorName == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"VendorName is not valid");
			}
			return true;
		}
	 
	 
	 
	 private boolean checkVendorId(String vendorId) {
		// AssetVendor assetVendor = assetVendorRepository.getAssetVendorAddress(address);
		 if(vendorId == null) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"VendorId is not valid");
		 }
		 return true;
	 }
	 
	 private boolean checkVendorId(String vendorId) {
	    	AssetVendor assetVendor = assetVendorRepository.getVendorById(vendorId);
			if (assetVendor == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AssetVendor Id is not valid");
			}
			else {
				return true;
			}
		}*/
	 
	

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

	public ApiResponse getAllVendor(Map<String, Object> filter, Pageable peageble) {

		ApiResponse response = new ApiResponse(false);

		String vendorId = filter.containsKey("vendorId") ? ((String) filter.get("vendorId")) : null;

		String country = filter.containsKey("country") ? ((String) filter.get("country")) : null;

		String city = filter.containsKey("city") ? ((String) filter.get("city")) : null;

//		Page<Map> assetVendor;
//		try {
//			//assetVendor = AssetVendorRepository.getAllVendor(vendorId,country,city,peageble);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		

		Page<Map> assetVendor = assetVendorRepository.getAllVendor(vendorId, country, city, peageble);
		if (assetVendor.getSize() > 0) {

			Map content = new HashMap();
			content.put("assetVendor", assetVendor);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retreived successfully");
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty");
		}

		return response;

	}

//		Page<Map> vendorList = assetVendorRepository.getAllVendorList(pageable);
//		Map content = new HashMap();
//		content.put("VendorList", vendorList);
//		ApiResponse response = new ApiResponse(true);
//		response.setSuccess(true);
//		response.setContent(content);
//		response.setMessage(ResponseMessages.GETALL_VENDOR_LIST);
//		return response; 

	@Override
	public ApiResponse getVendorById(String vendorId) {
		ApiResponse response = new ApiResponse();
		AssetVendor assetVendor = assetVendorRepository.getVendorById(vendorId);
		Map content = new HashMap();
		content.put("assetVendor", assetVendor);
		if (content != null) {
			response.setSuccess(true);
			response.setMessage("AssetVendor Retrieved Successfully");
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage("AssetVendorList is empty");
		}
		return response;
	}

	public ApiResponse updateassetVendorStatus(AssetVendor vendor, String vendorId) {
		ApiResponse response = validateAssetVendor(vendor);
		if (response.isSuccess()) {

			AssetVendor vendorRequest = assetVendorRepository.getById(vendorId);

			// ApiResponse response = validateAssetVendor(assetVendorStatus);
			// if (response.isSuccess()) {
			// AssetVendor assetVendor = assetVendorRepository.getById(vendorId);
			if (vendor != null) {
				// vendorRequest.setAssetVendorStatus(vendor);
				assetVendorRepository.save(vendor);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSETVENDOR_STATUS_UPDATED);
				// response.setContent(null);

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.VENDOR_DETAILS_INVALID);
				response.setContent(null);
			}
		}
		return response;
	}

	@Override
	public ApiResponse updateassetVendorStatus(String vendorId, AssetVendorEnum assetVendorEnum) {

		// ApiResponse response = validateAssetVendor(assetVendorEnum);
		ApiResponse response = new ApiResponse(false);
		// if (response.isSuccess()) {
		AssetVendor assetVendor = assetVendorRepository.getVendorById(vendorId);

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

//	@Override
//	public ApiResponse searchAssetVendor(String searchString) {
//		
//			ApiResponse response = new ApiResponse(false);
//			List<AssetVendor> vendorList = assetVendorRepository.searchString(searchString, assetvendor.set);
//			
//			
//			
//			
//			Map content = new HashMap();
//			if (vendorList.size() > 0) {
//				content.put("vendorList", vendorList);
//				response.setSuccess(true);
//				response.setContent(content);
//			} else {
//				content.put("vendorList", vendorList);
//				response.setSuccess(false);
//				response.setContent(content);
//			}
//
//			return response;
		
	
	
	
	@Override
	public ApiResponse searchVendorName(String vendorName) 
	{
		ApiResponse response = new ApiResponse();
		AssetVendor assetVendor = new AssetVendor();
		assetVendor.setVendorName(vendorName);
		
		List<AssetVendor> vendorList = assetVendorRepository.searchVendorName(vendorName);
		Map content = new HashMap();

		content.put("vendorDetails", vendorList);
		if(content != null && vendorList.size() >0) {
			response.setSuccess(true);
			response.setMessage("Asset vendor name Retrieved successfully");
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve vendorname");
			response.setContent(content);
		}
		return response;
	}
}

	



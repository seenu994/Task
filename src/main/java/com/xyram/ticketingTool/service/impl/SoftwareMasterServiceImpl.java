//package com.xyram.ticketingTool.service.impl;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.itextpdf.text.log.LoggerFactory;
//import com.xyram.ticketingTool.Repository.SoftwareMasterRepository;
//import com.xyram.ticketingTool.apiresponses.ApiResponse;
//import com.xyram.ticketingTool.entity.AssetVendor;
//import com.xyram.ticketingTool.entity.SoftwareMaster;
//import com.xyram.ticketingTool.service.SoftwareMasterService;
//import com.xyram.ticketingTool.util.ResponseMessages;
//
//import ch.qos.logback.classic.Logger;
//
//@Service
//@Transactional
//
//public class SoftwareMasterServiceImpl implements SoftwareMasterService {
//	private final com.itextpdf.text.log.Logger logger = LoggerFactory.getLogger(SoftwareMasterServiceImpl.class);
//	 
//	@Autowired
//	SoftwareMasterRepository softwareRepository;
//	ApiResponse response;
//
//	@Override
//	public ApiResponse addSoftwareMaster(SoftwareMaster softwareId) {
//		 ApiResponse response = new ApiResponse(false);
//
//		// validateSoftwareMasterId(SoftwareMaster softwareId  ) {
//		//ApiResponse response = new ApiResponse(false);
//		if (softwareId.getSoftwareId() != null) {
//			response.setMessage("success");
//		    response.setMessage(ResponseMessages.ADDED_SOFTWARE_ID);
//			response.setSuccess(true);
//		} else {
//			response.setMessage(ResponseMessages.SOFTWARE_ID_INVALID);
//			response.setSuccess(false);
//
//		}
//
//		// response = validateSoftwareMasterId(softwareMaster);
//		if (response.isSuccess()) {
//			SoftwareMaster softwareMasterSave = softwareRepository.save(softwareId);
//			if (softwareMasterSave == null) {
//				Map content = new HashMap<>();
//
//				content.put("SoftwareDetails", softwareMasterSave);
//				response.setSuccess(true);
//				response.setContent(content);
//
//			}
//			return response;
//		}
//		return response;
//	}
//
//	@Override
//	public ApiResponse editSoftwareMaster(SoftwareMaster softwareMaster) {
//		
//		
//
////			ApiResponse response = new ApiResponse();
////			response = validateSoftwareMaster(softwareMaster);
////			AssetVendor softwareid = softwareMasterRepository.getById(softwareId);
////			if (response.isSuccess()) {
////				if (softwareid != null) 
////				{
////					softwareid.setSoftwareId(softwareMaster.getSoftwareId);
////					softwareid.set
////					
////					softwareid.setAssetVendorStatus(Vendor.getAssetVendorStatus());
////					assetVendorRepository.save(vendor1);
////					//AssetVendor assetVendorAdded = new AssetVendor();
////					response.setSuccess(true);
////					response.setMessage(ResponseMessages.VENDOR_DETAILS_EDIT);
//////					Map content = new HashMap();
//////					content.put("vendorId", assetVendorAdded.getAssetVendor());
//////					response.setContent(content);
////
////				} else {
////					response.setSuccess(false);
////					response.setMessage(ResponseMessages.VENDOR_DETAILS_INVALID);
////					//response.setContent(null);
////				}
////
////			}
////			return response;
////
////		}
//
//		
//		
//		return null;
//	}
//
//	
//
//
//
//	private ApiResponse validateSoftwareMasterId(SoftwareMaster softwareId) {
//		//ApiResponse response = new ApiResponse(false);
//		if (softwareId.getSoftwareId() != null) {
//			response.setMessage("success");
//			response.setSuccess(true);		}
//		else {
//			response.setSuccess(false);
//
//		}
//
//		return response;
//	}
//}
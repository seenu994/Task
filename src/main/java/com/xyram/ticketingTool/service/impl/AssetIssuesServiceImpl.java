//package com.xyram.ticketingTool.service.impl;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
////import com.xyram.ticketingTool.Repository.AssetIssuesStatusRepository;
//import com.xyram.ticketingTool.Repository.AssetRepository;
//import com.xyram.ticketingTool.Repository.AssetVendorRepository;
//import com.xyram.ticketingTool.apiresponses.ApiResponse;
//import com.xyram.ticketingTool.entity.Asset;
//import com.xyram.ticketingTool.entity.AssetBilling;
//import com.xyram.ticketingTool.entity.AssetIssues;
////import com.xyram.ticketingTool.entity.AssetIssuesStatus;
//import com.xyram.ticketingTool.entity.AssetVendor;
//import com.xyram.ticketingTool.enumType.AssetIssueStatus;
//import com.xyram.ticketingTool.service.AssetIssuesService;
//import com.xyram.ticketingTool.util.ResponseMessages;
//
//@Service
//@Transactional
//public class AssetIssuesServiceImpl implements AssetIssuesService
//{
//	//private static final ApiResponse assetIssues = null;
//	@Autowired
//	AssetIssuesRepository  assetIssuesRepository;
//	
//	@Autowired
//	AssetRepository  assetRepository;
//	
//	@Autowired
//	AssetIssuesService assetIssuesService;
//	
//	
//	
//
//	
//	@Override
//	public ApiResponse addAssetIssues(AssetIssues assetIssues) 
//	{
//		ApiResponse response = new ApiResponse(false);
//		response = validateAssetIssues(assetIssues);
//		response = validateAssetIssueStatus(assetIssues);
//		if(assetIssues != null)
//		{
//			assetIssuesRepository.save(assetIssues);
//			response.setSuccess(true);
//			response.setMessage(ResponseMessages.ASSET_ISSUES_ADDED_SUCCESSFULLY);
//		}
//	     return response;
//		
//	}
//	
//	private ApiResponse validateAssetIssues(AssetIssues assetIssues) 
//	{
//		ApiResponse response = new ApiResponse(false);
//		
//		/* //validate asset id
//		 if(assetIssues.getAsset() == null)
//		 {
//			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory !!");
//		 }
//		 else
//		 {
//			 //Asset asset = assetRepository.getaId(assetIssues.getaId());
//			 Asset asset = getAssetById(assetIssues.getAsset().getaId());
//			 if(asset.getaId() == null)
//			 {
//				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id not valid!!!");
//			 }
//			 else
//			 {
//				 assetIssues.setaId(asset);
//			 }
//		 }
//		 //validate issueid
//		 if(assetIssues.getAssetIssue() == null)
//		 {
//			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "issue id is mandatory !!");
//		 }
//		 /*if(assetIssues.getAssetIssue() == null)
//		 {
//			 AssetIssues assetIssue = assetIssuesRepository.getAssetIssues(assetIssues.getAssetIssue());
//			 if(assetIssues.getAssetIssue() == null)
//			 {
//				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "issueId not valid!!!");
//			 }
//		 }*/
//		//validate vendor id
//		 if(assetIssues.getAssetVendor() == null)
//		 {
//			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandatory !!");
//		 }
//		 else
//		 {
//			 AssetVendor assetVendor = AssetVendorRepository.getById(assetIssues.getAssetVendor());
//			 if(assetVendor.getVendorId() == null)
//			 {
//				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid!!!");
//			 }
//			 else
//			 {
//				 assetIssues.setAssetVendor(assetVendor);
//			 }
//		 }
//		 /*if(assetIssues.getAssetIssuesStatus() == null)
//		 {
//			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
//		 }*/
//		 
//		 /*
//		 
//		 
//		 
//		 
//		 
//		 response.setSuccess(true);
//		 return response;
//	}
//
//	@Override
//	public ApiResponse getAssetIssuesList(AssetIssues assetIssues) 
//	{
//		AssetIssues assetIssueList = assetIssuesRepository.getAssetIssuesList(assetIssues);
//		Map content = new HashMap();
//		Object assetIssuesList = null;
//		content.put("assetIssuesList", assetIssuesList);
//		ApiResponse response = new ApiResponse(true);
//		response.setSuccess(true);
//		response.setContent(content);
//		return response;
//	}
//
//public Asset getAssetById(String aId) {
//		
//		return assetRepository.getById(aId);
//		
//	}
//
//
//	
//	
//	public ApiResponse editAssetIssues(AssetIssues assetIssues) 
//	{
//		
//        ApiResponse response = new ApiResponse(false);
//		
//		AssetIssues assetIssuesObj = assetIssuesRepository.getById(assetIssues.getAssetIssue());
//		 Asset asset = getAssetById(assetIssues.getaId().getaId());
//		 
//		if(assetIssuesObj != null) 
//	    {	
//			assetIssues.setAssetIssue(assetIssues.getAssetIssue());
//			assetIssues.setaId(assetIssues.getaId());
//			assetIssues.setComplaintRaisedDate(assetIssues.getComplaintRaisedDate());
//			assetIssues.setDescription(assetIssues.getDescription());
//			assetIssues.setAssetVendor(assetIssues.getAssetVendor());
//			AssetIssues assetIssuesAdded = assetIssuesRepository.save(assetIssues);
//	
//			response.setSuccess(true);
//			response.setMessage(ResponseMessages.ASSET_ISSUES_EDIT_SUCCESSFULLY);
//			Map content = new HashMap();
//		
//			content.put("assetIssues", assetIssues.getAssetIssue());
//			
//			
//			response.setContent(content);
//			System.out.println("message ->"+response.getMessage());
//	    }     
//		else 
//		{
//		response.setSuccess(false);
//		response.setMessage(ResponseMessages.ASSET_ISSUES_ID_IS_INVALID);
//		response.setContent(null);
//	    }
//      return response;
//    }	
//
//
//
//    public ApiResponse validateAssetIssueStatus(AssetIssues assetIssues)
//    {
//    	ApiResponse response = new ApiResponse(false);
//    	
//    	if(assetIssues.getAssetIssueStatus == null)
//    	{
//    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
//    	}
//    	
//    	return response;
//    	
//    }
//  }
//	/*@Override
//	public ApiResponse getIssues(Pageable pageable) 
//	{
//		List<Map> developerInfraList = (List<Map>) assetIssuesRepository.getAssetIssues(pageable);
//		Map content = new HashMap();
//
//		content.put("assetIssuesList", assetIssues);
//		ApiResponse response = new ApiResponse(true);
//		response.setSuccess(true);
//		response.setContent(content);
//		return assetIssues;
//
//	}
//
//	@Override
//	public ApiResponse searchAssetIssues(Pageable pageable,String issueId) 
//	{
//		ApiResponse response = new ApiResponse();
//		AssetIssues assetIssues2 = new AssetIssues();
//		assetIssues2.setIssueId(issueId);
//		List<Map> assetIssuesList = assetIssuesRepository.searchAssetIssues(issueId);
//		Map content = new HashMap();
//		content.put("AssetIssuesList", assetIssuesList);
//		response.setSuccess(true);
//		response.setContent(content);
//		return response;
//	}
//
//	public ApiResponse changeAssetIssuesStatus(String Status, String issueId) 
//	{
//       ApiResponse response = new ApiResponse(false);
//		
//       AssetIssues assetIssuesObj = assetIssuesRepository.changeAssetIssuesStatus(assetIssues.getissueId());
//		
//		if(assetIssuesObj != null) {		
//			try {
//				
//				assetIssuesObj.setStatus(Status);
//				
//				assetIssuesRepository.save(assetIssuesObj);
//
//				response.setSuccess(true);
//				response.setMessage(ResponseMessages.ASSET_ISSUES_STATUS_CHANGED);
//				
//			}catch(Exception e) {
//				response.setSuccess(false);
//				response.setMessage(ResponseMessages.ASSET_ISSUES_STATUS__NOT_CHANGED+" "+e.getMessage());
//			}	
//		}else {
//			response.setSuccess(false);
//			response.setMessage(ResponseMessages.ASSET_ISSUES_STATUS__NOT_CHANGED);
//		}
//		
//		return response;
//	}
//
//	@Override
//	public ApiResponse getAssetIssues(Pageable pageable) 
//	{
//       return (ApiResponse) assetIssuesRepository.getAssetIssues(pageable);
//	}
//
//	@Override
//	public ApiResponse downloadAllAssetIssues(Map<String, Object> filter) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//
//	
//
//
//
//}*/
//

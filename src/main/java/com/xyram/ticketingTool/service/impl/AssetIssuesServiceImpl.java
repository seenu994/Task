package com.xyram.ticketingTool.service.impl;


import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
//import com.xyram.ticketingTool.Repository.AssetIssuesStatusRepository;
import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.AssetIssues;

//import com.xyram.ticketingTool.entity.AssetIssuesStatus;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.enumType.AssetIssueStatus;
import com.xyram.ticketingTool.service.AssetIssuesService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssetIssuesServiceImpl implements AssetIssuesService
{
	//private static final ApiResponse assetIssues = null;
	@Autowired
	AssetIssuesRepository  assetIssuesRepository;
	
	@Autowired
	AssetRepository  assetRepository;
	
	@Autowired
	AssetIssuesService assetIssuesService;
	
	@Autowired
	AssetVendorRepository assetVendorRepository;
	
	

	
	@Override
	public ApiResponse addAssetIssues(AssetIssues assetIssues) 
	{
		ApiResponse response = new ApiResponse(false);
		response = validateAssetIssues(assetIssues);
		//response = validateAssetIssueStatus(assetIssues);
		if(response.isSuccess()) {
		if(assetIssues != null)
		{
			assetIssuesRepository.save(assetIssues);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_ISSUES_ADDED_SUCCESSFULLY);
		}
		}
	     return response;
		}
	
	private ApiResponse validateAssetIssues(AssetIssues assetIssues) 
	{
		ApiResponse response = new ApiResponse(false);
		
		 //validate asset id
		 if(assetIssues.getAssetId() == null)
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory !!");
		 }
		 else
		 {
			 //Asset asset = assetRepository.getaId(assetIssues.getaId());
			 Asset asset = getAssetById(assetIssues.getAssetId());
			 if(asset == null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id not valid!!!");
			 }
		 }

		 if(assetIssues.getVendorId() == null)
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandatory !!");
		 }
		 else
		 {
			 AssetVendor vendor = assetVendorRepository.getVendorById(assetIssues.getVendorId());
			 if(vendor == null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid!!!");
			 }
			 
		 }	
		 if(assetIssues.getComplaintRaisedDate() == null)
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "complaint raised date is mandatory!!!");
		 }
		 
		 if(assetIssues.getAssetIssueStatus() == null)
	    	{
	    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
	    	}
		 response.setSuccess(true);
		 return response;
	}

	@Override
	public ApiResponse getAssetIssuesList(AssetIssues assetIssues) 
	{
		AssetIssues assetIssueList = assetIssuesRepository.getAssetIssuesList(assetIssues);
		Map content = new HashMap();
		Object assetIssuesList = null;
		content.put("assetIssuesList", assetIssuesList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

   public Asset getAssetById(String aId) {
		
		return assetRepository.getById(aId);
		
	}


	
	
	public ApiResponse editAssetIssues(AssetIssues assetIssues) 
	{
		
        ApiResponse response = new ApiResponse(false);
		
		AssetIssues assetIssuesObj = assetIssuesRepository.getAssetIssueById(assetIssues.getAssetIssueId());
		 //Asset asset = getAssetById(assetIssues.getAssetId());
		 
		if(assetIssuesObj != null) 
	    {	
			if(assetIssues.getAssetId() != null)
			{
				 checkAssetId(assetIssues.getAssetId());
				 assetIssues.setAssetId(assetIssues.getAssetId());
			}
			if(assetIssues.getVendorId() != null)
			{
				checkVendorId(assetIssues.getVendorId());
				assetIssues.setVendorId(assetIssues.getVendorId());
			}
			if(assetIssues.getComplaintRaisedDate()!= null)
			{
				assetIssues.setComplaintRaisedDate(new Date());
			}
			if(assetIssues.getAssetIssueStatus() != null)
			{
				checkAssetIssueStatus(assetIssues.getAssetIssueStatus());
				assetIssues.setAssetIssueStatus(assetIssues.getAssetIssueStatus());
			}
			/*assetIssues.setassetIssues(assetIssues.getAssetIssueId());
			//assetIssues.setAssetId(assetId);
			assetIssues.setComplaintRaisedDate(assetIssues.getComplaintRaisedDate());
			assetIssues.setDescription(assetIssues.getDescription());
			assetIssues.setVendorId(assetIssues.getVendorId());
			AssetIssues assetIssuesAdded = assetIssuesRepository.save(assetIssues);
	
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_ISSUES_EDIT_SUCCESSFULLY);
			Map content = new HashMap();
		
			content.put("assetIssues", assetIssues.getAssetIssues());
			
			
			response.setContent(content);
			System.out.println("message ->"+response.getMessage());*/
			AssetIssues assetIssuesAdded = assetIssuesRepository.save(assetIssues);
			
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_ISSUES_EDIT_SUCCESSFULLY);
	    }     
		else 
		{
		response.setSuccess(false);
		response.setMessage(ResponseMessages.ASSET_ISSUES_ID_IS_INVALID);
		
	    }
      return response;
    }


	private boolean checkAssetIssueStatus(AssetIssueStatus assetIssueStatus) 
	{
		//AssetIssues issues = assetIssuesRepository.getAssetIssueStatus();
		if(assetIssueStatus == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
		}
		
		return true;
	}

	private boolean checkVendorId(String vendorId) 
	{
		AssetVendor assetvendor = assetVendorRepository.getVendorById(vendorId);
		if(assetvendor == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid");
		}
		return true;
	}

	private boolean checkAssetId(String assetId) 
	{
		Asset asset = assetRepository.getByassetId(assetId);
		if(asset == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
		}
		else
		{
		   return true;
		}
    }

}

//    public ApiResponse validateAssetIssueStatus(AssetIssues assetIssues)
//    {
//    	ApiResponse response = new ApiResponse(false);
//    	
//    	if(assetIssues.getAssetIssueStatus == null || assetIssues.getAssetIssueStatus().equals(""))
//    	{
//    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
//    	}
//    	
//        response.set
//    	return response;
//    	
//    }
//  }
	/*@Override
	public ApiResponse getIssues(Pageable pageable) 
	{
		List<Map> developerInfraList = (List<Map>) assetIssuesRepository.getAssetIssues(pageable);
		Map content = new HashMap();

		content.put("assetIssuesList", assetIssues);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return assetIssues;

	}

	@Override
	public ApiResponse searchAssetIssues(Pageable pageable,String issueId) 
	{
		ApiResponse response = new ApiResponse();
		AssetIssues assetIssues2 = new AssetIssues();
		assetIssues2.setIssueId(issueId);
		List<Map> assetIssuesList = assetIssuesRepository.searchAssetIssues(issueId);
		Map content = new HashMap();
		content.put("AssetIssuesList", assetIssuesList);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	public ApiResponse changeAssetIssuesStatus(String Status, String issueId) 
	{
       ApiResponse response = new ApiResponse(false);
		
       AssetIssues assetIssuesObj = assetIssuesRepository.changeAssetIssuesStatus(assetIssues.getissueId());
		
		if(assetIssuesObj != null) {		
			try {
				
				assetIssuesObj.setStatus(Status);
				
				assetIssuesRepository.save(assetIssuesObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_ISSUES_STATUS_CHANGED);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ASSET_ISSUES_STATUS__NOT_CHANGED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ASSET_ISSUES_STATUS__NOT_CHANGED);
		}
		
		return response;
	}

	@Override
	public ApiResponse getAssetIssues(Pageable pageable) 
	{
       return (ApiResponse) assetIssuesRepository.getAssetIssues(pageable);
	}

	@Override
	public ApiResponse downloadAllAssetIssues(Map<String, Object> filter) {
		// TODO Auto-generated method stub
		return null;
	}

	

	



}*/


package com.xyram.ticketingTool.service.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
//import com.xyram.ticketingTool.Repository.AssetIssuesStatusRepository;
import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.AssetIssues;
//import com.xyram.ticketingTool.entity.AssetIssuesStatus;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.enumType.AssetIssueStatus;
import com.xyram.ticketingTool.enumType.AssetStatus;
import com.xyram.ticketingTool.enumType.UserStatus;
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
		AssetIssues assetIssue = new AssetIssues();
		response = validateAssetIssues(assetIssues);
		//response = validateAssetIssueStatus(assetIssues);
		if(response.isSuccess()) 
		{
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
		 if(assetIssues.getAssetId() == null || assetIssues.getAssetId().equals(""))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory !!");
		 }
		 else
		 {
			 //Asset asset = assetRepository.getaId(assetIssues.getaId());
			 Asset asset = getAssetById(assetIssues.getAssetId());
			 if(asset == null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid asset reference");
			 }
		 }

		 if(assetIssues.getVendorId() == null ||assetIssues.getVendorId().equals(""))
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
			 assetIssues.setComplaintRaisedDate(new Date());
		    //Date currentUtilDate = new Date();
			// Date currentDate = Calendar.getInstance().getTime();
		 }
		 
		 if(assetIssues.getAssetIssueStatus() == null)
	     {
	    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandatory");
	     }
		 if(assetIssues.getDescription() == null || assetIssues.getDescription().equals(""))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description is mandatory");
		 }
		 assetIssues.setSolution(false);
		 response.setSuccess(true);
		 return response;
	}

	

   public Asset getAssetById(String aId) {
		
		return assetRepository.getAssetById(aId);
		
	}


	
	
	public ApiResponse editAssetIssues(AssetIssues assetIssues,String assetIssueId) 
	{
		
        ApiResponse response = new ApiResponse(false);
		//AssetIssues assetIssue;
		AssetIssues assetIssuesObj = assetIssuesRepository.getAssetIssueById(assetIssues.getAssetIssueId());
		
		 
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
				checkAssetIssuesStatus(assetIssues.getAssetIssueStatus());
				assetIssues.setAssetIssueStatus(assetIssues.getAssetIssueStatus());
			}
			if(assetIssues.getDescription() != null)
			{
		       assetIssues.setDescription(assetIssues.getDescription());
			}
			
			assetIssuesRepository.save(assetIssuesObj);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_ISSUES_EDIT_SUCCESSFULLY);
			
		}

		else 
		{
			response.setSuccess(false);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid assetIssueId");
			
		}
       return response;
    }


	private boolean checkVendorId(String vendorId) 
	{
		AssetVendor assetVendor = assetVendorRepository.getVendorById(vendorId);
		if(assetVendor == null || assetVendor.equals(""))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid");
		}
		else
		{
		   return true;
		}
	}

	private boolean checkAssetId(String assetId) 
	{
		Asset asset = assetRepository.getByAssetId(assetId);
		if(asset == null || asset.equals(""))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
		}
		else
		{
		   return true;
		}
    }

	@Override
	public ApiResponse returnRepair(AssetIssues assetIssues,String assetIssueId) 
	{
         ApiResponse response = new ApiResponse(false);
		 AssetIssues assetIssuesObj = assetIssuesRepository.getAssetIssueById(assetIssues.getAssetIssueId());
		 
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
				checkAssetIssuesStatus(assetIssues.getAssetIssueStatus());
				assetIssues.setAssetIssueStatus(AssetIssueStatus.CLOSE);
			}
			if(assetIssues.getResolvedDate()!= null)
			{
				checkResolvedDate(assetIssues.getResolvedDate(), assetIssueId);
				assetIssues.setResolvedDate(assetIssues.getResolvedDate());
			}
			else
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"resolved date is mandatory");
			}
		    if(assetIssues.getSolution() != false)
		    {
		    	//checkSolution(assetIssues.getSolution());
		    	assetIssues.setSolution(assetIssues.getSolution());
		    }
		    else
		    {
		    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"solution should be mandatory");
		    }
	
		assetIssuesRepository.save(assetIssues);
		response.setMessage(ResponseMessages.RETURN_REPAIR);
		response.setSuccess(true);
	  }
		else
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid assetIssueId");
		}
		return response;
		
	}

	private boolean checkResolvedDate(Date resolvedDate,String assetIssueId) 
	{
		
		Date assetIssues = assetIssuesRepository.getCompaintRaisedDate(assetIssueId);
		Date d1 = assetIssues;
		Date d2 = resolvedDate;
		
	    if (d1.after(d2) || d1.equals(d2)) 
	    {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"resolved date should be greater than complaint raised date");
		}
		else 
		{
			return true;
		}
		
	}
		

	private boolean checkAssetIssuesStatus(AssetIssueStatus assetIssueStatus)
	{
		AssetIssues assetIssue = new AssetIssues();
		if(assetIssueStatus == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
		}
		else
		{
			return true;
			//assetIssue.setAssetIssueStatus(AssetIssueStatus.CLOSE);
		}
		
		
	}
	
	@Override
	public ApiResponse returnDamage(AssetIssues assetIssues,String assetIssueId) 
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
				checkAssetIssuesStatus(assetIssues.getAssetIssueStatus());
				assetIssues.setAssetIssueStatus(AssetIssueStatus.DAMAGE);
			}
			if(assetIssues.getResolvedDate()!= null)
			{
				checkResolvedDate(assetIssues.getResolvedDate(),assetIssueId);
				assetIssues.setResolvedDate(assetIssues.getResolvedDate());
			}
		    if(assetIssues.getSolution() != false)
		    {
		    	//checkSolution(assetIssues.getSolution());
		    	assetIssues.setSolution(assetIssues.getSolution());
		    }
		    else
		    {
		    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"solution should be mandatory");
		    }
	
		assetIssuesRepository.save(assetIssues);
		response.setMessage(ResponseMessages.RETURN_DAMAGE);
		response.setSuccess(true);
	  }
		else
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid assetIssueId");
		}
		return response;
		
	}

	private boolean checkStatus(AssetIssueStatus assetIssueStatus) 
	{
			AssetIssues assetIssue = new AssetIssues();
			if(assetIssueStatus == null)
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
			}
			else
			{
				assetIssue.setAssetIssueStatus(AssetIssueStatus.DAMAGE);
			}
			return true;
			
	}


       @Override
	public ApiResponse getAllAssetsIssues(Map<String, Object> filter, Pageable pageable) {
		
		ApiResponse response = new ApiResponse(false);
		
		String assetId = filter.containsKey("assetId") ? ((String) filter.get("assetId"))
				: null;
		String vendorId = filter.containsKey("vendorId") ? ((String) filter.get("vendorId"))
				: null;
		String assetIssueStatus = filter.containsKey("assetIssueStatus") ? ((String) filter.get("assetIssueStatus")).toUpperCase()
					: null;
		
		AssetIssueStatus assetIssuestatus = null;
		if(assetIssueStatus!=null) {
			try {
				assetIssuestatus = assetIssueStatus != null ? AssetIssueStatus.toEnum(assetIssueStatus) : null;
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						filter.get("status").toString() + " is not a valid status");
			}
		}
				
		Page<Map> assetIssues = assetIssuesRepository.getAllAssetsIssues(assetId, vendorId, assetIssuestatus,pageable);
		
		
		if(assetIssues.getSize() > 0) {
			Map content = new HashMap();
			content.put("assetissues", assetIssues);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retreived successfully.");
		}else {
			response.setSuccess(false);
			response.setMessage("List is empty.");	
		}
		return response;
	}

	@Override
	public ApiResponse getAssetIssues(String assetIssueId) 
	{
		ApiResponse response = new ApiResponse();
		AssetIssues assetIssues = assetIssuesRepository.getAssetIssueById(assetIssueId);
		Map content = new HashMap();
		content.put("assetIssues", assetIssues);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset Issues Retrieved Successfully");
			response.setContent(content);
			
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
		
	}
 
	@Override
	public ApiResponse searchAssetIssue(String assetIssueId) 
	{
		ApiResponse response = new ApiResponse();
		AssetIssues assetIssues = new AssetIssues();
		assetIssues.setAssetIssueId(assetIssueId);
		List<Map> assetIssuesList = assetIssuesRepository.searchAssetIssue(assetIssueId);
		Map content = new HashMap();

		content.put("AssetList", assetIssuesList);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset issue Retrieved successfully");
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}
}
	
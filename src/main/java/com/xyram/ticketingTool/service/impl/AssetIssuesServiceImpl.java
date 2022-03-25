package com.xyram.ticketingTool.service.impl;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.service.AssetIssuesService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Repository
public class AssetIssuesServiceImpl implements AssetIssuesService
{
	private static final ApiResponse assetIssues = null;
	@Autowired
	AssetIssuesRepository  assetIssuesRepository;

	@Override
	public ApiResponse addAssetIssues(AssetIssues assetIssues) 
	{
		ApiResponse response = new ApiResponse(false);
		
		if(assetIssues != null) 
		{
			 assetIssuesRepository.save(assetIssues);
	         response.setSuccess(true);
	         response.setMessage(ResponseMessages.ASSET_ISSUES_ADDED);
		}
		else 
		{
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ASSET_ISSUES_NOT_ADDED);
		}
		
		return response;
	}		
				
	@Override
	public ApiResponse editAssetIssues(AssetIssues assetIssues) 
	{
		
        ApiResponse response = new ApiResponse(false);
		
		AssetIssues assetIssuesObj = (AssetIssues) assetIssuesRepository.getById(assetIssues.getIssueId());
		
		
		if(assetIssuesObj != null) {		
			try 
			{
				assetIssuesRepository.save(assetIssuesObj);
                response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_ISSUES_EDITED);
				
			}
			catch(Exception e) 
			{
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ASSET_ISSUES_NOT_EDITED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ASSET_ISSUES_NOT_EDITED);
		}
		
		return response;
	}

	@Override
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
		AssetIssues.setissueId(issueId);
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

	

	



}

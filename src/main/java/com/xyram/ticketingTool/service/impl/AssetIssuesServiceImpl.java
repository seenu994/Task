package com.xyram.ticketingTool.service.impl;

/*import java.awt.print.Pageable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.AssetIssuesService;
import com.xyram.ticketingTool.util.ResponseMessages;

import ch.qos.logback.core.status.Status;

/*@Service
@Repository
public class AssetIssuesServiceImpl implements AssetIssuesService
{
	private static final ApiResponse assetIssues = null;
	@Autowired
	AssetIssuesRepository  assetIssuesRepository;

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public ApiResponse addAssetIssues(AssetIssues assetIssues) 
	{
		ApiResponse response = new ApiResponse(false);
		response = validateAssetIssues(assetIssues);
		System.out.println("aId::" + assetIssues.getaId());
		if(response.isSuccess()) 
		{
			if(!((String) assetIssuesRepository.getById(assetIssues.getIssueId())).isEmpty())
			{
		
			throw new ResponseStatusException(HttpStatus.CONFLICT,"asset issue is already assigned to existing vendor ");
			}
			Asset asset = AssetRepository.getById(assetIssues.getaId());
			if (asset != null) 
			{
				try 
				{
                   assetIssues.setassetIssues(asset.getaId());
				} 
				catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							asset.getaId() + " is not a valid status");
				}
			} else {
				throw new ResourceNotFoundException("invalid asset id ");
			}
			//asset.setAssetstatus(assetStatus.AVAILABLE);
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

	private ApiResponse validateAssetIssues(AssetIssues assetIssues) {
		
		return null;
	}		
				
	/*@Override
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

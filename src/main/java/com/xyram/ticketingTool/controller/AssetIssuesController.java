package com.xyram.ticketingTool.controller;


import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.enumType.AssetIssueStatus;
import com.xyram.ticketingTool.response.ReportExportResponse;
import com.xyram.ticketingTool.service.AssetIssuesService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class AssetIssuesController
{
	private final Logger logger = LoggerFactory.getLogger(AssetIssuesController.class);
	
	@Autowired
	AssetIssuesService assetIssuesService ; 
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addAssetIssues",
			AuthConstants.INFRA_USER_BASEPATH + "/addAssetIssues",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/addAssetIssues"})
	public ApiResponse addAssetIssues(@RequestBody AssetIssues assetIssues)
	{
		logger.info("received request to add assetIssues");
		return assetIssuesService.addAssetIssues(assetIssues);
		
	}
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editAssetIssues/{assetIssueId}",
			AuthConstants.INFRA_USER_BASEPATH + "/editAssetIssues/{assetIssueId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editAssetIssues/{assetIssueId}"})
	public ApiResponse editAssetIssues(@RequestBody AssetIssues assetIssues, @PathVariable String assetIssueId)
	{
		logger.info("received request to edit assetIssues");
		return assetIssuesService.editAssetIssues(assetIssues, assetIssueId);
	}
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/returnRepair/{assetIssueId}",
			AuthConstants.INFRA_USER_BASEPATH + "/returnRepair/{assetIssueId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/returnRepair/{assetIssueId}"})
	public ApiResponse returnRepair(@RequestBody AssetIssues assetIssues, @PathVariable String assetIssueId)
	{
		logger.info("received request to return an asset ");
		return assetIssuesService.returnRepair(assetIssues, assetIssueId);
	}
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/returnDamage/{assetIssueId}", 
			AuthConstants.INFRA_USER_BASEPATH  + "/returnDamage/{assetIssueId}",
			AuthConstants.INFRA_ADMIN_BASEPATH +"/returnDamage/{assetIssueId}"})
	public ApiResponse returnDamage(@RequestBody AssetIssues assetIssues,@PathVariable String assetIssueId)
	{
		logger.info("received request to edit assetIssues");
		return assetIssuesService.returnDamage(assetIssues,assetIssueId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllAssetsIssues",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllAssetsIssues",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllAssetsIssues"})
	ApiResponse getAllAssetsIssues(@RequestParam Map<String, Object>filter, Pageable pageable) {
		System.out.println(filter);
		logger.info("Received request to Get all asset issues");
		return assetIssuesService.getAllAssetsIssues(filter,pageable);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAssetIssuesById/{assetIssueId}",
			AuthConstants.INFRA_USER_BASEPATH + "/getAssetIssuesById/{assetIssueId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAssetIssuesById/{assetIssueId}"})
	public ApiResponse getAssetIssuesById(@PathVariable String assetIssueId)
	{
		logger.info("received request to edit assetIssues");
		return assetIssuesService.getAssetIssuesById(assetIssueId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/downloadAllAssetIssues",
	        AuthConstants.INFRA_USER_BASEPATH + "/downloadAllAssetIssues",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/downloadAllAssetIssues"})

	public 	ApiResponse downloadAllAssetIssues(@RequestBody Map<String, Object> filter) 
	{
		logger.info("Download all asset issue");
		return assetIssuesService.downloadAllAssetIssues(filter);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAssetIssueByAssetId/{assetId}",
			AuthConstants.INFRA_USER_BASEPATH + "/getAssetIssueByAssetId/{assetId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAssetIssueByAssetId/{assetId}"})
	public ApiResponse getAssetById1(@PathVariable String assetId)
	{
		logger.info("received request to edit assetIssues");
		return assetIssuesService.getAssetById1(assetId);
	}
}

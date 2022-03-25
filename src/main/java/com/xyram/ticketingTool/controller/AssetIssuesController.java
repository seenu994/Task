package com.xyram.ticketingTool.controller;

import java.awt.print.Pageable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.response.ReportExportResponse;
import com.xyram.ticketingTool.service.AssetIssuesService;
import com.xyram.ticketingTool.util.AuthConstants;

/*@RestController
@CrossOrigin
public class AssetIssuesController
{
	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	AssetIssuesService assetIssuesService ; 
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createAssetIssues" })
	public ApiResponse addAssetIssues(@RequestBody AssetIssues assetIssues)
	{
		logger.info("received request to add assetIssues");
		return assetIssuesService.addAssetIssues(assetIssues);
	}
	/*@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editAssetIssues" })
	public ApiResponse editAssetIssues(@RequestBody AssetIssues assetIssues)
	{
		logger.info("received request to edit assetIssues");
		return assetIssuesService.editAssetIssues(assetIssues);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAssetIssues"})
	public ApiResponse getIssues(Pageable pageable) {
		logger.info("Received request to get assetIssues");
		return assetIssuesService.getAssetIssues(pageable);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchAssetIssues"})
	public ApiResponse searchAssetIssues(Pageable pageable, String issueId) {
		logger.info("Received request to search job Vendor");
		return assetIssuesService.searchAssetIssues(pageable,issueId);
	}
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/changeAssetIssuesStatus"})
	public ApiResponse changeAssetIssuesstatus(@PathVariable String Status,String issueId) 
	{
		logger.info("Received request to change status of assetIssue");
		return assetIssuesService.changeAssetIssuesStatus(Status,issueId);
	}
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllAssetIssues"})
	public ApiResponse getAssetIssues(Pageable pageable) {
		logger.info("Received request to get assetIssues");
		return assetIssuesService.getAssetIssues(pageable);
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/downloadAllAssetIssues",})
	public 	ApiResponse downloadAllAssetIssues(@RequestBody Map<String, Object> filter) 
	{
		logger.info("Download all My Time sheets");
		return assetIssuesService.downloadAllAssetIssues(filter);
	}

}*/

package com.xyram.ticketingTool.service;



import java.util.Map;


import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.enumType.AssetIssueStatus;

@Service
@Transactional
public interface AssetIssuesService 
{

	
    ApiResponse addAssetIssues(AssetIssues assetIssues);
    
    ApiResponse editAssetIssues(AssetIssues assetIssues, String assetIssueId);
    
    
    //ApiResponse searchAssetIssues(Pageable pageable,String issueId);
    
    
    

	//ApiResponse changeAssetIssuesStatus(String Status, String issueId);
	
	//ApiResponse downloadAllAssetIssues(Map<String, Object> filter);*/

	
   //ApiResponse getAssetIssues(Pageable pageable);

    //ApiResponse getAssetIssues(AssetIssues assetIssues);


	ApiResponse returnRepair(AssetIssues assetIssues, String assetIssueId);

	ApiResponse returnDamage(AssetIssues assetIssues,String assetIssueId);

	ApiResponse getAllAssetsIssues(Map<String, Object> filter, Pageable pageable);


	ApiResponse searchAssetIssue(String assetIssueId);

	ApiResponse getAssetIssuesById(String assetIssueId);

	ApiResponse downloadAllAssetIssues(Map<String, Object> filter);
	
}
	

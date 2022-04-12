package com.xyram.ticketingTool.service;



import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;

@Service
@Transactional
public interface AssetIssuesService 
{

	
    ApiResponse addAssetIssues(AssetIssues assetIssues);
    
    ApiResponse editAssetIssues(AssetIssues assetIssues);
    
    
    //ApiResponse searchAssetIssues(Pageable pageable,String issueId);
    
    
    

	//ApiResponse changeAssetIssuesStatus(String Status, String issueId);
	
	//ApiResponse downloadAllAssetIssues(Map<String, Object> filter);*/

	
   //ApiResponse getAssetIssues(Pageable pageable);

    //ApiResponse getAssetIssues(AssetIssues assetIssues);

	ApiResponse getAssetIssuesList(AssetIssues assetIssues);

	



//ApiResponse getAssetIssues(Pageable pageable);
	
}
	

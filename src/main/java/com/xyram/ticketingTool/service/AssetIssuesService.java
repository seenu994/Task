package com.xyram.ticketingTool.service;



import java.util.Map;


import javax.transaction.Transactional;

import org.hibernate.mapping.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.enumType.AssetIssueStatus;

@Service
@Transactional
public interface AssetIssuesService 
{

	
    ApiResponse addAssetIssues(AssetIssues assetIssues) throws Exception;
    
    ApiResponse editAssetIssues(AssetIssues assetIssues, String assetIssueId) throws Exception;
    
    ApiResponse returnRepair(AssetIssues assetIssues, String assetIssueId) throws Exception;

	ApiResponse returnDamage(AssetIssues assetIssues,String assetIssueId) throws Exception;

	ApiResponse getAllAssetsIssues(Map<String, Object> filter, Pageable pageable);

    ApiResponse getAssetIssuesById(String assetIssueId);

	ApiResponse downloadAllAssetIssues(Map<String, Object> filter) throws Exception;
	
	ApiResponse getAssetById1(String assetId);
	
	
}
	

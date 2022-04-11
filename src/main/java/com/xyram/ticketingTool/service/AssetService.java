package com.xyram.ticketingTool.service;



import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;
import org.apache.tomcat.util.http.fileupload.FileUploadException;


public interface AssetService {
    
	ApiResponse addasset(Asset asset);
	
	ApiResponse editAsset(Asset asset, String id);

//	ApiResponse getAllAsset(Pageable pageable);

//	ApiResponse searchAsset(String assetId);

	ApiResponse getAssetById(String assetId);

//	ApiResponse getAssetByVendorName(Pageable pageable, String vendorName);

	ApiResponse getAllAssets(Map<String, Object> filter, Pageable pageable);

//	ApiResponse getAssetEmployeeById(String assetId, Pageable pageable);

//	ApiResponse getAssetBillingById(String assetId, Pageable pageable);
//
//	ApiResponse getAssetSoftwareById(String assetId, Pageable pageable);
//
//	ApiResponse getAssetIssuesById(String assetId, Pageable pageable);

	Map downloadAssets(Map<String, Object> filter) throws ParseException, FileUploadException, IOException;
	
}

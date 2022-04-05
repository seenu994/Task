package com.xyram.ticketingTool.service;



import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;

@Service
public interface AssetService {
    
	ApiResponse addasset(Asset asset);
	
	ApiResponse editAsset(Asset asset, String id);
	
    ApiResponse getAllAssets(Pageable pageable);

//	ApiResponse searchAsset(String searchString);
	
}

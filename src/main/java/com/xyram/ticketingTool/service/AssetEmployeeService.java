package com.xyram.ticketingTool.service;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetEmployee;

@Service
public interface AssetEmployeeService {

	ApiResponse addAssetEmployee(AssetEmployee assetEmployee);

	ApiResponse editAssetEmployee(AssetEmployee assetEmployee, String assetId);

	

}

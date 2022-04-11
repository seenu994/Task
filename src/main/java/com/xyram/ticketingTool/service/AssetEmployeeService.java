package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetEmployee;

@Service
public interface AssetEmployeeService {

	ApiResponse addAssetEmployee(AssetEmployee assetEmployee);

	ApiResponse editAssetEmployee(AssetEmployee assetEmployee, String assetId);

	ApiResponse updateAssetEmployee(AssetEmployee assetEmployee, String assetId);

	ApiResponse getAssetEmployeeById(String assetId, Pageable pageable);
}

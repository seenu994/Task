package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetSoftware;

public interface AssetSoftwareService {

	ApiResponse addassetSoftware(AssetSoftware assetSoftware);

	ApiResponse getAssetSoftwareById(String assetId, Pageable pageable);

	ApiResponse updateAssetSoftware(AssetSoftware assetSoftware, String assetId, String softwareId);

}

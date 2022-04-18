package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.enumType.SoftwareEnum;

@Service
public interface SoftwareMasterService {
	ApiResponse addSoftwareMaster(SoftwareMaster softwareMaster);
	ApiResponse editSoftwareMaster(SoftwareMaster softwareMaster,String vendorId);
ApiResponse getAllsoftwareMaster(Map<String, Object> filter,Pageable peageble);
//ApiResponse getAllsoftwareMasterList(Map<String, Object> filter, Pageable pageable);
//ApiResponse getAssetIssues(String assetIssueId);


//ApiResponse updatesoftwareMasterStatusupdatesoftwareMasterStatus(String softwareId, SoftwareEnum softwareEnum);
//ApiResponse updatesoftwareMasterStatus(String softwareId, SoftwareEnum softwareMasterStatus);
//ApiResponse updatesoftwareMasterStatus(SoftwareMaster SoftwareMasterRequest);
//ApiResponse updatesoftwareMasterStatus(SoftwareMaster softwareId, SoftwareEnum softwareMasterStatus);
ApiResponse updatesoftwareMasterStatus(String softwareId, SoftwareEnum softwareEnum);
}

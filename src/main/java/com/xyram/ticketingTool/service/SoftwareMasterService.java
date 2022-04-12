package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.enumType.SoftwareEnum;

@Service
public interface SoftwareMasterService {
	ApiResponse addSoftwareMaster(SoftwareMaster softwareMaster);
	ApiResponse editSoftwareMaster(SoftwareMaster SoftwareMasterRequest);
ApiResponse getAllsoftwareMasterList(Pageable peageble);
//ApiResponse updatesoftwareMasterStatusupdatesoftwareMasterStatus(String softwareId, SoftwareEnum softwareEnum);
//ApiResponse updatesoftwareMasterStatus(String softwareId, SoftwareEnum softwareMasterStatus);
//ApiResponse updatesoftwareMasterStatus(SoftwareMaster SoftwareMasterRequest);
//ApiResponse updatesoftwareMasterStatus(SoftwareMaster softwareId, SoftwareEnum softwareMasterStatus);
ApiResponse updatesoftwareMasterStatus(String softwareId, SoftwareEnum softwareEnum);
}

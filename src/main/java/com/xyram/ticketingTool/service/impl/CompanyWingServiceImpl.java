package com.xyram.ticketingTool.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.CompanyWingsRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.service.CompanyWingService;
import com.xyram.ticketingTool.service.EmployeeService;

@Service
@Transactional
public class CompanyWingServiceImpl implements CompanyWingService  {

@Autowired
CompanyWingsRepository wingRepo;

@Override
public ApiResponse getAllWing() {
	List<Map> wingList = wingRepo.getAllWing();
	Map content = new HashMap();
	content.put("wingList", wingList);
	ApiResponse response = new ApiResponse(true);
	response.setSuccess(true);
	response.setContent(content);
	return response;
}



}

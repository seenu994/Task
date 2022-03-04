package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.TCProjectModules;
import com.xyram.ticketingTool.entity.TestCases;
import com.xyram.ticketingTool.entity.TestCasesProjects;

public interface TestCasesService {
	ApiResponse CreateProj_TestCase(TestCasesProjects projCreatRequest);
	
	ApiResponse getTestCaseProjects(Pageable pageable);
	
	ApiResponse CreateTCProjectModules(TCProjectModules TCprojectModuleReq);
	
	ApiResponse EditTCProjectModules(TCProjectModules TCprojectModuleReq);
	
	ApiResponse DeleteTCProjectModules(String TCProjModuleID);
	
	ApiResponse getTCProjectModules(Pageable pageable);
	
	ApiResponse CreateTestCase(TestCases ticketRequest);
	
	ApiResponse EditTestCase(TestCases ticketRequest);
	
	ApiResponse getTestCasePMHDetails(Pageable pageable, String testCasePMID);
	
	//ApiResponse EditTestCase(MultipartFile[] files, String ticketId, String ticketRequest);
	
	//ApiResponse getAllTestCase(TicketStatus status, Pageable pageable);

}

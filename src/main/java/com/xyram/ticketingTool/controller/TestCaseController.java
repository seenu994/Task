package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.TCProjectModules;
import com.xyram.ticketingTool.entity.TestCases;
import com.xyram.ticketingTool.entity.TestCasesProjects;
import com.xyram.ticketingTool.service.TestCasesService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
/*@CrossOrigin*/
class TestCaseController {
	private final Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	TestCasesService testcaseserv;
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH	 + "/CreatePrjTestCase", AuthConstants.DEVELOPER_BASEPATH + "/CreatePrjTestCase" })
	public ApiResponse CreatePrjTestCase(@RequestBody TestCasesProjects TestcaseRequest) {
		logger.info("Received request to Create Test case Projects");
		return testcaseserv.CreateProj_TestCase(TestcaseRequest);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getTestCaseProjects", AuthConstants.DEVELOPER_BASEPATH + "/getTestCaseProjects" })
	public ApiResponse getTestCaseProjects(Pageable pageable) {
		logger.info("inside TC Contoller :: getTestCaseProjects");
		return testcaseserv.getTestCaseProjects(pageable);
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH	 + "/CreateTCProjectModules", AuthConstants.DEVELOPER_BASEPATH + "/CreateTCProjectModules" })
	public ApiResponse CreateTCProjectModules(@RequestBody TCProjectModules TCProjModRequest) {
		logger.info("Received request to Create Test case");
		return testcaseserv.CreateTCProjectModules(TCProjModRequest);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH	 + "/EditTCProjectModules", AuthConstants.DEVELOPER_BASEPATH + "/EditTCProjectModules" })
	public ApiResponse EditTCProjectModules(@RequestBody TCProjectModules TCProjModRequest) {
		logger.info("Received request to Edit Test case");
		return testcaseserv.EditTCProjectModules(TCProjModRequest);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH	 + "/DeleteTCProjectModules/{TCProjModuleID}", AuthConstants.DEVELOPER_BASEPATH + "/DeleteTCProjectModules/{TCProjModuleID}" })
	public ApiResponse DeleteTCProjectModules(@PathVariable String TCProjModuleID) {
		logger.info("Received request to Edit Test case");
		return testcaseserv.DeleteTCProjectModules(TCProjModuleID);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getTCProjectModules", AuthConstants.DEVELOPER_BASEPATH + "/getTCProjectModules" })
	public ApiResponse getTCProjectModules(Pageable pageable) {
		logger.info("inside TC Contoller :: getTCProjectModules");
		return testcaseserv.getTCProjectModules(pageable);
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH	 + "/CreateTestCase", AuthConstants.DEVELOPER_BASEPATH + "/CreateTestCase" })
	public ApiResponse CreateTestCase(@RequestBody TestCases TestcaseRequest) {
		logger.info("Received request to Create Test case");
		return testcaseserv.CreateTestCase(TestcaseRequest);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH	 + "/EditTestCase", AuthConstants.DEVELOPER_BASEPATH + "/EditTestCase" })
	public ApiResponse EditTestCase(@RequestBody TestCases TestcaseRequest) {
		logger.info("Received request to Edit Test case");
		return testcaseserv.EditTestCase(TestcaseRequest);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getTestCasePMHDetails/{testCasePMID}", AuthConstants.DEVELOPER_BASEPATH + "/getTestCasePMHDetails/{testCasePMID}" })
	public ApiResponse getTestCasePMHDetails(Pageable pageable, @PathVariable String testCasePMID) {
		logger.info("Received request to get TestCase PMHDetails");
		return testcaseserv.getTestCasePMHDetails(pageable, testCasePMID);
	}

}

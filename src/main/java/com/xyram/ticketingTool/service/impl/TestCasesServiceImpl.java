package com.xyram.ticketingTool.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyram.ticketingTool.Repository.TCProjectModulesRepository;
import com.xyram.ticketingTool.Repository.TestCaseRepository;
import com.xyram.ticketingTool.Repository.TestCasesProjRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.TCProjectModules;
import com.xyram.ticketingTool.entity.TestCases;
import com.xyram.ticketingTool.entity.TestCasesProjects;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.TestCasesService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
public class TestCasesServiceImpl implements TestCasesService {
	
	@Autowired
	CurrentUser userDetail;
	
	@Autowired
	TestCaseRepository testcaserep;
	
	@Autowired
	TestCasesProjRepository ProjRepository;
	
	@Autowired
	TCProjectModulesRepository tcPrjModulesRepository;
		
	@Override
	public ApiResponse CreateProj_TestCase(TestCasesProjects projcreatrequest) {
		ApiResponse response = new ApiResponse(false);
		
		//System.out.println(projcreatrequest);
		//projcreatrequest.setProjectId(projcreatrequest.getProjectId());
		projcreatrequest.setCreatedBy(userDetail.getUserId());
		projcreatrequest.setCreatedAt(new Date());
		projcreatrequest.setUpdatedBy(userDetail.getUserId());
		projcreatrequest.setLastUpdatedAt(new Date());
		TestCasesProjects TP = ProjRepository.save(projcreatrequest);
		
		response.setSuccess(true);
		//response.setMessage(ResponseMessages.PRJ_TESTCASE_CREATED);
		response.setContent(null);
		
		return response;
	}
	
	@Override
	public ApiResponse getTestCaseProjects(Pageable pageable) {
		List<Map> TestCasesPrjList;
		//System.out.println(userDetail.getUserRole());

		TestCasesPrjList = ProjRepository.getTestCaseProjects(pageable);
		if (TestCasesPrjList != null) {

			Map content = new HashMap<>();
			content.put("projectList", TestCasesPrjList);
			ApiResponse response = new ApiResponse(true);
			response.setSuccess(true);
			response.setContent(content);
			return response;
		} else {
			ApiResponse response = new ApiResponse(true);
			response.setMessage("project list is empty");
			return response;
		}
	}
	
	@Override
	public ApiResponse CreateTCProjectModules(TCProjectModules TCprojectModuleReq) {
		ApiResponse response = new ApiResponse(false);
		
		TCprojectModuleReq.setCreatedBy(userDetail.getUserId());
		TCprojectModuleReq.setCreatedAt(new Date());
		TCprojectModuleReq.setUpdatedBy(userDetail.getUserId());
		TCprojectModuleReq.setLastUpdatedAt(new Date());
		//TCProjectModules tcprjmodreq = 
		tcPrjModulesRepository.save(TCprojectModuleReq);
		
		response.setSuccess(true);
		//response.setMessage(ResponseMessages.PRJ_TESTCASE_CREATED);
		response.setContent(null);
		
		return response;
		
	}
	
	@Override
	public ApiResponse EditTCProjectModules(TCProjectModules TCprojectModuleReq) {
		ApiResponse response = new ApiResponse(false);
		TCProjectModules TCPM = tcPrjModulesRepository.getById(TCprojectModuleReq.getTcpmId());
		
		if (TCPM != null) {
			TCPM.setTcpmId(TCprojectModuleReq.getTcpmId());
			TCPM.setProjectId(TCprojectModuleReq.getProjectId());
			TCPM.setModuleDesc(TCprojectModuleReq.getModuleDesc());
			TCPM.setUpdatedBy(userDetail.getUserId());
			TCPM.setLastUpdatedAt(new Date());
			tcPrjModulesRepository.save(TCPM);
			
			response.setSuccess(true);
			//response.setMessage(ResponseMessages.TEST_CASE_EDITED);
			response.setContent(null);
		} else {
			response.setSuccess(false);
			//response.setMessage(ResponseMessages.TEST_CASE_NOT_EXIST);
			response.setContent(null);
		}
		return response;
		
	}
	
	@Override
	public ApiResponse DeleteTCProjectModules(String TCProjModuleID) {
		ApiResponse response = new ApiResponse(false);
		TCProjectModules TCPM = tcPrjModulesRepository.getById(TCProjModuleID);
		
		System.out.println("TCPM - " + TCPM.getProjectId());
		if (TCPM != null) {
			tcPrjModulesRepository.delete(TCPM);
			
			response.setSuccess(true);
			//response.setMessage(ResponseMessages.TEST_CASE_EDITED);
			response.setContent(null);
		} else {
			response.setSuccess(false);
			//response.setMessage(ResponseMessages.TEST_CASE_NOT_EXIST);
			response.setContent(null);
		}
		
		return response;
		
	}
	
	@Override
	public ApiResponse getTCProjectModules(Pageable pageable) {
		List<Map> TCPrjModList;
		
		TCPrjModList = tcPrjModulesRepository.getTCProjectModules(pageable);
		if (TCPrjModList != null) {

			Map content = new HashMap<>();
			content.put("projectList", TCPrjModList);
			ApiResponse response = new ApiResponse(true);
			response.setSuccess(true);
			response.setContent(content);
			return response;
		} else {
			ApiResponse response = new ApiResponse(true);
			response.setMessage("project module list is empty");
			return response;
		}
	}
	
	@Override
	public ApiResponse CreateTestCase(TestCases TCRequest) {
		ApiResponse response = new ApiResponse(false);
	
		//System.out.println("TCRequest.getTCPrjModule().getTcpmId()" + TCRequest.gettCPrjModule().getTcpmId());
		if(TCRequest.gettCPrjModule() != null && TCRequest.gettCPrjModule().getTcpmId() != null) {
			TCProjectModules TCPM = tcPrjModulesRepository.getById(TCRequest.gettCPrjModule().getTcpmId());
			if (TCPM != null) {
				//System.out.println("Project Module Exist");
				TCRequest.settCPrjModule(TCPM);
			} else {
				//System.out.println("Project Module Not Exist");
				response.setSuccess(false);
				response.setMessage("Project Module doesn't exsists");
				return response;
			}
		}
		
		TCRequest.setCreatedBy(userDetail.getUserId());
		TCRequest.setCreatedAt(new Date());
		TCRequest.setUpdatedBy(userDetail.getUserId());
		TCRequest.setLastUpdatedAt(new Date());
		TestCases tcObj = testcaserep.save(TCRequest);
		
		response.setSuccess(true);
		//response.setMessage(ResponseMessages.TESTCASE_CREATED);
		response.setContent(null);
		
		return response;
	}
	
	@Override
	public ApiResponse EditTestCase(TestCases TCRequest) {
		ApiResponse response = new ApiResponse(false);
		TestCases tcObj = testcaserep.getById(TCRequest.getPtId());
		
		if (tcObj != null) {
			if(TCRequest.gettCPrjModule() != null && TCRequest.gettCPrjModule().getTcpmId() != null) {
				TCProjectModules TCPM = tcPrjModulesRepository.getById(TCRequest.gettCPrjModule().getTcpmId());
				if (TCPM != null) {
					tcObj.settCPrjModule(TCPM);
				} else {
					response.setSuccess(false);
					response.setMessage("Project Module doesn't exsists");
					return response;
				}
			}
			tcObj.setPtId(TCRequest.getPtId());
			tcObj.setProjectId(TCRequest.getProjectId());
			tcObj.setTestCaseName(TCRequest.getTestCaseName());
			tcObj.setTestObjective(TCRequest.getTestObjective());
			tcObj.setPrecondition(TCRequest.getPrecondition());
			tcObj.setLabel(TCRequest.getLabel());
			tcObj.setPriorityId(TCRequest.getPriorityId());
			tcObj.setStatus(TCRequest.getStatus());
			tcObj.setComponent(TCRequest.getComponent());
			tcObj.setOwner(TCRequest.getOwner());
			tcObj.setReviewer1(TCRequest.getReviewer1());
			tcObj.setReviewer2(TCRequest.getReviewer2());
			tcObj.setUpdatedBy(userDetail.getUserId());
			tcObj.setLastUpdatedAt(new Date());
			testcaserep.save(tcObj);
			
			response.setSuccess(true);
			//response.setMessage(ResponseMessages.TEST_CASE_EDITED);
			response.setContent(null);
		} else {
			response.setSuccess(false);
			//response.setMessage(ResponseMessages.TEST_CASE_NOT_EXIST);
			response.setContent(null);
			return response;
		}
		
		return response;
	}
	
	@Override
	public ApiResponse getTestCasePMHDetails(Pageable pageable, String testCasePMID) {
		List<Map> TCPModListByID;
		ApiResponse response = new ApiResponse(false);
		
		TCPModListByID = testcaserep.getTestCasePMHDetails(pageable, testCasePMID);
		//CProjectModules TCPM = tcPrjModulesRepository.getById(TCRequest.gettCPrjModule().getTcpmId());
		if (TCPModListByID != null) {

			Map content = new HashMap<>();
			content.put("projectList", TCPModListByID);
			//ApiResponse response = new ApiResponse(true);
			response.setSuccess(true);
			response.setContent(content);
			//return response;
		} else {
			//ApiResponse response = new ApiResponse(true);
			response.setSuccess(false);
			response.setMessage("project module list is empty");
			//return response;
		}
		return response;
	}

}

package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Module;
import com.xyram.ticketingTool.service.ModuleService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class ModuleController {

	private final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	@Autowired
	ModuleService moduleService;

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createModule" })
	public ApiResponse addModule(@RequestBody Module Module) {
		logger.info("Received request to add Module");
		return moduleService.createModule(Module);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllModules",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllModules",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllModules",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllModules",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllModules",
			AuthConstants.HR_BASEPATH + "/getAllModules",
			AuthConstants.JOB_VENDOR_BASEPATH + "/getAllModules",
			AuthConstants.ACCOUNTANT_BASEPATH + "/getAllModules"
			})
	public ApiResponse getAllModules(Pageable pageable) {
		logger.info("indide ModuleContoller :: getAllModules");
		return moduleService.getAllModules(pageable);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editModule/{moduleId}" })
	public ApiResponse editModule(@PathVariable Integer moduleId, @RequestBody Module ModuleRequest) {
		logger.info("indide ModuleContoller :: editModule");
		return moduleService.editModuleById(moduleId, ModuleRequest);
	}

}

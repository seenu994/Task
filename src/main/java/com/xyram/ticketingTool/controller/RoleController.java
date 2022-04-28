package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Designation;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.service.RoleService;
import com.xyram.ticketingTool.util.AuthConstants;

/**
 * 
 * @author sahana.neelappa
 *
 */
@CrossOrigin
@RestController

public class RoleController {
	private final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	RoleService roleService;

	/**
	 * getAllRole
	 * 
	 * URL:http://<server name>/<context>/api/catogory/getAllCatagory
	 * 
	 * @requestType get
	 * @return List<Category>
	 */

	
	
	/*@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addRole",AuthConstants.HR_ADMIN_BASEPATH + "/addRole",
			AuthConstants.INFRA_USER_BASEPATH + "/addRole", AuthConstants.INFRA_ADMIN_BASEPATH + "/addRole",
			AuthConstants.HR_BASEPATH + "/addRole",AuthConstants.DEVELOPER_BASEPATH + "/addRole"})
	  public ApiResponse addRole(@RequestBody Role role) {
		logger.info("Received request to add role");
		return roleService.addRole(role);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editRoleById/{Id}",AuthConstants.HR_ADMIN_BASEPATH + "/editRoleById/{Id}",
			AuthConstants.INFRA_USER_BASEPATH + "/editRoleById/{Id}",AuthConstants.HR_BASEPATH + "/editRoleById/{Id}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editRoleById/{Id}", AuthConstants.DEVELOPER_BASEPATH + "/editRoleById/{Id}"})
	public ApiResponse editRoleById(@RequestBody Role Request, @PathVariable String Id) {
		logger.info("Received request to edit role");

		return roleService.editRoleById(Request,Id);
	}*/
	
	

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllRole",AuthConstants.HR_BASEPATH + "/getAllRole",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllRole",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllRole" ,AuthConstants.DEVELOPER_BASEPATH + "/getAllRole",AuthConstants.HR_ADMIN_BASEPATH + "/getAllRole"})
	public ApiResponse getAllRole() {
		logger.info("indide Role Controller :: getAllRole");
		return roleService.getAllRole();
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllRole/{roleName}"})
	public Integer getAllRolePermission(@PathVariable String roleName) {
		logger.info("indide Role Controller :: getAllRole");
		return roleService.getAllRolePermission(roleName);
	}
}
	
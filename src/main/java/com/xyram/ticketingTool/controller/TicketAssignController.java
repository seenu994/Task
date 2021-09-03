package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.entity.TicketAssignee;
import com.xyram.ticketingTool.service.RoleService;
import com.xyram.ticketingTool.service.TicketAssignService;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.util.AuthConstants;

import ch.qos.logback.core.pattern.color.ANSIConstants;

/**
 * 
 * @author sahana.neelappa
 *
 */
@CrossOrigin
@RestController
/* @RequestMapping("/api/ticketAssign") */
public class TicketAssignController {
	private final Logger logger = LoggerFactory.getLogger(TicketAssignController.class);
	@Autowired
	TicketAssignService ticketAssignService;

	/**
	 * assign ticket
	 * 
	 * URL:http://<server name>/<context>/api/ticketAssign/assignticket
	 * 
	 * 
	 * 
	 */
	@PostMapping(value= {AuthConstants.ADMIN_BASEPATH + "/assignTicketToInfraTeam",AuthConstants.INFRA_USER_BASEPATH + "/assignTicketToInfraTeam"})
	public Map<String,String> assignTicketToInfraTeam(@RequestBody Map<String, Object> requestMap) {
		logger.info("Received request to assign tickets to infra team");
		return ticketAssignService.assignTicketToInfraTeam(requestMap);
	}
}
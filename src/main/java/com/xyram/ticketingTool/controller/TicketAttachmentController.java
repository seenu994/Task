package com.xyram.ticketingTool.controller;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAssignee;
import com.xyram.ticketingTool.entity.TicketAttachment;
import com.xyram.ticketingTool.service.RoleService;
import com.xyram.ticketingTool.service.TicketAssignService;
import com.xyram.ticketingTool.service.TicketAttachmentService;
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
//@RequestMapping("/api/ticketAttachment")
public class TicketAttachmentController {
	private final Logger logger = LoggerFactory.getLogger(TicketAttachmentController.class);
	@Autowired
	TicketAttachmentService ticketAttachmentService;

	/**	 * add ticket attachment
	 * 
	 * URL:http://<server name>/<context>/api/ticketAssign/assignticket
	 * 
	 * 
	 * 
	 */
	
	@PostMapping(value= {AuthConstants.ADMIN_BASEPATH +"/addImage/{ticketId}",AuthConstants.DEVELOPER_BASEPATH +"/addImage/{ticketId}",AuthConstants.HR_ADMIN_BASEPATH +"/addImage/{ticketId}",AuthConstants.INFRA_USER_BASEPATH +"/addImage/{ticketId}",AuthConstants.INFRA_ADMIN_BASEPATH +"/addImage/{ticketId}"})
	public Map storeImage(@RequestPart(name = "files", required = true) MultipartFile[] files,@PathVariable String ticketId) {
		logger.info("indide TicketAttachmentController :: addImage");
		return ticketAttachmentService.storeImage(files,ticketId);
	}
	@DeleteMapping(value= {AuthConstants.ADMIN_BASEPATH +"/deleteimage/{ticketId}",AuthConstants.HR_ADMIN_BASEPATH +"/deleteimage/{ticketId}",AuthConstants.INFRA_ADMIN_BASEPATH +"/deleteimage/{ticketId}",AuthConstants.DEVELOPER_BASEPATH +"/deleteimage/{ticketId}",AuthConstants.INFRA_USER_BASEPATH+"/deleteimage/{ticketId}"})
	public ApiResponse deleteImage(@PathVariable String ticketId) {
		logger.info("indide TicketAttachmentController :: deleteImage");
		return ticketAttachmentService.deleteImage(ticketId);
	}

}
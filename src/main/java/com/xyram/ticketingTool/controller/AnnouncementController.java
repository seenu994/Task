
package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.enumType.AnnouncementStatus;
import com.xyram.ticketingTool.service.AnnouncementService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class AnnouncementController {

	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	AnnouncementService announcementService;

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createAnnouncement",
			AuthConstants.HR_ADMIN_BASEPATH + "/createAnnouncement",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/createAnnouncement" })
	public ApiResponse createAnnouncement(@RequestBody Announcement announcement) {
		logger.info("Creating new Announcement");
		return announcementService.createAnnouncement(announcement);
	}

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editAnnouncement",
			AuthConstants.HR_ADMIN_BASEPATH + "/editAnnouncement",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editAnnouncement" })
	public ApiResponse editAnnouncement(@RequestBody Announcement announcement) {
		logger.info("Editing Announcement");
		return announcementService.editAnnouncement(announcement);
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/changeAnnouncementStatus/{announcementId}/status/{announcementStatus}",
			AuthConstants.HR_ADMIN_BASEPATH + "/changeAnnouncementStatus/{announcementId}/status/{announcementStatus}", 
			AuthConstants.INFRA_ADMIN_BASEPATH + "/changeAnnouncementStatus/{announcementId}/status/{announcementStatus}" })
	public ApiResponse changeAnnouncementStatus(@PathVariable String announcementId, @PathVariable AnnouncementStatus announcementStatus) {
		logger.info("Received request to change Announcement status to: " + announcementStatus + "for AnnouncementId: " + announcementId);
		return announcementService.changeAnnouncementStatus(announcementId, announcementStatus);
	}
	
	@DeleteMapping(value = { AuthConstants.ADMIN_BASEPATH + "/deleteAnnouncement/{announcementId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/deleteAnnouncement/{announcementId}", 
			AuthConstants.INFRA_ADMIN_BASEPATH + "/deleteAnnouncement/{announcementId}" })
	public ApiResponse deleteAnnouncement(@PathVariable String announcementId) {
		logger.info("Received deleting Artcle: ");
		return announcementService.deleteAnnouncement(announcementId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAnnouncementById/{announcementId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAnnouncementById/{announcementId}", 
			AuthConstants.INFRA_USER_BASEPATH + "/getAnnouncementById/{announcementId}",
			AuthConstants.HR_BASEPATH + "/getAnnouncementById/{announcementId}", 
			AuthConstants.DEVELOPER_BASEPATH + "/getAnnouncementById/{announcementId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAnnouncementById/{announcementId}" })
	public ApiResponse getAnnouncementById(@PathVariable String announcementId) {
		logger.info("Received get getAnnouncementById by Id ");
		return announcementService.getAnnouncementById(announcementId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllAnnouncements",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllAnnouncements", AuthConstants.INFRA_USER_BASEPATH + "/getAllAnnouncements",
			AuthConstants.HR_BASEPATH + "/getAllAnnouncements", AuthConstants.DEVELOPER_BASEPATH + "/getAllAnnouncements",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllAnnouncements" })
	public ApiResponse getAllAnnouncements(Pageable pageable) {
		logger.info("Get all Announcements ");
		return announcementService.getAllAnnouncements(pageable);
	} 
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllMyAnnouncements",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllMyAnnouncements",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllMyAnnouncements" })
	public ApiResponse getAllMyAnnouncements(Pageable pageable) {
		logger.info("Get all getAllMyAnnouncements ");
		return announcementService.getAllMyAnnouncements(pageable);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchAnnouncement/{searchString}",
			AuthConstants.HR_ADMIN_BASEPATH + "/searchAnnouncement/{searchString}", AuthConstants.INFRA_USER_BASEPATH + "/searchAnnouncement/{searchString}",
			AuthConstants.HR_BASEPATH + "/searchAnnouncement/{searchString}", AuthConstants.DEVELOPER_BASEPATH + "/searchAnnouncement/{searchString}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/searchAnnouncement/{searchString}" })
	public ApiResponse searchAnnouncement(Pageable pageable,@PathVariable String searchString) {
		logger.info("Get all Announcements ");
		return announcementService.searchAnnouncement(pageable,searchString);
	}

}

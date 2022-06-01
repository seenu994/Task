
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

	@PostMapping("/createAnnouncement")
	public ApiResponse createAnnouncement(@RequestBody Announcement announcement)  throws Exception{
		logger.info("Creating new Announcement");
		return announcementService.createAnnouncement(announcement);
	}

	@PostMapping("/editAnnouncement" )
	public ApiResponse editAnnouncement(@RequestBody Announcement announcement)  throws Exception{
		logger.info("Editing Announcement");
		return announcementService.editAnnouncement(announcement);
	}
	
	@PostMapping("/changeAnnouncementStatus/{announcementId}/status/{announcementStatus}")
	public ApiResponse changeAnnouncementStatus(@PathVariable String announcementId, @PathVariable AnnouncementStatus announcementStatus)  throws Exception{
		logger.info("Received request to change Announcement status to: " + announcementStatus + "for AnnouncementId: " + announcementId);
		return announcementService.changeAnnouncementStatus(announcementId, announcementStatus);
	}
	
	@DeleteMapping("/deleteAnnouncement/{announcementId}")
	public ApiResponse deleteAnnouncement(@PathVariable String announcementId)  throws Exception {
		logger.info("Received deleting Artcle: ");
		return announcementService.deleteAnnouncement(announcementId);
	}
	
	@GetMapping("/getAnnouncementById/{announcementId}" )
	public ApiResponse getAnnouncementById(@PathVariable String announcementId)  throws Exception{
		logger.info("Received get getAnnouncementById by Id ");
		return announcementService.getAnnouncementById(announcementId);
	}
	
	@GetMapping("/getAllAnnouncements")
	public ApiResponse getAllAnnouncements(Pageable pageable) throws Exception {
		logger.info("Get all Announcements ");
		return announcementService.getAllAnnouncements(pageable);
	} 
	
	@GetMapping("/getAllMyAnnouncements")
	public ApiResponse getAllMyAnnouncements(Pageable pageable)  throws Exception{
		logger.info("Get all getAllMyAnnouncements ");
		return announcementService.getAllMyAnnouncements(pageable);
	}
	
	@GetMapping("/searchAnnouncement/{searchString}")
	public ApiResponse searchAnnouncement(Pageable pageable,@PathVariable String searchString)  throws Exception{
		logger.info("Get all Announcements ");
		return announcementService.searchAnnouncement(pageable,searchString);
	}

}

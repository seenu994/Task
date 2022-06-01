
package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.enumType.AnnouncementStatus;


public interface AnnouncementService {
	
	ApiResponse createAnnouncement(Announcement announcement)  throws Exception;
	
	ApiResponse editAnnouncement(Announcement announcement)  throws Exception;
	
	ApiResponse deleteAnnouncement(String announcementId)  throws Exception;
	
	ApiResponse changeAnnouncementStatus(String announcementId, AnnouncementStatus status)  throws Exception;
	
	ApiResponse getAllAnnouncements(Pageable pageable)  throws Exception;
	
	ApiResponse getAnnouncementById(String announcementId)  throws Exception;
	
	ApiResponse searchAnnouncement(Pageable pageable, String searchString)  throws Exception;

	ApiResponse getAllMyAnnouncements(Pageable pageable)  throws Exception;


}


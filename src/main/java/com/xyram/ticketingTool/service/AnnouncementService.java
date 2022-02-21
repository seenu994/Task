
package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.enumType.AnnouncementStatus;


public interface AnnouncementService {
	
	ApiResponse createAnnouncement(Announcement announcement);
	
	ApiResponse editAnnouncement(Announcement announcement);
	
	ApiResponse deleteAnnouncement(String announcementId);
	
	ApiResponse changeAnnouncementStatus(String announcementId, AnnouncementStatus status);
	
	ApiResponse getAllAnnouncements(Pageable pageable);
	
	ApiResponse getAnnouncementById(String announcementId);
	
	ApiResponse searchAnnouncement(Pageable pageable, String searchString);

	ApiResponse getAllMyAnnouncements(Pageable pageable);


}


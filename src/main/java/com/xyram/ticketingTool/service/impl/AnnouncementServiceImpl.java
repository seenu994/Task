
package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.AnnouncementRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.enumType.AnnouncementStatus;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.AnnouncementService;
import com.xyram.ticketingTool.ticket.config.EmployeePermissionConfig;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService{
	
	private static final Logger logger = LoggerFactory.getLogger(EmpoloyeeServiceImpl.class);


	@Autowired
	CurrentUser currentUser;

	@Autowired
	AnnouncementServiceImpl announcementServiceImpl;

	@Autowired
	AnnouncementRepository announcementRepository;
	
	@Autowired
	EmployeePermissionConfig empPerConfig;

	@Override
	public ApiResponse createAnnouncement(Announcement announcement) throws Exception {
		ApiResponse response = new ApiResponse(false);
		if(!empPerConfig.isHavingpersmission("annAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
		if(announcement != null) {
			
//			try {
				announcement.setCreatedBy(currentUser.getUserId());
				announcement.setUserId(currentUser.getUserId());
				announcement.setUserName(currentUser.getFirstName());
				announcement.setUpdatedBy(currentUser.getUserId());
				announcement.setCreatedAt(new Date());
				announcement.setLastUpdatedAt(new Date());
				
				announcementRepository.save(announcement);
				
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ANNOUNCEMENT_ADDED);
				Map content = new HashMap();
				content.put("AnnouncementId", announcement.getAnnouncementId());
				response.setContent(content);
//			}catch(Exception e) {
//				response.setSuccess(false);
//				response.setMessage(ResponseMessages.ANNOUNCEMENT_NOT_ADDED+" "+e.getMessage());
//			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_NOT_ADDED);
		}
		
		return response;
	}

	@Override
	public ApiResponse editAnnouncement(Announcement announcement)  throws Exception{
		
		ApiResponse response = new ApiResponse(false);

		if(!empPerConfig.isHavingpersmission("annAdd") && !empPerConfig.isHavingpersmission("annEditAll")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
		
		Announcement announcementObj = announcementRepository.findAnnouncementById(announcement.getAnnouncementId());
		if(!currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN") && !currentUser.getUserId().equalsIgnoreCase(announcementObj.getUserId())) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.NOT_AUTHORIZED);
			return response;
		}
		
		if(announcementObj != null) {		
			try {
				announcementObj.setTitle(announcement.getTitle());
				announcementObj.setDescription(announcement.getDescription());
				announcementObj.setSearchLabels(announcement.getSearchLabels());
				announcementObj.setUpdatedBy(currentUser.getUserId());
				announcementObj.setLastUpdatedAt(new Date());
				
				announcementRepository.save(announcementObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ANNOUNCEMENT_EDITED);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ANNOUNCEMENT_NOT_EDITED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_NOT_EDITED);
		}
		
		return response;
	}

	@Override
	public ApiResponse deleteAnnouncement(String announcementId)  throws Exception{
		ApiResponse response = new ApiResponse(false);
		if(!empPerConfig.isHavingpersmission("annAdd") && !empPerConfig.isHavingpersmission("annEditAll")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
		Announcement announcementObj = announcementRepository.findAnnouncementById(announcementId);
		if(!currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN") && !currentUser.getUserId().equalsIgnoreCase(announcementObj.getUserId())) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.NOT_AUTHORIZED);
			return response;
		}
		if(announcementObj != null) {		
			try {
				
				announcementRepository.delete(announcementObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ANNOUNCEMENT_DELETED);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ANNOUNCEMENT_NOT_DELETED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_NOT_DELETED);
		}
		
		return response;
	}

	@Override
	public ApiResponse changeAnnouncementStatus(String announcementId, AnnouncementStatus status)  throws Exception{
		
		ApiResponse response = new ApiResponse(false);
		if(!empPerConfig.isHavingpersmission("annAdd") && !empPerConfig.isHavingpersmission("annEditAll")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
		Announcement announcementObj = announcementRepository.findAnnouncementById(announcementId);
		if(!currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN") && !currentUser.getUserId().equalsIgnoreCase(announcementObj.getUserId())) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.NOT_AUTHORIZED);
			return response;
		}
		if(announcementObj != null) {		
			try {
				
				announcementObj.setStatus(status);
				announcementObj.setUpdatedBy(currentUser.getUserId());
				announcementObj.setLastUpdatedAt(new Date());
				
				announcementRepository.save(announcementObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ANNOUNCEMENT_STATUS_CHANGED);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ANNOUNCEMENT_STATUS_CHANGED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_STATUS_NOT_CHANGED);
		}
		
		return response;
	}

	@Override
	public ApiResponse getAllAnnouncements(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		if(!empPerConfig.isHavingpersmission("annViewAll")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
		Page<Map> list = null;
		
		if(!currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN"))
			list = announcementRepository.getAllActiveAnnouncements(pageable);
		else
			list = announcementRepository.getAllAnnouncements(pageable);
		
		if(list.getSize() > 0) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_LIST_RETREIVED);
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
			content.put("Announcements", list);
			response.setContent(content);
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_LIST_NOT_RETREIVED);
		}
		
		return response;
	} 
	
	@Override
	public ApiResponse getAllMyAnnouncements(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		
		Page<Map> list = announcementRepository.getAllMyAnnouncements(pageable,currentUser.getUserId());
		
		if(list.getSize() > 0) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_LIST_RETREIVED);
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
			content.put("Announcements", list);
			response.setContent(content);
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_LIST_NOT_RETREIVED);
		}
		
		return response;
	}

	@Override
	public ApiResponse searchAnnouncement(Pageable pageable, String searchString) throws Exception {

		ApiResponse response = new ApiResponse(false);
		if(!empPerConfig.isHavingpersmission("annViewAll")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
		Page<Map> list = null;
		searchString = searchString.replaceAll("[-+.^:,]!@#$%&*()_~`/","");
		if(currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN"))
		{
			response.setMessage(ResponseMessages.ANNOUNCEMENT_LIST_RETREIVED+" For Super admin");
			list = announcementRepository.searchAllAnnouncements(pageable,searchString.toLowerCase());
		}
		else {
			response.setMessage(ResponseMessages.ANNOUNCEMENT_LIST_RETREIVED+" For employees");
			list = announcementRepository.searchAllActiveAnnouncements(pageable,searchString.toLowerCase());
		}
		
		if(list.getSize() > 0) {
			response.setSuccess(true);
			
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
			content.put("Announcements", list);
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_LIST_NOT_RETREIVED);
		}
		return response;
	}

	@Override
	public ApiResponse getAnnouncementById(String announcementId)  throws Exception{
		ApiResponse response = new ApiResponse(false);
		if(!empPerConfig.isHavingpersmission("annViewAll")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
		Announcement announcementObj = announcementRepository.findAnnouncementById(announcementId);
		if(announcementObj != null) {		
			try {

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ANNOUNCEMENT_DETAILS_RETREIVED);
				Map content = new HashMap();
				content.put("Detail", announcementObj);
				response.setContent(content);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ANNOUNCEMENT_DETAILS_RETREIVED+" "+e.getMessage());
				
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ANNOUNCEMENT_DETAILS_NOT_RETREIVED);
		}
		
		return response;
	}

}


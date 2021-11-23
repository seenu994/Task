package com.xyram.ticketingTool.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.StoryAttachmentsRespostiory;
import com.xyram.ticketingTool.entity.StoryAttachments;
import com.xyram.ticketingTool.fileupload.FileTransferService;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.StoryAttachmentsService;

@Service
public class StoryAttachementsServiceImpl  implements StoryAttachmentsService{
	
	
	private final Logger logger = LoggerFactory.getLogger(StoryAttachementsServiceImpl.class);

	@Value("${story-attachment-base-url}")
	private String storyAttachmentBaseUrl;

	@Autowired
	CurrentUser currentUser;



	@Autowired
	StoryAttachmentsRespostiory StoryAttachmentsRespostiory;

	@Autowired
	FileTransferService fileUploadService;

	@Override
	public List<Map> uploadStoryAttachemet(MultipartFile[] file, String storyId) {
		List<Map> StoryAttachmentList = new ArrayList<>();
		Map<String, Object> fileUploadedStatus = new HashMap<>();
		List<Object> fileUploadedList = new ArrayList<>();
		List<Object> fileUploadfailureList = new ArrayList<>();
        
		for (MultipartFile prescriptionFile : file) {
			 
			StoryAttachments storyAttachment = new StoryAttachments();
            
			String fileName = System.currentTimeMillis() + "_" + prescriptionFile.getOriginalFilename();
			if(false) {
				fileUploadfailureList.add(prescriptionFile.getOriginalFilename());
				continue;
			}
			storyAttachment.setFileType(prescriptionFile.getContentType());

			storyAttachment.setUploadedBy(currentUser.getScopeId());
			storyAttachment.setUploadedOn(new Date());
			storyAttachment.setStoryId(storyId);

			boolean response = false;
			try {
				response = fileUploadService.uploadFile(prescriptionFile, storyAttachmentBaseUrl, fileName);
				storyAttachment.setFileName(fileName);
				storyAttachment.setFileDownloadUrl(storyAttachmentBaseUrl + "/" + fileName);

			} catch (Exception e) {

				logger.info(e.toString());
			}
			
			if (response) {
				storyAttachment.setFileName(fileName);
				StoryAttachments newStoryAttachments = StoryAttachmentsRespostiory.save(storyAttachment);
				if (newStoryAttachments != null) {
					fileUploadedList.add(prescriptionFile.getOriginalFilename());
				} else {
					fileUploadfailureList.add(prescriptionFile.getOriginalFilename());
				}
			} else {
				fileUploadfailureList.add(prescriptionFile.getOriginalFilename());
			}
			
			
			
		}
		fileUploadedStatus.put("perscriptionsUploaded", fileUploadedList);
		fileUploadedStatus.put("perscriptionsUploadFailure", fileUploadfailureList);
		StoryAttachmentList.add(fileUploadedStatus);

		return StoryAttachmentList;
	}

	public String getpersciptionBucket(String patientId) {
		//String perscriptionbucketName = bucketName + "/rpm/prescription/" + patientId + "/";
		//return perscriptionbucketName;
		
		return null;
	}

	@Override
	public List<Map> getStoryAttachmentsListByStoryId(String storyId)

	{
		return StoryAttachmentsRespostiory.getStoryAttachmentListByStoryId(storyId);

	}

	
	
	
	@Override
	public StoryAttachments getStoryAttachmentsById(String storyAttachmentId) {
		logger.info("Received request for get prescription with id:" + storyAttachmentId);

		return StoryAttachmentsRespostiory.findById(storyAttachmentId).map(storyAttachment -> {
			logger.info("Prescription retrieved successfully: " + storyAttachment);

			return storyAttachment;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "storyAttachment  not found with id " + storyAttachmentId));
	}

	@Override
	public Map<String, Object> deleteStoryAttachmentById(String storyAttachmentId) {
		Map<String, Object> reponse = new HashMap<>();
		StoryAttachments storyAttachment = getStoryAttachmentsById(storyAttachmentId);
		if (storyAttachment != null) {
			if (storyAttachment.getUploadedBy().equals(currentUser.getScopeId())) {
				String filename = storyAttachment.getFileName();

				Integer status = StoryAttachmentsRespostiory.deleteStoryAttachment(storyAttachmentId);

				if (status > 0) {
					// ss3StorageService.deleteFile(filename, bucketName);
					fileUploadService.deleteFile(storyAttachment.getFileName(), storyAttachmentBaseUrl);
					reponse.put("message", "Prescription  succesfully deleted for id" + storyAttachmentId);

				} else {
					reponse.put("message", "unable to delete for prescription  id" + storyAttachmentId);

				}
			} else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
						"You Don't Have permission to perform this Operation");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "prescription not found with id " + storyAttachmentId);
		}

		return reponse;

	}
	
//	public boolean fileValidation(String contentType) {
//  	  List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg", "application/pdf");
//  	  if(contentTypes.contains(contentType)) {
//  	       return true;
//  	    } else {
//  	        return false;
//  	    }
//  	
//  }
	
	
	
	

}

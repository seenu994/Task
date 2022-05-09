package com.xyram.ticketingTool.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.NotesRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Notes;
import com.xyram.ticketingTool.entity.TicketAttachment;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.NotesService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class NotesServiceImpl implements NotesService {

	@Autowired
	NotesRepository notesRepository;

	@Autowired
	CurrentUser currentUser;

	@Override
	public ApiResponse createNotes(Notes noteRequest) throws ParseException {
		
		ApiResponse response = new ApiResponse(false);
		if(noteRequest == null) {
			response.setMessage("Notes Description is null");
			return response;
		}

		Notes notes = notesRepository.getNotes(new Date(), currentUser.getScopeId());
		if (notes != null) {

			notes.setUpdatedBy(currentUser.getScopeId());
			// notes.setNotesUploadedDate(new Date());
			notes.setLastUpdatedAt(new Date());
			notes.setNotes(noteRequest.getNotes());

			notesRepository.save(notes);

			response.setSuccess(true);
			response.setMessage(ResponseMessages.NOTES_UPDATED);
		} else {

			noteRequest.setNotesUploadedDate(new Date());
			noteRequest.setCreatedBy(currentUser.getScopeId());
			noteRequest.setCreatedAt(new Date());
			notesRepository.save(noteRequest);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.NOTES_CREATED);
		}
		return response;
	}

	@Override
	public ApiResponse getNotes(Date paramDate) {
		ApiResponse response = new ApiResponse(false);
		Notes notesObj = notesRepository.getNotes(paramDate, currentUser.getScopeId());
		Map content = new HashMap();
		content.put("notesObj", notesObj);
		if (content != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.NOTES_RETRIEVED);
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.NOTES_NOT_RETRIEVED);
		}

		return response;
	}

	@Override
	public Page<Map> getAllNotes(Map<String, Object> filter, Pageable pageable) {
		ApiResponse response = new ApiResponse(false);

		String searchString = filter.containsKey("searchString") ? ((String) filter.get("searchString")) : null;
		// String note = filter.containsKey("notes") ? ((String) filter.get("notes")) :
		// null;

		String fromDateStr = filter.containsKey("fromDate") ? ((String) filter.get("fromDate")).toLowerCase() : null;
		Date fromDate = null;
		if (fromDateStr != null) {
			try {
				fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String toDateStr = filter.containsKey("toDate") ? ((String) filter.get("toDate")).toLowerCase() : null;
		Date toDate = null;
		if (toDateStr != null) {
			try {
				toDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (toDate == null || fromDate == null) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.DATE_FEILD);
		}

		Page<Map> notesObj = notesRepository.getAllNotes(searchString, fromDateStr, toDateStr, pageable,
				currentUser.getScopeId());

		if (notesObj.getSize() > 0) {
			System.out.println(notesObj);
			Map content = new HashMap();
			content.put("notesObj", notesObj);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("retrieved successfully");
		} else {
			response.setSuccess(false);
			response.setMessage("is empty");
		}

		return notesObj;
	}

	@Override
	public ApiResponse deleteNotes(Date paramDate) {
		ApiResponse response = new ApiResponse(false);
		Notes notesObj = notesRepository.getNotes(paramDate, currentUser.getScopeId());
		if (notesObj != null) {
			notesRepository.deleteNotes(paramDate, currentUser.getScopeId());
			response.setSuccess(true);
			response.setMessage(ResponseMessages.DELETE_NOTES);
			response.setContent(null);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.NOTES_NOT_FOUND);
		}
		return response;
	}

}

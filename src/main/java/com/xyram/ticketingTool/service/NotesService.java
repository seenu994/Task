package com.xyram.ticketingTool.service;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Notes;

public interface NotesService {

	ApiResponse getNotes(Date paramDate);

	ApiResponse createNotes(Notes noteRequest);
	
	Page<Map> getAllNotes(Map<String, Object> filter, Pageable pageable );
	
	ApiResponse deleteNotes(Date paramDate);

}

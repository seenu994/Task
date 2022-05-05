package com.xyram.ticketingTool.controller;

import java.sql.Date;
import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Notes;
import com.xyram.ticketingTool.service.NotesService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class NotesController {

	private final Logger logger = LoggerFactory.getLogger(AssetController.class);

	@Autowired
	NotesService notesService;

	@PostMapping(value = { AuthConstants.INFRA_ADMIN_BASEPATH + "/createNotes",
			AuthConstants.HR_ADMIN_BASEPATH + "/createNotes", AuthConstants.DEVELOPER_BASEPATH + "/createNotes",
			AuthConstants.INFRA_USER_BASEPATH + "/createNotes", AuthConstants.HR_BASEPATH + "/createNotes" })
	public ApiResponse createNotes(@RequestBody Notes notes) throws ParseException {
		logger.info("Creating Notes");
		return notesService.createNotes(notes);
	}

	@GetMapping(value = { AuthConstants.INFRA_ADMIN_BASEPATH + "/getNotes",
			AuthConstants.HR_ADMIN_BASEPATH + "/getNotes", AuthConstants.DEVELOPER_BASEPATH + "/getNotes",
			AuthConstants.INFRA_USER_BASEPATH + "/getNotes", AuthConstants.HR_BASEPATH + "/getNotes" })
	public ApiResponse getNotes(@RequestParam Date paramDate) {
		logger.info("Received request for get notes");
		return notesService.getNotes(paramDate);
	}

	@GetMapping(value = { AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllNotes",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllNotes", AuthConstants.DEVELOPER_BASEPATH + "/getAllNotes",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllNotes", AuthConstants.HR_BASEPATH + "/getAllNotes" })
	Page<Map> getAllAssets(@RequestParam Map<String, Object> filter, Pageable pageable) {
		System.out.println(filter);
		logger.info("Received request to Get all Notes");
		return notesService.getAllNotes(filter, pageable);
	}

	@DeleteMapping(value = { AuthConstants.INFRA_ADMIN_BASEPATH + "/deleteNotes",
			AuthConstants.HR_ADMIN_BASEPATH + "/deleteNotes", AuthConstants.DEVELOPER_BASEPATH + "/deleteNotes",
			AuthConstants.INFRA_USER_BASEPATH + "/deleteNotes", AuthConstants.HR_BASEPATH + "/deleteNotes" })
	ApiResponse deleteNotes(@RequestParam Date paramDate) {
		logger.info("Received request for delete");
		return notesService.deleteNotes(paramDate);
	}

}

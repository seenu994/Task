package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.service.ClientService;



@RestController
@CrossOrigin
public class ClientControll {

	private final Logger logger = LoggerFactory.getLogger(ClientControll.class);

	@Autowired
	private ClientService clientService;

	@PostMapping( "/addClient")
	public Client addClient(@RequestBody Client clientRequest) {
		logger.info("Received request to add client: " + clientRequest.getCreatedBy());
		return clientService.addClient(clientRequest);
	}
}
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

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.enumType.ClientStatus;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.service.ClientService;
import com.xyram.ticketingTool.util.AuthConstants;



@RestController
@CrossOrigin
public class ClientControll {

	private final Logger logger = LoggerFactory.getLogger(ClientControll.class);

	@Autowired
	private ClientService clientService;

	@PostMapping("/addClient")
	public ApiResponse addClient(@RequestBody Client clientRequest) {
		
		return clientService.addClient(clientRequest);
	}
	
	@PutMapping("/editClient/{clientId}")
	public ApiResponse editClient(@RequestBody Client clientRequest,@PathVariable String clientId ) {
	
		return clientService.editClient(clientId,clientRequest);
	}
	
	
	@PutMapping("/{clientId}/status/{userstatus}")
	public ApiResponse updateClientStatus(@PathVariable String clientId, @PathVariable ClientStatus userstatus) {
	
		return clientService.updateClientStatus(clientId, userstatus);
	}

	@GetMapping(value= {AuthConstants.ADMIN_BASEPATH +"/getAllClient",AuthConstants.INFRA_ADMIN_BASEPATH +"/getAllClient"})
	public ApiResponse getAllClient(Pageable pageable) {
		logger.info("indide Client controller :: getAllClient");
		return clientService.getAllClient(pageable);
	}
}
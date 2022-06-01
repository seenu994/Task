package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.enumType.ClientStatus;

public interface ClientService {

ApiResponse addClient(Client clientRequest)  throws Exception;

ApiResponse updateClientStatus(String clientId, ClientStatus userstatus)  throws Exception;

ApiResponse editClient(String clientId, Client clientRequest) throws Exception;

ApiResponse getAllClient(Pageable pageable) throws Exception;

ApiResponse searchClient(String searchString)  throws Exception;
		
	
}

package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.enumType.ClientStatus;
import com.xyram.ticketingTool.enumType.UserStatus;

public interface ClientService {

 ApiResponse addClient(Client clientRequest);

ApiResponse updateClientStatus(String clientId, ClientStatus userstatus);

ApiResponse editClient(String clientId, Client clientRequest);
		
	
}

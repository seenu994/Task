package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.ClientRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.enumType.ClientStatus;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.service.ClientService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	ClientRepository clientRepository;

	@Override
	public ApiResponse addClient(Client clientRequest) {
		ApiResponse response = new ApiResponse(false);
		if (clientRequest.getClientName() != null) {
			Client clients = clientRepository.save(clientRequest);
			response.setMessage(ResponseMessages.CLIENT_CREATED);
			response.setSuccess(true);
			Map content = new HashMap();
			content.put("clientId", clientRequest.getId());
			response.setContent(content);
		}

		return response;
	}

	@Override
	public ApiResponse updateClientStatus(String clientId, ClientStatus status) {
		ApiResponse response = validateStatus(status);
		if (response.isSuccess()) {
			Client client = clientRepository.getById(clientId);
			if (client != null) {
				client.setStatus(status);
				clientRepository.save(client);
				// Employee employeere=new Employee();

				response.setSuccess(true);
				response.setMessage(ResponseMessages.STATUS_UPDATE);
				response.setContent(null);
			}

			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.CLIENT_INVALID);
				response.setContent(null);
			}

		}
		return response;
		
		
		
	}

	@Override
	public ApiResponse editClient(String clientId, Client clientRequest) {
		ApiResponse response = new ApiResponse(false);
		if (clientRequest.getClientName() != null) {
			clientRequest.setClientName(clientRequest.getClientName());
			Client clients = clientRepository.save(clientRequest);
			response.setMessage(ResponseMessages.CLIENT_UPDATED);
			response.setSuccess(true);
			response.setContent(null);
		}

		return response;
	}
	
	private ApiResponse validateStatus(ClientStatus userstatus) {
		ApiResponse response = new ApiResponse(false);
		if (userstatus != ClientStatus.ACTIVE || userstatus != ClientStatus.INACTIVE) {
			response.setMessage(ResponseMessages.USERSTATUS_INVALID);
			response.setSuccess(false);
		}

		else {
			response.setMessage(ResponseMessages.STATUS_UPDATE);
			response.setSuccess(true);
		}

		return response;
	}

}

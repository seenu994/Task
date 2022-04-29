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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.ClientRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.enumType.ClientStatus;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.ClientService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	CurrentUser currentUser;

	@Override
	public ApiResponse addClient(Client clientRequest) {
		ApiResponse response = new ApiResponse(false);
		//Client client = new Client();
		response = validateClient(clientRequest);
		if(response.isSuccess())
		{
			if (clientRequest.getClientName() != null) 
			{
				clientRequest.setCreatedAt(new Date());
				clientRequest.setCreatedBy(currentUser.getUserId());
				clientRepository.save(clientRequest);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.CLIENT_CREATED);
				/*assetIssues.setCreatedAt(new Date());
				assetIssues.setCreatedBy(currentUser.getName());
				assetIssuesRepository.save(assetIssues);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_ISSUES_ADDED_SUCCESSFULLY);*/
	
				//Map content = new HashMap();
				//content.put("clientId", clientRequest.getId());
				//response.setContent(content);
			}
		}

		return response;
	}

	private ApiResponse validateClient(Client clientRequest) 
	{
		ApiResponse response = new ApiResponse(false);
		if(clientRequest.getClientName() == null || clientRequest.getClientName().equals(""))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client name is mandatory !!");
		}
		if(clientRequest.getClientName().length() < 3 )
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client name should be greater than 2 characters !!");
		}
		if(!clientRequest.getClientName().matches("^[a-z A-Z]+"))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"client name should not contain numbers and special characters");
		}
		Client clients = clientRepository.getClientName(clientRequest.getClientName());
		if(clients != null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client name is already exist !!");
		}
		response.setSuccess(true);
		return response;
	}

	
	@Override
	public ApiResponse updateClientStatus(String clientId, ClientStatus status) 
	{
		ApiResponse response = new ApiResponse(false);
		//ApiResponse response = validateStatus(status);
		//if (response.isSuccess()) {
			Client client = clientRepository.getClientById(clientId);
			if (client != null) {
				client.setStatus(status);
				clientRepository.save(client);
				// Employee employeere=new Employee();

				response.setSuccess(true);
				response.setMessage(ResponseMessages.STATUS_UPDATE);
				//response.setContent(null);
			}

			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.CLIENT_INVALID);
				//response.setContent(null);
			}

		//}
		return response;
		
		
		
	}

	@Override
	public ApiResponse editClient(String clientId, Client clientRequest) {
		ApiResponse response = new ApiResponse(false);
		
		Client client = clientRepository.getClientById(clientId);
		if(client != null)
		{
			if (clientRequest.getClientName() != null) 
			{
				response = validateClient(clientRequest);
				client.setClientName(clientRequest.getClientName());
			}	
				clientRepository.save(client);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.CLIENT_UPDATED);
				
				//response.setContent(null);
			
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid client id !!");
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

	@Override
	public ApiResponse getAllClient(Pageable pageable) 
	{
		   ApiResponse response = new ApiResponse(true);
	       Page<Map> ClientList =   clientRepository.getAllClientList(pageable);
	       Map content = new HashMap();
	       if(content != null)
	       {
		       content.put("ClientList", ClientList);
		       
		       response.setMessage(ResponseMessages.CLIENT_LIST);
		       response.setSuccess(true);
		       response.setContent(content);
		       
	       }
	       else
	       {
	    	   response.setSuccess(false);
	    	   response.setMessage("could not retrieve data");
	       }
	       return  response;
	}
}

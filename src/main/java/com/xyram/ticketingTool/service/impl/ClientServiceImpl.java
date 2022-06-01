package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.xyram.ticketingTool.ticket.config.EmployeePermissionConfig;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	CurrentUser currentUser;
	
	@Autowired
	EmployeePermissionConfig empPerConfig;

	@Override
	public ApiResponse addClient(Client clientRequest) throws Exception{
		ApiResponse response = new ApiResponse(false);
		//Client client = new Client();
		if(!empPerConfig.isHavingpersmission("prjAdmin")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
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
		if(clientRequest.getClientName().length() < 3 || clientRequest.getClientName().length() > 30)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client name should be minimum 3 characters and maximum 30 characters!!");
		}
		if(!clientRequest.getClientName().matches("^[a-z 0 -9 A-Z]+"))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"client name should not contain any special characters");
		}
		Client clients = clientRepository.getClientsName(clientRequest.getClientName());
		if(clients != null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client name is already exist !!");
		}
		response.setSuccess(true);
		return response;
	}

	
	@Override
	public ApiResponse updateClientStatus(String clientId, ClientStatus status)  throws Exception
	{
		ApiResponse response = new ApiResponse(false);
		if(!empPerConfig.isHavingpersmission("prjAdmin")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
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
	public ApiResponse editClient(String clientId, Client clientRequest)  throws Exception{
		ApiResponse response = new ApiResponse(false);
		if(!empPerConfig.isHavingpersmission("prjAdmin")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
		Client client = clientRepository.getClientById(clientId);
		if(client != null)
		{
			if(clientRequest.getClientName() != null) 
			{
				validateClientName(client.getId(),clientRequest.getClientName());
				client.setClientName(clientRequest.getClientName());
				//response = validateClient(clientRequest);
				//client.setClientName(clientRequest.getClientName());
			}
			if(clientRequest.getStatus() != null)
			{
				client.setStatus(clientRequest.getStatus());
			}
			clientRequest.setLastUpdatedAt(new Date());
			clientRequest.setUpdatedBy(currentUser.getUserId());
			
			
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
	
	private boolean validateClientName(String Id, String clientName) 
	{
		Client clients = clientRepository.getClient(clientName);
		String client = clientRepository.getClientName(clientName);
		if(clientName == null || clientName.equals(""))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client name is mandatory !!");
		}
		if(clientName.length() < 3 || clientName.length() > 30)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client name should be minimum 3 characters and maximum 30 characters!!");
		}
		if(!clientName.matches("^[a-z 0 -9 A-Z]+"))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"client name should not contain any special characters");
		}
		if(!Id.equals(client)) {
		    if(clients != null) {
			  throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client name already exists!");
		    }
		}
		return true;
	}

	private ApiResponse validateStatus(ClientStatus userstatus) 
	{
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
	public ApiResponse getAllClient(Pageable pageable)  throws Exception
	{
		   ApiResponse response = new ApiResponse(true);
		   if(!empPerConfig.isHavingpersmission("prjAdmin")) {
				response.setSuccess(false);
				response.setMessage("Not authorised to edit Hrcalendar");
				return response;
			}
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

	@Override
	public ApiResponse searchClient(String searchString) throws Exception
	{
		ApiResponse response = new ApiResponse(false);
		if(!empPerConfig.isHavingpersmission("prjAdmin")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}
		List<Map> client = clientRepository.serchClient(searchString);

		Map content = new HashMap();
		content.put("client", client);
		if (content != null) 
		{	
			response.setSuccess(true);
			response.setMessage("client retrived successfully");
			response.setContent(content);
		} 
		else 
		{
			response.setSuccess(false);
			response.setMessage("Not retrived the data");
		}

		return response;

	}
}

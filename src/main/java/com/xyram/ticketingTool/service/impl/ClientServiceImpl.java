package com.xyram.ticketingTool.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.ClientRepository;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.enumType.ClientStatus;
import com.xyram.ticketingTool.service.ClientService;
@Service
@Transactional
public class ClientServiceImpl  implements ClientService{
	private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	ClientRepository clientRepository;
	
	@Override
	public Client addClient(Client clientRequest) {
		/*clientRequest.setCreatedAt(new Date());
		clientRequest.setUpdatedOn(new Date());*/
		clientRequest.setStatus(ClientStatus.ACTIVE);
		Client clients = clientRepository.save(clientRequest);
		return clients;
	}

}

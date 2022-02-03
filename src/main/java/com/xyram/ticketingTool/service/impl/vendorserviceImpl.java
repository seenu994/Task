package com.xyram.ticketingTool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.xyram.ticketingTool.Repository.VendorRepository;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.VendorService;

public class vendorserviceImpl implements VendorService {

	VendorRepository vendorRepository;
	
	@Autowired
	CurrentUser currentUser;
	
	

}

package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Brand;
import com.xyram.ticketingTool.entity.RamSize;
import com.xyram.ticketingTool.service.RamSizeService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class RamSizeController 
{
	private final Logger logger = LoggerFactory.getLogger(RamSizeController.class);
	
	@Autowired
	RamSizeService ramSizeService;
	
	@PostMapping(value = {AuthConstants.INFRA_ADMIN_BASEPATH + "/addRamSize", 
            AuthConstants.ADMIN_BASEPATH + "/addRamSize",
            AuthConstants.INFRA_USER_BASEPATH + "/addRamSize"})
	public ApiResponse addRamSize(@RequestBody RamSize ramSize) {
	logger.info("Received request to add Ram");
	return ramSizeService.addRamSize(ramSize);
	}
	
	@PutMapping(value = {AuthConstants.INFRA_ADMIN_BASEPATH + "/editRamSize/{ramId}", 
            AuthConstants.ADMIN_BASEPATH + "/editRamSize/{ramId}",
            AuthConstants.INFRA_USER_BASEPATH + "/editRamSize/{ramId}"})
	public ApiResponse editRamSize(@RequestBody RamSize ramSize, @PathVariable String ramId) {
	logger.info("Received request to edit Ram Size");
	return ramSizeService.editRamSize(ramSize, ramId);
	}
	
	@GetMapping(value = {AuthConstants.ADMIN_BASEPATH + "/getAllRamSize",
  		  AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllRamSize",
            AuthConstants.INFRA_USER_BASEPATH + "/getAllRamSize"})
    public ApiResponse getAllRamSize(Pageable pageable) {
	        logger.info("Received request to get all Ram Size");
			return ramSizeService.getAllRamSize(pageable);
	 }
	
}

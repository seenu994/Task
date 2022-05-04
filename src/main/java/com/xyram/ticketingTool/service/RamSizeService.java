package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.RamSize;


public interface RamSizeService {

	ApiResponse addRamSize(RamSize ramSize);

	ApiResponse editRamSize(RamSize ramSize, String ramId);

	ApiResponse getAllRamSize(Pageable pageable);

	ApiResponse deleteRam(String ramId);

	ApiResponse searchRamSize(String searchString);
	
	

}

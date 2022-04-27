package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.RamSizeRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Brand;
import com.xyram.ticketingTool.entity.RamSize;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.RamSizeService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class RamSizeServiceImpl implements RamSizeService {

	@Autowired
	RamSizeRepository ramSizeRepository;
	
	@Autowired
	CurrentUser currentUser;
	
	@Override
	public ApiResponse addRamSize(RamSize ramSize) {
		ApiResponse response = new ApiResponse(false);
		response = validateRamSize(ramSize); 
		if (response.isSuccess()) {
			if (ramSize != null) {
				ramSize.setCreatedAt(new Date());
				ramSize.setCreatedBy(currentUser.getUserId());
				ramSize.setRamStatus(true);
				ramSizeRepository.save(ramSize);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.RAM_ADDED);
			}
			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.RAM_NOT_ADDED);
			}
		}
		return response;
	}

	private ApiResponse validateRamSize(RamSize ramSize) {
		ApiResponse response = new ApiResponse(false);

		String regex = "^[a-zA-Z0-9]+$";
		RamSize ramObj = ramSizeRepository.getRamSize(ramSize.getRamSize());
		if (ramSize.getRamSize() == null || ramSize.getRamSize().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram size is mandatory");
		} 
		else if(ramSize.getRamSize().length() < 3 || ramSize.getRamSize().length() > 5){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram size should be greater than 2 & less than 6 characters");
		}
		else if(!ramSize.getRamSize().matches(regex)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram should be alphanumeric characters only");
		}
		else if(ramObj != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ramSize already exists!");
		}
		response.setSuccess(true);
		return response;
	}

	@Override
	public ApiResponse editRamSize(RamSize ramSize, String ramId) {
        ApiResponse response = new ApiResponse(false);
		
		RamSize ramObj = ramSizeRepository.getRamById(ramId);
		if(ramObj != null) {
		   if(ramSize.getRamSize() == null || ramSize.getRamSize().equals("")) { 
			   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram size is mandatory");
		   } 
		   else {
		    checkRamSize(ramSize.getRamSize());
		    ramObj.setRamSize(ramSize.getRamSize());
		    }
		   ramObj.setLastUpdatedAt(new Date());
		   ramObj.setUpdatedBy(currentUser.getUserId());
		   ramObj.setRamStatus(ramSize.isRamStatus());
		   ramSizeRepository.save(ramObj);
		   response.setSuccess(true);
		   response.setMessage(ResponseMessages.RAM_SIZE_EDITED);
		}
		else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.RAM_NOT_EDITED);
		}
		return response;
	}

	private boolean checkRamSize(String ramSize) {
		String regex = "^[a-zA-Z0-9]*$";
		RamSize ramObj = ramSizeRepository.getRamSize(ramSize);
		if(ramSize.length() < 3 || ramSize.length() > 5){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram size should be greater than 2 & less than 6 characters");
		}
		else if(!ramSize.matches(regex)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram should be alphanumeric characters only");
		}
		else if(ramObj != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ramSize already exists!");
		}
		else {
			return true;
		}
	}

	@Override
	public ApiResponse getAllRamSize(Pageable pageable) {
		ApiResponse response = new ApiResponse();
		Page<Map> ramSize = ramSizeRepository.getAllRam(pageable);
		Map content = new HashMap();
		content.put("ramSize", ramSize);
		if(content != null) {
			response.setSuccess(true);
			response.setContent(content);
			response.setMessage(ResponseMessages.RAM_LIST_RETRIVED);
			
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;	}

	@Override
	public ApiResponse deleteRam(String ramId) {
		ApiResponse response = new ApiResponse(false);
		RamSize ramObj = ramSizeRepository.getRamById(ramId);
		if (ramObj != null) {
//			if(!brandObj.getCreatedBy().equals(currentUser.getUserId())) {
//				response.setSuccess(false);
//				response.setMessage("Not authorised to delete this brand");
//			}
			ramSizeRepository.delete(ramObj);
			response.setSuccess(true);
			response.setMessage("RamSize deleted successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Ram Id is not valid.");
		}
		return response;
	}

}

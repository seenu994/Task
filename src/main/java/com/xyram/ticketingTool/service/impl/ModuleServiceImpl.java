package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xyram.ticketingTool.entity.Module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.xyram.ticketingTool.Repository.ModuleRespository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.enumType.JobOpeningStatus;
import com.xyram.ticketingTool.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	ModuleRespository moduleRespository;

	@Override
	public ApiResponse createModule(Module module) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		Module mod = moduleRespository.save(module);

		if (mod != null) {
			response.setSuccess(true);
			response.setMessage("New  modules Created");
		} else {
			response.setSuccess(false);
			response.setMessage("New  modules Not Created");
		}

		return response;

	}

	@Override
	public ApiResponse getAllModules(Pageable pageable) {
		ApiResponse response = new ApiResponse(false);
		Map content = new HashMap();
		Page<Module> modules = moduleRespository.findAll(pageable);
		content.put("moduleList", modules);
		if (modules != null) {
			response.setSuccess(true);
			response.setMessage("New  Module Created");
		} else {
			response.setSuccess(false);
			response.setMessage("New Module Not Created");
		}
		response.setContent(content);

		return response;
	}

	@Override
	public ApiResponse editModuleById(Integer moduleId, Module moduleRequest) {

		ApiResponse response = new ApiResponse(false);
		Module module = moduleRespository.getbyModuleId(moduleId);
		if (module != null) {
			if (moduleRequest.getModuleName() != null) {
				module.setModuleName(moduleRequest.getModuleName());
			}
			if (moduleRequest.getModuleStatus() != null) {
				module.setModuleStatus(moduleRequest.getModuleStatus());

			}

			moduleRespository.save(module);
			response.setSuccess(true);
			response.setMessage("Module Updated Sucessfully");
			response.setContent(null);
		}

		else {
			response.setSuccess(false);
			response.setMessage("Module Id does Not Exist");
		}

		return response;
	}

}

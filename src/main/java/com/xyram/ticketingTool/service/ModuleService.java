package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Module;

public interface ModuleService {

	ApiResponse createModule(Module module);

	ApiResponse getAllModules(Pageable pageable);

	ApiResponse editModuleById(Integer moduleId, Module moduleRequest);

}

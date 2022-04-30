package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Skills;

public interface SkillsService {

	ApiResponse addSkills(Skills skills);

	ApiResponse editSkills(Skills skills, String skillId);

	ApiResponse getAllSkills(Pageable pageable);

}

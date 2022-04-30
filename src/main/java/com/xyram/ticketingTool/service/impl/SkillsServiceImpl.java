package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.BrandRepository;
import com.xyram.ticketingTool.Repository.SkillsRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Brand;
import com.xyram.ticketingTool.entity.Skills;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.SkillsService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class SkillsServiceImpl implements SkillsService{
	
	@Autowired
	SkillsRepository skillsRepository;
	
	@Autowired
	CurrentUser currentUser;

	@Override
	public ApiResponse addSkills(Skills skills) {
		
		ApiResponse response = new ApiResponse(false);
		response = validateSkills(skills); 
		if (response.isSuccess()) {
			if (skills != null) {
				skills.setCreatedAt(new Date());
				skills.setCreatedBy(currentUser.getUserId());
				skills.setSkillStatus(true);
				skillsRepository.save(skills);
				response.setSuccess(true);
				response.setMessage("Skills Added Sccessfully");
			}
			else {
				response.setSuccess(false);
				response.setMessage("Skills not Added");
			}
		}
		return response;	}

	private ApiResponse validateSkills(Skills skills) {
		ApiResponse response = new ApiResponse(false);
//		String regex = "[a-zA-Z]+ ";
		Skills skill = skillsRepository.getSkills(skills.getSkillName());
		if (skills.getSkillName() == null || skills.getSkillName().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "skills is mandatory");
		} 
		else if(skills.getSkillName().length() < 3){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill character length should be greater than 2");
		}
//		else if(!skills.getSkillName().matches(regex)) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill Name should be character only");
//		}
		else if(skill != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill Name already exists");
		}
		response.setSuccess(true);
		return response;
	}

	@Override
	public ApiResponse editSkills(Skills skills, String skillId) {
		ApiResponse response = new ApiResponse(false);
		Skills skill = skillsRepository.getSkillsById(skillId);

		if(skill != null) {
		   if(skills.getSkillName() == null || skills.getSkillName().equals("")) {
			   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill is mandatory");
		    }
		   else {
			   checkSkills(skills.getSkillName(), skillId);
			   skill.setSkillName(skills.getSkillName());
		   }
		   skill.setLastUpdatedAt(new Date());
		   skill.setUpdatedBy(currentUser.getUserId());
		   skill.setSkillStatus(skills.isSkillStatus());
		   skillsRepository.save(skill);
		   response.setSuccess(true);
		   response.setMessage(ResponseMessages.BRAND_EDITED);
		}
		else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.BRAND_NOT_EDITED);
		}
		return response;	}

	private boolean checkSkills(String skillName, String skillId) {
//		String regex = "[a-zA-Z]+";
		Skills skill = skillsRepository.getSkills(skillName);
		String skillObj = skillsRepository.getSkillId(skillName);
		if(skillName.length() < 3){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill character length should be greater than 2");
		}
//		if(!skillName.matches(regex)) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill Name should be character only");
//		}
		if(!skillId.equals(skillObj)) {
		    if(skill != null) {
			   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill Name already exists");
		   }
		}
		return true;
		
	}

	@Override
	public ApiResponse getAllSkills(Pageable pageable) {
		ApiResponse response = new ApiResponse();
		Page<Map> skill = skillsRepository.getAllSkills(pageable);
	
		Map content = new HashMap();
		content.put("skill", skill);
		if(content != null) {
			response.setSuccess(true);
			response.setContent(content);
			response.setMessage("Skills List Retrieved Successfully");
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}

	@Override
	public ApiResponse searchSkills(String searchString) {
		ApiResponse response = new ApiResponse(false);
		List<Map> skills = skillsRepository.searchSkills(searchString);

		Map content = new HashMap();
		content.put("skills", skills);
		if (content != null) {
			response.setSuccess(true);
			response.setMessage("Search Skills retrieved successfully");
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage("could not retrieve the data");
		}
		return response;
	}

}

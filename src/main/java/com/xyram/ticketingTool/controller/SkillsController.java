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
import com.xyram.ticketingTool.entity.Skills;
import com.xyram.ticketingTool.service.BrandService;
import com.xyram.ticketingTool.service.SkillsService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController
public class SkillsController {
private final Logger logger = LoggerFactory.getLogger(BrandController.class);
	
	@Autowired
	SkillsService skillsService;
	
	@PostMapping(value = {AuthConstants.HR_ADMIN_BASEPATH + "/addSkills"})
        public ApiResponse addSkills(@RequestBody Skills skills) {
           logger.info("Received request to add Skills");
           return skillsService.addSkills(skills);
       }
	
	@PutMapping(value = {AuthConstants.HR_ADMIN_BASEPATH + "/editSkills/{skillId}"})
    public ApiResponse editSkills(@RequestBody Skills skills, @PathVariable String skillId) {
        logger.info("Received request to edit Skills");
        return skillsService.editSkills(skills, skillId);
	}
	
	 @GetMapping(value = {AuthConstants.HR_ADMIN_BASEPATH + "/getAllSkills"})
     public ApiResponse getAllSkills(Pageable pageable) {
 	        logger.info("Received request to get all Skills");
 			return skillsService.getAllSkills(pageable);
 	 }
	 
	 @GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/searchSkills/{searchString}"})
		public ApiResponse searchSkills(@PathVariable String searchString) {
			logger.info("Received request to search Skills");
			return skillsService.searchSkills(searchString);
		}


}

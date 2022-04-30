package com.xyram.ticketingTool.Repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Skills;

public interface SkillsRepository extends JpaRepository<Skills, String>{

	@Query("Select s from Skills s where s.skillName =:skillName")
	Skills getSkills(String skillName);

	@Query("Select s from Skills s where s.skillId =:skillId")
	Skills getSkillsById(String skillId);

	@Query("Select s.skillId from Skills s where s.skillName =:skillName")
	Skills getSkillId(String skillName);

	@Query("Select distinct new map(s.skillId as skillId, s.skillName as skillName, s.skillStatus as skillStatus) from Skills s")
	Page<Map> getAllSkills(Pageable pageable);
	
	

	
	

}

package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Sprint;
import com.xyram.ticketingTool.entity.StoryLabel;

public interface StoryLabelRepository extends JpaRepository<StoryLabel, String> {

	
	@Query("select new Map(s.id as id ,s.labelName as labelName) from StoryLabel s where s.projectId=:projectId ")
	List<Map> getStoryLabelByproject(String projectId);
	
	@Query("select s from StoryLabel s where s.id=:id ")
	StoryLabel getStoryLabelById(String id);


}

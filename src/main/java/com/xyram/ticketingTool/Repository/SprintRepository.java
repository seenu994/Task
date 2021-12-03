package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, String> {

	@Query("SELECT s from Sprint s WHERE s.project.pId = :projectId ")
	List<Map> getByprojectId(String projectId);

	@Query(value ="SELECT * from sprint s WHERE s.project_id = :projectId ORDER BY s.sprint_start_date DESC LIMIT 0,1 ",nativeQuery = true)
	Sprint getLatestSprintByProject(String projectId);


}
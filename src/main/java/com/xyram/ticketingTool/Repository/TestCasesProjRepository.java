package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.TestCasesProjects;

@Repository
public interface TestCasesProjRepository extends JpaRepository<TestCasesProjects, String>{
	
	//@Query("Select e.email from Employee e where Select distinct new map(a.project_id, b.project_name) from TestCasesProjects a join Projects b on a.projectId = b.pId")
	//List<Map> getTestCaseProjects();
	
	@Query("Select distinct new map(a.projectId, b.projectName) from TestCasesProjects a join Projects b on a.projectId = b.pId")
	List<Map> getTestCaseProjects(Pageable pageable);
	
	/*
	@Query("select e from User e  where e.id= :userId ")
	List<Map> getTestCaseProjects();
	*/
}

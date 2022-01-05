package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xyram.ticketingTool.entity.JobApplication;

@Repository
@Transactional

public interface JobApplicationRepository
		extends CrudRepository<JobApplication, Long>, JpaSpecificationExecutor<JobApplication> {

	@Query(value = "SELECT ja from JobApplication ja where ja.Id = :appId ")
	JobApplication getApplicationById(String appId);

	@Query(value = "SELECT ja from JobApplication ja")
	List<JobApplication> getList();

	@Query(value = "SELECT * from ticketdbtool.job_application ja where ja.application_id = :jobAppId ", nativeQuery = true)
	Map getAppById(String jobAppId);

	@Query(value = "SELECT * from job_application j where j.job_code=:jobCodeId", nativeQuery = true)
	List<Map> getjobOpeningsById(String jobCodeId);

	@Query("select count(e)>0 from JobApplication e where e. candidateEmail=:candidateEmail")
	boolean findb(String candidateEmail);

}

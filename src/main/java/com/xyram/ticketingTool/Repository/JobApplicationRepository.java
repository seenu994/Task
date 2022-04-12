package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xyram.ticketingTool.entity.JobApplication;
import com.xyram.ticketingTool.enumType.JobApplicationStatus;

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
	
	@Query(value = "SELECT j from JobApplication j where j.jobCode=:jobCodeId")
	List<JobApplication> getjobOpeningsCode(String jobCodeId);

	@Query("select count(e)>0 from JobApplication e where e. candidateEmail=:candidateEmail")
	boolean findb(String candidateEmail);

	
	@Query(value = "SELECT ja from JobApplication ja left join Employee e  where e.eId=:referredEmployeeId")
	
	List<JobApplication> getEmployeeNameByScoleId(String referredEmployeeId);
	

	

	@Query(value = "SELECT ja from JobApplication ja left join ja.jobOpenings as jo where"
			+ " (:vendor is null or  lower(ja.referredVendor)=:vendor )  and "
			+ "(:status is null or ja.jobApplicationSatus=:status) and "
			+ "(:userRole is null  or (:userRole ='HR_ADMIN') OR  (:userRole ='HR') "
			+ " OR (:userRole ='TICKETINGTOOL_ADMIN') "
			+ " OR (ja.createdBy=:userId)) and "
			+ " (:searchString is null  "
			+ " Or lower(ja.candidateEmail) LIKE %:searchString% "
			+ " or lower(str(ja.jobApplicationSatus))  LIKE %:searchString% "
			+ " Or lower(ja.candidateMobile) LIKE %:searchString% "
			+"  Or lower(ja.referredEmployee) Like %:searchString%"
			+"  Or lower(ja.candidateName) Like %:searchString%"
			+ " Or lower(jo.jobCode) LIKE %:searchString%) ORDER BY ja.createdAt DESC")		 
	Page<JobApplication> getAllApllication(String searchString, JobApplicationStatus status,
			String vendor ,String userRole, String userId, Pageable pageable );
	
	@Query(value = "SELECT ja from JobApplication ja  where ja.Id=:jobAppId")


	JobApplication getJobApplicationNotify(String jobAppId);
	@Query(value = "SELECT ja from JobApplication ja  where ja.Id=:jobApplicationId")


	JobApplication getJobApplicationNotifys(String jobApplicationId);

}

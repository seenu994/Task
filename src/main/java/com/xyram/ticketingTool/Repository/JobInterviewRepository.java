package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xyram.ticketingTool.entity.JobApplication;
import com.xyram.ticketingTool.entity.JobInterviews;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.enumType.JobApplicationStatus;

public interface JobInterviewRepository
		extends CrudRepository<JobInterviews, Long>, JpaSpecificationExecutor<JobInterviews> {

	@Query(value = "SELECT * from ticketdbtool.job_interviews jo where jo.interview_id = :interviewId  and  jo.application_id = :applicationId", nativeQuery = true)
	JobInterviews getByDataId(String interviewId, String applicationId);

	@Query(value = "SELECT * from ticketdbtool.job_interviews jo where jo.interview_id = :interviewId  ", nativeQuery = true)
	JobInterviews getById(String interviewId);

	@Query(value = "SELECT jo from JobInterviews jo  ")
	List<JobInterviews> getList();

	@Query(value = "SELECT new map(jo as jobInterviewDetail) from JobInterviews jo where jo.Id = :jobInterviewId  ")
	Map getInterviewById(String jobInterviewId);

	@Query(value = "SELECT new map(jo as jobInterviews) from JobInterviews jo where jo.jobApplication.Id = :applicationId  ")
	List<Map> getInterviewByAppId(String applicationId);

	@Query(value = "SELECT jo as jobInterviews from JobInterviews jo where jo.jobApplication.Id = :applicationId  ")
	List<JobInterviews> getInterviewByAppListId(String applicationId);

	@Query(value = "SELECT jo as jobInterviews from JobInterviews jo where jo.jobApplication.Id = :appId  ")
	JobInterviews getInterviewByAppIdRound(String appId);

	@Query(value = "SELECT jo as jobInterviews from JobInterviews jo where jo.interviewer = :scopeId  ")
	List<Map> getInterviwerByScopeID(String scopeId);

	@Query(value = "SELECT ji from JobInterviews ji left join ji.jobApplication as ja "
			+ " left join ja.jobOpenings as jo where"
			+ " (:status is null Or ji.jobInterviewStatus= :status) "
			+ " (:userRole is null  or ((:userRole ='HR_ADMIN') " + " OR (:userRole ='TICKETINGTOOL_ADMIN') "
			+ " OR (ji.interviewer=:userId))) and " 
			+ " (:searchString is null  "
			+ "  Or lower(ji.interviewerName) LIKE %:searchString% "
			+ "  Or lower(ji.interviewDate) LIKE %:searchString% "
			+ "  Or lower(ji.interviewType) Like %:searchString%"
			+ "  Or lower(ja.candidateEmail) LIKE %:searchString% "
			+ "  Or lower(ja.candidateMobile) LIKE %:searchString% "
			+ "  Or lower(ja.candidateName) LIKE %:searchString% "
			+ "  Or lower(jo.jobCode) LIKE %:searchString%)")
	Page<JobInterviews> getAllJobInterview(String searchString, String status,
			String userRole, String userId, Pageable pageable);

}

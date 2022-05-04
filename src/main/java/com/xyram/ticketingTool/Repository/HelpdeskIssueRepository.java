package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.HelpDeskIssueTypes;
import com.xyram.ticketingTool.entity.SoftwareMaster;
@Repository
@Transactional

public interface HelpdeskIssueRepository  extends JpaRepository <HelpDeskIssueTypes, String> {

	
	
	
//	@Query("Select distinct new map(h.issueTypeId as issueTypeId,h.helpdeskIssueType as helpdeskIssueType) from HelpDeskIssueTypes h "
//    	    + "where p.issueTypeId =:issueTypeId")
//	
//	Page<Map> getIssueById(String issueTypeId, Pageable pageable);

	
	@Query("Select distinct h from HelpDeskIssueTypes h where h.issueTypeId=:issueTypeId")
	HelpDeskIssueTypes getByIssueId(String issueTypeId);

	@Query("Select distinct new map(h.issueTypeId as issueTypeId, h.helpdeskIssueType as helpDeskIssueType) from HelpDeskIssueTypes h")
	Page<Map> getAllIssues(Pageable pageable);
	
	
	
	
	@Query("select distinct new map(h.issueTypeId as issueTypeId,h.helpdeskIssueType as helpdeskIssueType) from HelpDeskIssueTypes h where "
			+ "h.helpdeskIssueType LIKE %:searchString% ")
	
	List<Map> searchhelpdeskIssueType(String searchString);

	
	@Query("Select distinct h from HelpDeskIssueTypes h where h.helpdeskIssueType=:helpdeskIssueType")
	HelpDeskIssueTypes getByIssue(String helpdeskIssueType);
	
	
	
	
}

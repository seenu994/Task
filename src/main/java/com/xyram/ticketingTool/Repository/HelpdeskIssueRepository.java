package com.xyram.ticketingTool.Repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.HelpDeskIssue;

public interface HelpdeskIssueRepository  extends JpaRepository< HelpDeskIssue, String> {

	
	
	
//	@Query("Select distinct new map(p.issue_Id as issue_Id,p.issueName as issueName) from HelpDeskIssue p "
//    	    + "where p.id =:id")
//	
//	Page<Map> getIssueById(String id, Pageable pageable);

	
	@Query("Select distinct p from HelpDeskIssue p where p.issueId=:issueId")
	HelpDeskIssue getByIssueId(String issueId);

	@Query("Select distinct new map(p.issueId as issueId, p.issueName as issueName) from HelpDeskIssue p")
	Page<Map> getAllIssues(Pageable pageable);

	

	

}

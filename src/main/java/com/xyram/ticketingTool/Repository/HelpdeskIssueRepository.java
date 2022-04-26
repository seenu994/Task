package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.HelpDeskIssue;
import com.xyram.ticketingTool.entity.Role;

public interface HelpdeskIssueRepository  extends JpaRepository< HelpDeskIssue, String> {

	

	

}

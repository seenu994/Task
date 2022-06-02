package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;

//import java.util.Map;

//import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Sprint;


//import antlr.collections.List;

public interface SprintService {

	Sprint createSprint(Sprint sprint) throws Exception;

	Sprint editSprint(Sprint sprint,String sId);

	IssueTrackerResponse getSprintByProject(String projectId);

	Sprint getLatestSprintByProject(String id);

	Sprint changeStatusBySprintId(String sprintId, String status);

}

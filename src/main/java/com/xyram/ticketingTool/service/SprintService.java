package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

//import java.util.Map;

//import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Sprint;


//import antlr.collections.List;

public interface SprintService {

	Sprint createSprint(Sprint sprint);

	Sprint editSprint(Sprint sprint,String sId);

	List<Map> getSprintByProject(String projectId);

	Sprint getLatestSprintByProject(String id);

}

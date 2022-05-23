package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.TimeSheet;
import com.xyram.ticketingTool.enumType.TimesheetStatus;

@Repository
public interface TimesheetRepository extends JpaRepository<TimeSheet, String>{
	
	
	@Query(value = "SELECT t from TimeSheet t where Date(t.timeSheetDate) = STR_TO_DATE(:sheetDate, '%Y-%m-%d') and t.employeeId=:employeeId ")
	List<TimeSheet> getAllSheetsByDate(Date sheetDate, String employeeId);
	

	@Query(value = "SELECT distinct  new map( t.timeSheetId as timeSheetId,t.employeeId as employeeId,t.timeSheetDate as timeSheetDate, "
			+ "concat(ee.firstName,' ', ee.lastName) as employeeName,t.projectId as projectId,p.projectName as projectName, "
			+ "t.taskName as taskName,t.taskId as taskId,t.taskDescription as taskDesc,t.approverId as approverId, "
			+ "concat(ep.firstName,' ', ep.lastName) as approverName,t.status as status,t.hoursSpent as hoursSpent "
			+ ",t.taskComments as comments, "
			+ "t.reason as reason) from TimeSheet t left join Employee ep on t.approverId = ep.userCredientials.id "
			+ "left join Employee ee on t.employeeId = ee.eId "
			+ "left join Projects p ON t.projectId = p.pId where "
			+ "(:toDate is null OR Date(t.timeSheetDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR t.status = :status) AND "
			+ "(:projectId is null OR t.projectId=:projectId) AND "
			+ "(:fromDate is null OR Date(t.timeSheetDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND "
			+ "(:userId is null OR t.employeeId=:userId) ")
	Page<Map> getAllMyTimeSheets(String userId,String projectId, String fromDate, String toDate, TimesheetStatus status, Pageable pageable);
	
	@Query(value = "SELECT distinct  new map( t.timeSheetId as timeSheetId,t.employeeId as employeeId,t.timeSheetDate as timeSheetDate, "
			+ "concat(ee.firstName,' ', ee.lastName) as employeeName,t.projectId as projectId,p.projectName as projectName, "
			+ "t.taskName as taskName,t.taskId as taskId,t.taskDescription as taskDesc,t.approverId as approverId, "
			+ "concat(ep.firstName,' ', ep.lastName) as approverName,t.status as status,t.hoursSpent as hoursSpent "
			+ ",t.taskComments as comments, "
			+ "t.reason as reason) from TimeSheet t left join Employee ep on t.approverId = ep.userCredientials.id "
			+ "left join Employee ee on t.employeeId = ee.eId "
			+ "left join Projects p ON t.projectId = p.pId where "
			+ "(:toDate is null OR Date(t.timeSheetDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR t.status = :status) AND "
			+ "(:projectId is null OR t.projectId=:projectId) AND "
			+ "(:fromDate is null OR Date(t.timeSheetDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND "
			+ "(:userId is null OR t.employeeId=:userId) ")
	List<Map> downloadAllMyTimeSheets(String userId,String projectId, String fromDate, String toDate, TimesheetStatus status);
	
	@Query(value = "SELECT count(t.timesheet_id) as timeSheetId from timesheet t left join employee ep on t.approver_id = ep.user_id "
			+ "left join employee ee on t.employee_id = ee.user_id "
			+ "left join project p ON t.project_id = p.project_id where "
			+ "(:toDate is null OR Date(t.timesheet_date) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR t.status=:status) AND "
			+ "(:projectId is null OR t.project_id=:projectId) AND "
			+ "(:fromDate is null OR Date(t.timesheet_date) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND "
			+ "(:userId is null OR t.employee_id=:userId) ",  nativeQuery = true)
	Long getAllMyTimeSheetsCount(String userId,String projectId, String fromDate, String toDate, TimesheetStatus status);
	
	@Query(value = "SELECT distinct new map( t.timeSheetId as timeSheetId,t.employeeId as employeeId,t.timeSheetDate as timeSheetDate, "
			+ "concat(ee.firstName,' ', ee.lastName) as employeeName,t.projectId as projectId,p.projectName as projectName, "
			+ "t.taskName as taskName,t.taskId as taskId,t.taskDescription as taskDesc,t.approverId as approverId, "
			+ "concat(ep.firstName,' ', ep.lastName) as approverName,t.status as status,t.hoursSpent as hoursSpent "
			+ ",t.taskComments as comments, "
			+ "t.reason as reason) from TimeSheet t left join Employee ep on t.approverId = ep.userCredientials.id "
			+ "left join Employee ee on t.employeeId = ee.userCredientials.id "
			+ "left join Projects p ON t.projectId = p.pId where "
			+ "((:toDate is null OR Date(t.timeSheetDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR t.status=:status) AND "
			+ "(:projectId is null OR t.projectId=:projectId) AND "
			+ "(:fromDate is null OR Date(t.timeSheetDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d'))) AND "
			+ "(:employeeId is null OR t.employeeId=:employeeId) AND "
			+ "(t.approverId=:userId)")
	Page<Map> getAllMyTeamTimeSheets(String userId,String employeeId,String projectId, String fromDate, String toDate, TimesheetStatus status, Pageable pageable);

	@Query(value = "SELECT distinct new map( t.timeSheetId as timeSheetId,t.employeeId as employeeId,t.timeSheetDate as timeSheetDate, "
			+ "concat(ee.firstName,' ', ee.lastName) as employeeName,t.projectId as projectId,p.projectName as projectName, "
			+ "t.taskName as taskName,t.taskId as taskId,t.taskDescription as taskDesc,t.approverId as approverId, "
			+ "concat(ep.firstName,' ', ep.lastName) as approverName,t.status as status,t.hoursSpent as hoursSpent "
			+ ",t.taskComments as comments, "
			+ "t.reason as reason) from TimeSheet t left join Employee ep on t.approverId = ep.userCredientials.id "
			+ "left join Employee ee on t.employeeId = ee.userCredientials.id "
			+ "left join Projects p ON t.projectId = p.pId where "
			+ "((:toDate is null OR Date(t.timeSheetDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR t.status=:status) AND "
			+ "(:projectId is null OR t.projectId=:projectId) AND "
			+ "(:fromDate is null OR Date(t.timeSheetDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d'))) AND "
			+ "(:employeeId is null OR t.employeeId=:employeeId) AND "
			+ "(t.approverId=:userId)")
	List<Map> downloadAllMyTeamTimeSheets(String userId,String employeeId,String projectId, String fromDate, String toDate, TimesheetStatus status);
	
	@Query(value = "SELECT count(t.timesheet_id) as timeSheetId from timesheet t left join employee ep on t.approver_id = ep.userCredientials.id  "
			+ "left join employee ee on t.employee_id = ee.user_id "
			+ "left join project p ON t.project_id = p.project_id where "
			+ "((:toDate is null OR Date(t.timesheet_date) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR t.status=:status) AND "
			+ "(:projectId is null OR t.project_id=:projectId) AND "
			+ "(:fromDate is null OR Date(t.timesheet_date) >= STR_TO_DATE(:fromDate, '%Y-%m-%d'))) AND "
			+ "(:employeeId is null OR t.employee_id=:employeeId) AND "
			+ "(t.approver_id=:userId)",  nativeQuery = true)
	Long getAllMyTeamTimeSheetsCount(String userId,String employeeId,String projectId, String fromDate, String toDate, TimesheetStatus status);

	@Query("Select t from TimeSheet t where t.timeSheetId=:timeSheetId")
	TimeSheet getByTimesheetId(String timeSheetId);
	
}

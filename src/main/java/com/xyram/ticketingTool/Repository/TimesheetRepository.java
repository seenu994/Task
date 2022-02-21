package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.TimeSheet;
import com.xyram.ticketingTool.enumType.TimesheetStatus;

@Repository
public interface TimesheetRepository extends JpaRepository<TimeSheet, String>{
	
	

	@Query(value = "SELECT t.timesheet_id as timeSHeetId,t.employee_id as employeeId,t.timesheet_date as timeSheetDate "
			+ "concat(e2.frist_name,' ', e2.last_name) as employeeName,t.project_id as projectId, "
			+ "t.task_name as taskName,t.task_id as taskId,t.task_desc as taskDesc,t.approver_id as approverId, "
			+ "concat(e1.frist_name,' ', e1.last_name) as approverName,t.status as status,t.hours_spent as hoursSpent "
			+ ",t.task_comments as comments, "
			+ "t.reason as reason from timesheet t left join employee e1 on a.approver_id = e1.user_id "
			+ "left join employee e2 on a.employee_id = e2.user_id where "
			+ "(:toDate is null OR Date(a.timesheet_date) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR lower(t.status)=:status) AND "
			+ "(:projectId is null OR t.project_id=:projectId) AND "
			+ "(:fromDate is null OR Date(t.timesheet_date) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND "
			+ "(:userId is null OR lower(t.employee_id)=:userId) ",  nativeQuery = true)
	Page<List<Map>> getAllMyTimeSheets(String userId,String projectId, String fromDate, String toDate, String status, Pageable pageable);

}

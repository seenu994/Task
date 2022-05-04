package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.HrCalendar;

@Repository
@Transactional
public interface HrCalendarRepository extends JpaRepository<HrCalendar, String>{

//	getAllMySchedulesFromCalendarByStatus
	
	@Query("Select distinct new map( a.Id as id,a.candidateMobile as mobile,a.candidateName as name,a.status as status, "
			+ "a.createdAt as createdAt,a.createdBy as createdBy,a.scheduleDate as scheduleDate, a.searchedSource as searchedSource, "
			+ "a.jobId as jobId,jo.jobTitle as jobTitle, a.closed as closed,a.callCount as callCount,a.reportingTo as reportingTo, "
			+ "a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt) from HrCalendar a "
			+ "left join JobOpenings jo on a.jobId = jo.id where a.createdBy = :userId and "
			+ "(:toDate is null OR Date(a.scheduleDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:fromDate is null OR Date(a.scheduleDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR lower(a.status)=:status) AND "
			+ "(:jobId is null OR a.jobId=:jobId) AND "
			+ "(:closed is null OR a.closed=:closed) ORDER BY a.scheduleDate ASC")
	Page<Map> getAllMySchedulesFromCalendarByStatus(String userId,String jobId, 
			String fromDate, String toDate, String status,Boolean closed,Pageable pageable);
	
	/*@Query("Select distinct new map( a.Id as id,a.status as status,a.scheduleDate as scheduleDate,a.closed as closed, "
			+ "concat(ee.firstName,' ', ee.lastName) as scheduledBy,hc.description as comments) from HrCalendar a "
			+ "left join Employee ee on a.createdBy = ee.userCredientials.id "
			+ "left join HrCalendarComment hc on a.id = hc.scheduleId and hc.created_at = (select  max(hc1.created_at) from HrCalendarComment hc1 "
			+ "where a.id = hc1.scheduleId order by hc1.created_at desc) where "
			+ "a.candidateMobile like %:mobileNo% "
			+ "ORDER BY a.scheduleDate ASC")
			*/
	@Query(value ="Select a.Id as id, a.status as status,a.schedule_date as scheduleDate,a.closed as closed, "
			+ "concat(ee.frist_name,' ', ee.last_name) as scheduledBy, hc.description as comments from hrcalendar a "
			+ "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
			+ "left join hrcalendarcomment hc on a.id = hc.schedule_id "
			+ "and hc.created_at = (select max(hc1.created_at) from hrcalendarcomment hc1 where a.id = hc1.schedule_id order by hc1.created_at desc) "
			+ "where a.candidate_mobile like %:mobileNo% "
			+ "ORDER BY a.Id ASC", nativeQuery = true)
	List<Map> getCandidateHistory(String mobileNo);
	
	@Query("Select distinct new map( a.Id as id,a.candidateMobile as mobile,a.candidateName as name,a.status as status, "
			+ "a.createdAt as createdAt,a.scheduleDate as scheduleDate, a.searchedSource as searchedSource, "
			+ "a.jobId as jobId,jo.jobTitle as jobTitle, a.closed as closed,a.callCount as callCount,a.reportingTo as reportingTo, "
			+ "a.createdAt as createdAt,a.createdBy as createdBy,a.lastUpdatedAt as lastUpdatedAt) from HrCalendar a "
			+ "left join JobOpenings jo on a.jobId = jo.id where a.createdBy = :userId and "
			+ "(:searchString is null "
			+ "or a.candidateMobile = :searchString "
			+ "or lower(a.candidateName) like %:searchString% "
			+ "or lower(jo.jobTitle) like %:searchString%) "
			+ "ORDER BY a.scheduleDate ASC")
	List<Map> searchInMyShedule(String userId,String searchString);
	
	@Query("Select distinct new map( a.Id as id,a.candidateMobile as mobile,a.candidateName as name,a.status as status, "
			+ "a.createdAt as createdAt,a.scheduleDate as scheduleDate, a.searchedSource as searchedSource, "
			+ "a.jobId as jobId,jo.jobTitle as jobTitle, a.closed as closed,a.callCount as callCount,a.reportingTo as reportingTo, "
			+ "a.createdAt as createdAt,a.createdBy as createdBy,a.lastUpdatedAt as lastUpdatedAt) from HrCalendar a "
			+ "left join JobOpenings jo on a.jobId = jo.id where a.reportingTo = :reporterId and  "
			+ "(:searchString is null "
			+ "or a.candidateMobile = :searchString "
			+ "or lower(a.candidateName) like %:searchString% "
			+ "or lower(jo.jobTitle) like %:searchString%) "
			+ "ORDER BY a.scheduleDate ASC")
	List<Map> searchInMyTeamShedule(String reporterId,String searchString);
	
	@Query("Select distinct new map( a.Id as id,a.candidateMobile as mobile,a.candidateName as name,a.status as status, "
			+ "a.createdAt as createdAt, a.scheduleDate as scheduleDate, a.searchedSource as searchedSource, "
			+ "a.jobId as jobId,jo.jobTitle as jobTitle,a.createdBy as createdBy, a.closed as closed,a.callCount as callCount,a.reportingTo as reportingTo, "
			+ "concat(ee.firstName,' ', ee.lastName) as scheduledBy, "
			+ "a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt) from HrCalendar a "
			+ "left join Employee ee on a.createdBy = ee.userCredientials.id "
			+ "left join JobOpenings jo on a.jobId = jo.id  where a.reportingTo = :userId and "
			+ "(:toDate is null OR Date(a.scheduleDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:fromDate is null OR Date(a.scheduleDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND "
			+ "(:employeeId is null OR a.createdBy=:employeeId) AND "
			+ "(:status is null OR lower(a.status)=:status) AND "
			+ "(:jobId is null OR a.jobId=:jobId) AND "
			+ "(:closed is null OR a.closed=:closed) ORDER BY a.scheduleDate ASC")
	Page<Map> getAllMyTeamSchedulesFromCalendarByStatus(String userId,String employeeId,String jobId,
			String fromDate, String toDate, String status,Boolean closed,Pageable pageable);
	
	@Query("Select distinct new map(a.candidateName as candidateName,a.status as status,"
			+ "a.scheduleDate as scheduleDate, a.searchedSource as searchedSource,"
			+ "a.closed as closed, jo.jobCode as jobCode, jo.jobTitle as jobTitle) from HrCalendar a left join JobOpenings jo on a.jobId = jo.id where "
			+ "a.createdBy = :userId AND "
			+ "(:toDate is null OR Date(a.scheduleDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:fromDate is null OR Date(a.scheduleDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR a.status=:status) AND "
			+ "(:closed is null OR a.closed=:closed) ORDER BY a.scheduleDate ASC")
			List<Map> downloadAllMySchedulesFromCalendarByStatus(String userId, String fromDate, String toDate, String status,Boolean closed);
	
	@Query("Select distinct new map(a.candidateName as candidateName,a.status as status,"
			+ "a.scheduleDate as scheduleDate, a.searchedSource as searchedSource,jo.jobCode as jobCode,"
			+ "jo.jobTitle as jobTitle,a.closed as closed,concat(ee.firstName,' ', ee.lastName) as scheduledBy) from HrCalendar a "
			+ "left join JobOpenings jo on a.jobId = jo.id left join Employee ee on a.createdBy = ee.userCredientials.id "
			+ "where a.reportingTo =:userId AND " 
			+ "(:toDate is null OR Date(a.scheduleDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:fromDate is null OR Date(a.scheduleDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND "
			+ "(:status is null OR a.status=:status) AND "
			+ "(:closed is null OR a.closed=:closed) ORDER BY a.scheduleDate ASC")
			List<Map> downloadAllMyTeamSchedulesFromCalendarByStatus(String userId,
			String fromDate, String toDate, String status,Boolean closed);
	
	@Query("Select new map(h.Id as id,h.candidateMobile as candidateMobile,h.status as status) from HrCalendar h")
	Page<Map> getAllHrCalendarSchedules(Pageable pageable);

	@Query("Select distinct new map(a.Id as id, a.createdAt as createdAt, a.lastUpdatedAt as lastUpdatedAt,"
			+ "a.status as status, a.callCount as callCount, a.scheduleDate as scheduleDate) from HrCalendar a "
			+ "where a.Id =:scheduleId")
	Map getScheduleById(String scheduleId);

	
}

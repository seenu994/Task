package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Articles;
import com.xyram.ticketingTool.entity.Reminder;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, String> {
	@Query("Select distinct new map( r.ReminderId as ReminderId,r.title as title,r.reminderDate as reminderDate,r.reminderTime as reminderTime,"
			+ " r.userName as userName, r.createdAt as createdAt,r.lastUpdatedAt as lastUpdatedAt,"
			+ " r.UpdatedBy as UpdatedBy, r.createdBy as createdBy) from Reminder r" + " ORDER BY r.createdAt DESC")
	Page<Map> getAllReminders(Pageable pageable);

	@Query("SELECT r from Reminder r where r.ReminderId=:id")
	Reminder findReminderById(String id);
	

//	@Query("Select distinct new map( r.Id as id, "
//			+ "r.createdAt as createdAt,r.createdBy as createdBy,r.scheduleDate as scheduleDate, r.searchedSource as searchedSource, "
//			+ "r.reportingTo as reportingTo,"
//			+ "r.createdAt as createdAt,r.lastUpdatedAt as lastUpdatedAt) from Reminder r "
//			+ "(:toDate is null OR Date(r.reminderDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
//			+ "(:fromDate is null OR Date(r.reminderDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND ")
//
//	Page<Map> getReminderByDate(String userId, String fromDate, String toDate, Pageable pageable);
}

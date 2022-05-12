package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Reminder;
import com.xyram.ticketingTool.entity.ReminderLog;

@Repository
public interface ReminderlogRepository extends JpaRepository<ReminderLog, String> {

	@Query("Select r from ReminderLog r")
	List<ReminderLog> getAllLogs();

	

}

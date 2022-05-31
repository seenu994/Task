package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Articles;
import com.xyram.ticketingTool.entity.Notes;
import com.xyram.ticketingTool.entity.Reminder;
import com.xyram.ticketingTool.entity.ReminderLog;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, String> {
	@Query(value="SELECT r.id, r.updated_by, r.created_at, r.created_by, r.last_updated_at, r.notify_members, r.reminder_date, r.reminder_time, r.title, r.user_id, r.user_name, "
			+ "TIMESTAMPDIFF(MINUTE, CONVERT_TZ(CURRENT_TIMESTAMP(),'+00:00', '+05:30'), CONVERT_TZ(concat(reminder_date, ' ', reminder_time),'+00:00', '+05:30')) DiffDTTM "
			+ "from reminders r "
			+ "where TIMESTAMPDIFF(MINUTE, CONVERT_TZ(CURRENT_TIMESTAMP(),'+00:00', '+05:30'), CONVERT_TZ(concat(reminder_date, ' ', reminder_time),'+00:00', '+05:30')) between 5 and 1",nativeQuery = true)
	List<Reminder> getRemindersWithinFiveMin();
	
	
	@Query(value="SELECT r.id, r.updated_by, r.created_at, r.created_by, r.last_updated_at, r.notify_members, r.reminder_date, r.reminder_time, r.title, r.user_id, r.user_name, "
			+ "TIMESTAMPDIFF(MINUTE, CONVERT_TZ(CURRENT_TIMESTAMP(),'+00:00', '+05:30'), CONVERT_TZ(concat(reminder_date, ' ', reminder_time),'+00:00', '+05:30')) DiffDTTM "
			+ "from reminders r "
			+ "where TIMESTAMPDIFF(MINUTE, CONVERT_TZ(CURRENT_TIMESTAMP(),'+00:00', '+05:30'), CONVERT_TZ(concat(reminder_date, ' ', reminder_time),'+00:00', '+05:30')) between 10 and 6",nativeQuery = true)
	List<Reminder> getRemindersWithinTenMin();
	
	@Query(value="SELECT r.id, r.updated_by, r.created_at, r.created_by, r.last_updated_at, r.notify_members, r.reminder_date, r.reminder_time, r.title, r.user_id, r.user_name, "
			+ "TIMESTAMPDIFF(MINUTE, CONVERT_TZ(CURRENT_TIMESTAMP(),'+00:00', '+05:30'), CONVERT_TZ(concat(reminder_date, ' ', reminder_time),'+00:00', '+05:30')) DiffDTTM "
			+ "from reminders r "
			+ "where TIMESTAMPDIFF(MINUTE, CONVERT_TZ(CURRENT_TIMESTAMP(),'+00:00', '+05:30'), CONVERT_TZ(concat(reminder_date, ' ', reminder_time),'+00:00', '+05:30')) between 15 and 11",nativeQuery = true)
	List<Reminder> getRemindersWithinFifteenMin();
	
	@Query("Select distinct new map( r.reminderId as reminderId,r.title as title,r.reminderDate as reminderDate,r.reminderTime as reminderTime,"
			+ "  r.createdAt as createdAt,r.lastUpdatedAt as lastUpdatedAt,"
			+ " r.UpdatedBy as UpdatedBy, r.createdBy as createdBy) from Reminder r" + " ORDER BY r.createdAt DESC")
	Page<Map> getAllReminders(Pageable pageable);

	@Query("SELECT r from Reminder r where r.reminderId=:id")
	Reminder findReminderById(String id);

	@Query("Select distinct new map(r.reminderId as reminderId,r.title as title,r.reminderDate as reminderDate,r.reminderTime as reminderTime,"
			+ " r.createdAt as createdAt,r.lastUpdatedAt as lastUpdatedAt,"
			+ " r.UpdatedBy as UpdatedBy, r.createdBy as createdBy) from Reminder r where date(r.reminderDate) =(:paramDate) AND r.createdBy=:userId")
	List<Map> getRemindersByDateValue(Date paramDate, String userId);

	@Transactional
	@Modifying
	@Query("Delete from Reminder r where r.reminderId=?1")
	void deleteReminder(String id);

}

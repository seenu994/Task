package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.HrCalendarComment;

@Repository
public interface HrCalendarCommentRepository extends JpaRepository<HrCalendarComment, String>{

	@Query("Select new map(h.Id as id,h.description as description,h.createdAt as createdAt,h.createdBy as createdBy) from HrCalendarComment h where"
			+ " h.scheduleId = :scheduleId")
	List<Map> getAllScheduleComments(String scheduleId);
}

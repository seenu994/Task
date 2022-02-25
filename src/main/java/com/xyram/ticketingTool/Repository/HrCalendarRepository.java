package com.xyram.ticketingTool.Repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.entity.HrCalendar;

@Repository
public interface HrCalendarRepository extends JpaRepository<HrCalendar, String>{

	@Query("Select new map(h.Id as id,h.candidateMobile as candidateMobile,h.status as status) from hrcalendar h")
	Page<Map> getAllHrCalendarSchedules(Pageable pageable);
}

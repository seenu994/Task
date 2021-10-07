package com.xyram.ticketingTool.Repository;

import javax.management.Notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Notifications;



public interface NotificationRepository extends JpaRepository<Notifications, String> {
	
	@Query("SELECT n FROM Notifications n WHERE n.receiverId = :id ")

	Page<Notifications> getAllNotifications(String id, Pageable pageable);

}

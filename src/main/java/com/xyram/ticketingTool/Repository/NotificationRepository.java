package com.xyram.ticketingTool.Repository;

import java.util.List;

import javax.management.Notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Notifications;



public interface NotificationRepository extends JpaRepository<Notifications, String> {
	
	@Query("SELECT n FROM Notifications n WHERE n.receiverId = :id and n.seenStatus = 0 ORDER  BY n.createdAt DESC")
	Page<Notifications> getAllNotifications(String id, Pageable pageable);
	
	@Query("SELECT COUNT(n) FROM Notifications n WHERE n.receiverId = :id and n.seenStatus = 0")
    long getNotificationCount(String id);
	
	@Modifying(flushAutomatically = true,clearAutomatically = true)
	@Query("update Notifications n set n.seenStatus = 1 where n.receiverId = :id ")
	void clearAllNotifications(String id);

}

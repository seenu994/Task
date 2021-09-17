package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, String> {

}

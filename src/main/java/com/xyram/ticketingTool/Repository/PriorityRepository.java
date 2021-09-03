package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.Priority;

public interface PriorityRepository extends JpaRepository<Priority, Integer> {

}

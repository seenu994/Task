package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.Projects;

public interface  ProjectRepository extends  JpaRepository<Projects,Integer> {

}

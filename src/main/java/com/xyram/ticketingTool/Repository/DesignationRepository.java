package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, String> {

}

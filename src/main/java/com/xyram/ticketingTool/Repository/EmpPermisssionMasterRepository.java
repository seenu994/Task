package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.EmpPermisssionMaster;


@Repository
public interface EmpPermisssionMasterRepository extends JpaRepository<EmpPermisssionMaster, String>{

	
}

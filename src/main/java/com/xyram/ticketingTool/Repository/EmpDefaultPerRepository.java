package com.xyram.ticketingTool.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.EmpDefaultPermissions;

@Repository
@Transactional
public interface EmpDefaultPerRepository extends JpaRepository<EmpDefaultPermissions, String>{

}

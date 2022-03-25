package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.SoftwaresInstalled;

@Repository
public interface SoftwaresInstalledRepository extends JpaRepository<SoftwaresInstalled, String> {

}

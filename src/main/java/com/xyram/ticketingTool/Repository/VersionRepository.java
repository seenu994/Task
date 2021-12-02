package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Version;

@Repository
public interface VersionRepository extends JpaRepository<Version, String> {

}

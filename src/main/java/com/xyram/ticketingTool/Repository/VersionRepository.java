package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Version;

@Repository
public interface VersionRepository extends JpaRepository<Version, String> {

	@Query("Select v from Version v where v.id = :id")
	Version getByVersionId(String id);

	
	@Query("Select v from Version v where v.projectId=:id")
	 List<Version> getByProjectId(String id);

}

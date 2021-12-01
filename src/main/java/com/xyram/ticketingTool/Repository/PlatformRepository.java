package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.entity.Sprint;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, String> {

	@Query("select new map(p.id,p.platformName as platformName) from Platform p where p.projectId=:projectId")
	List<Map> getStoryPlatformByProject(String projectId);

}

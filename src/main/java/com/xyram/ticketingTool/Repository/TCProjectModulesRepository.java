package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.TCProjectModules;

public interface TCProjectModulesRepository extends JpaRepository<TCProjectModules, String> {
	
	@Query("Select distinct new map(a.tcpmId, a.moduleDesc, a.projectId, b.projectName) from TCProjectModules a join Projects b on a.projectId = b.pId")
	List<Map> getTCProjectModules(Pageable pageable);

}

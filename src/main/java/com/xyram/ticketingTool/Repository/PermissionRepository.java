
package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.entity.Permissions;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, Integer> {
	
	
	@Query("Select new map(p.permissionId as id,p.permissionName as name,p.status as status) from Permissions p")
	List<Map> getAllPermissions();

	
}
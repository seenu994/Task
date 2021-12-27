package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.RoleMasterTable;

@Repository
public interface RoleMasterRepository extends JpaRepository<RoleMasterTable,String> {

	@Query("Select p from RoleMasterTable p Join Role r ON p.rolePermissionId = r.Id WHERE p.rolePermissionId = :roleId  ")
	List<RoleMasterTable> getAllRolePermissions(String roleId);

	@Query("Select p from RoleMasterTable p  WHERE p.rolePermissionId = :roleId and p.modules = :modules ")
	RoleMasterTable updateRolePermissions(String roleId, String modules);

}

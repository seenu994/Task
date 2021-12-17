package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.UserPermissions;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermissions, String> {

	@Query("Select u from UserPermissions u where u.userId = :id")
	List<UserPermissions> getByUserId(String id);

	@Query("Select ur.roleId from UserPermissions u INNER JOIN Employee ur ON u.userId = ur.userCredientials.id where ur.roleId = :roleId")
	List<String> getDetailsyRole(String roleId);

	@Query("Select u from UserPermissions u INNER JOIN Employee ur ON u.userId = ur.userCredientials.id where ur.roleId = :roleId")
	List<UserPermissions> getByRole(String roleId);

}

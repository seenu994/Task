package com.xyram.ticketingTool.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.admin.model.TicketUserDetails;
import com.xyram.ticketingTool.admin.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	@Query("SELECT u FROM User u Where LOWER(u.username)=:username")
	List<User> findByUsername(String username);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update User  u set u.uid = :uid WHERE u.username=:username")
	int updateUid(@Param("username")String username, @Param("uid") String uid);

	
}

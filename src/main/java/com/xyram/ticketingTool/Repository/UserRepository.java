package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.admin.model.TicketUserDetails;
import com.xyram.ticketingTool.admin.model.User;

public interface UserRepository extends JpaRepository<User, String>{

	@Query("SELECT u FROM User u Where LOWER(u.username)=:username")
	List<User> findByUsername(String username);

}

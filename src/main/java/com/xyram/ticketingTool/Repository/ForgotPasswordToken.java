package com.xyram.ticketingTool.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.entity.ForgotPasswordKey;
import com.xyram.ticketingTool.entity.TicketFile;

@Repository
public interface ForgotPasswordToken extends JpaRepository<ForgotPasswordKey,String> {

	@Query("select r from ForgotPasswordKey r where r.resetPasswordToken = :accesskey")
	ForgotPasswordKey findByAccestoken(@Param("accesskey") String accesskey);
}

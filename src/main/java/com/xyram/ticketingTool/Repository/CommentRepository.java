package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, String> {

}

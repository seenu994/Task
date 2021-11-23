package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.StoryComments;

@Repository
public interface StoryCommentRepository extends JpaRepository<StoryComments, String>{

}

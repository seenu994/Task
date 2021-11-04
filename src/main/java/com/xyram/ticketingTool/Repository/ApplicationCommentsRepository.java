package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.ApplicationComments;

@Repository
public interface ApplicationCommentsRepository extends JpaRepository<ApplicationComments,String> {

}

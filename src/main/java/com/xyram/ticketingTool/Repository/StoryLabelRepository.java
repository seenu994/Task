package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.Sprint;
import com.xyram.ticketingTool.entity.StoryLabel;

public interface StoryLabelRepository extends JpaRepository<StoryLabel, String> {


}

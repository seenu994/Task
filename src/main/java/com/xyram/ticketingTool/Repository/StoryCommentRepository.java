package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.StoryComments;

@Repository
public interface StoryCommentRepository extends JpaRepository<StoryComments, String>{

	@Query("SELECT new map(sc.description as description,sc.mentionTo as mentionTo,sc.commentedDate as commentedDate"
			+ " ,sc.storyId as storyId,p1.projectName as projectName,"
			+ "CONCAT(e1.firstName ,' ', e1.lastName) as coommentedBy,sc.storyAttachment as storyAttachment)  from StoryComments sc"
			+ " left join Projects p1 on p1.pId=sc.projectId "
			+   "left join sc.storyAttachment sa "
			+ " left join Employee e1 on e1.eId=sc.commentedBy WHERE sc.projectId=:id"  )
	List<Map> getCommentsByProjectId(String id);

	@Query("SELECT new map(sc.description as description,sc.mentionTo as mentionTo,sc.commentedDate as commentedDate"
			+ " ,sc.storyId as storyId,p1.projectName as projectName,"
			+ "CONCAT(e1.firstName ,' ', e1.lastName) as coommentedBy,sc.storyAttachment as storyAttachment)  from StoryComments sc"
			+ " left join Projects p1 on p1.pId=sc.projectId "
			+   "left join sc.storyAttachment sa "
			+ " left join Employee e1 on e1.eId=sc.commentedBy WHERE sc.projectId=:projectId AND sc.storyId=:storyId"  )
	List<Map> getStoryCommentbyprojectIdandStoryId(String projectId,String storyId);

	// sc.mentionTo as mentionTo,sc.commentedDate as commentedDate,sc.storyId as storyId, "
//	+ " p1.projectName,CONCAT(e1.firstName ,' ', e1.lastName) as coommentedBy,sc.storyAttachment as storyAttachment)
}

package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.StoryComments;

@Repository
public interface StoryCommentRepository extends JpaRepository<StoryComments, String> {

	@Query("select new map(s.id as id , s.description as description "
			+ ", CONCAT(e1.firstName ,' ', e1.lastName) as commentedBy, "
			+ " CONCAT(e2.firstName ,' ', e2.lastName) as mentionTo ,s.commentedDate as commentedDate "
			+ ",s.storyAttachment as storyAttachment) from StoryComments s "
			+ "left join s.storyAttachment sa "
			+ "left join Employee e1 on e1.eId=s.commentedBy "
			+ "left join Employee e2 on e2.eId=s.mentionTo where s.storyId= :storyId")
	List<Map> getStoryCommentsByStory(String storyId);

	@Modifying
	@Query("delete  from StoryComments s where s.id=:id")
	Integer deleteStoryCommentsById(String id);

}

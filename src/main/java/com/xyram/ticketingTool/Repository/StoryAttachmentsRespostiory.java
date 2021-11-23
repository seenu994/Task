package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.StoryAttachments;

@Repository
public interface StoryAttachmentsRespostiory extends JpaRepository<StoryAttachments, String> {

	@Query("select new Map (s.id as id, s.fileName as fileName ,s.fileDownloadUrl as fileDownloadUrl , "
			+ "CONCAT(e1.firstName ,' ', e1.lastName) as uploadedBy,s.uploadedOn as uploadedOn)   from StoryAttachments s "
			+ " left join Employee e1 on e1.eId=s.uploadedBy  where s.storyId=:storyId")
	List<Map> getStoryAttachmentListByStoryId(String storyId);

	@Modifying
	@Query("DELETE FROM StoryAttachments p WHERE p.id=:storyAttachmentId")
	Integer deleteStoryAttachment(String storyAttachmentId);

}

package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, String> {
	
	
	
	
    @Query("select  count(s.id)  from Story s where s.projectId=:projectId ")
	Integer getTotalTicketByprojectId(String projectId);
	
    
    @Query("select s from Story s where s.id=:id")
    Story getStoryById(String id );

    
    
    @Query("select   new Map( s.storyNo as storyNo ,s.title as title , p.projectName as projectName,f.featureName as storyStatus,  "
    		+ " s.id as id, s.storyType as storyType,CONCAT(e1.firstName ,' ', e1.lastName) as owner, "
    		+ "CONCAT(e2.firstName ,' ', e2.lastName) as assignedTo )" 
    		+ " From Story s left join Projects p on p.pId =s.projectId "
    		+ "left join Feature f on f.featureId=s.storyStatus "
    		+ "left join Employee e1 on e1.eId=s.owner "
    		+ "left join Employee e2 on e2.eId=s.assignTo where s.projectId=:projectId")
  List<Map>  getAllStories(String projectId);
    
    
    
    
    
    @Query("select   new Map( s.storyNo as storyNo ,s.title as title , p.projectName as projectName,f.featureName as storyStatus,  "
    		+ " s.id as id, s.storyType as storyType,CONCAT(e1.firstName ,' ', e1.lastName) as owner, "
    		+ "CONCAT(e2.firstName ,' ', e2.lastName) as assignedTo ,s.storyDescription as storyDescription ,s.owner as owenerid, s.assignTo as assignId, sl.labelName as label,pt.platformName as platform) " 
    		+ " From Story s"
    		+ " left join Projects p on p.pId =s.projectId "
    		+ "left join Feature f on f.featureId=s.storyStatus "
    		+ "left join Employee e1 on e1.eId=s.owner "
    		+ "left join Employee e2 on e2.eId=s.assignTo "
    		+ "left join StoryLabel sl on sl.id=s.storyLabel "
    		+" left join Platform pt on pt.id=s.platform "
    		+ " where s.id=:storyId and s.projectId =:projectId ")
  Map  getAllStoriesByStoryId(String projectId ,String storyId);

    
//    @Query("select   new Map( s.storyNo as storyNo ,s.title as title , p.projectName as projectName,f.featureName as storyStatus,  "
//    		+ " s.id as id, s.storyType as storyType,CONCAT(e1.firstName ,' ', e1.lastName) as owner, "
//    		+ "CONCAT(e2.firstName ,' ', e2.lastName) as assignedTo ,s.storyDescription as storyDescription) " 
//    		+ " From Story s"
//    		+ " left join Projects p on p.pId =s.projectId "
//    		+ "left join Feature f on f.featureId=s.storyStatus "
//    		+ "left join StoryLabel sl on s.storyLabel=sl.id "
//    		+ "left join Employee e1 on e1.eId=s.owner "
//    		+ "left join Employee e2 on e2.eId=s.assignTo "
//    		
//    		+ " where s.id=:storyId and s.projectId =:projectId ")
//  Map  getAllStoriesByStoryId(String projectId ,String storyId);
    
    
    
    
    @Query("select   new Map( s.storyNo as storyNo ,s.title as title , p.projectName as projectName,f.featureName as storyStatus,  "
    		+ " s.id as id, s.storyType as storyType,CONCAT(e1.firstName ,' ', e1.lastName) as owner, "
    		+ "CONCAT(e2.firstName ,' ', e2.lastName) as assignedTo )" 
    		+ " From Story s left join Projects p on p.pId =s.projectId "
    		+ "left join Feature f on f.featureId=s.storyStatus "
    		+ "left join Employee e1 on e1.eId=s.owner "
    		+ "left join Employee e2 on e2.eId=s.assignTo"
    		+ " where s.projectId=:projectId and s.storyStatus=:storystatusId")
	List<Map> getAllStoriesByStoryStaus(String projectId ,String storystatusId) ;
}

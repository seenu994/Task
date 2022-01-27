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
	Story getStoryById(String id);

	@Query("select   new Map( s.storyNo as storyNo ,s.version as version,s.title as title ,sl.labelName as storyLabel, s.createdOn as createdOn, p.projectName as projectName,f.featureName as storyStatus,  "
			+ " s.id as id, s.storyType as storyType,CONCAT(e1.firstName ,' ', e1.lastName) as owner, "
			+ "CONCAT(e2.firstName ,' ', e2.lastName) as assignedTo )"
			+ " From Story s left join Projects p on p.pId =s.projectId "
			+ "left join Feature f on f.featureId=s.storyStatus " + "left join Employee e1 on e1.eId=s.owner "
			+ " left join Platform pt on pt.id=s.platform " + "left join StoryLabel sl on sl.id=s.storyLabel "
			+ "left join Employee e2 on e2.eId=s.assignTo where s.projectId=:projectId and "
			+ "(:searchString  is null " + " OR  s.storyType Like %:searchString% "
			+ " OR  CONCAT(e2.firstName ,' ', e2.lastName) LIKE %:searchString% " + " OR s.title LIKE %:searchString% "

			+ " OR f.featureName LIKE %:searchString% " + " OR pt.platformName LIKE %:searchString% "
			+ " OR s.storyDescription LIKE %:searchString%)")

	List<Map> getAllStories(String projectId, String searchString);
//	s.storyNo as storyNo ,s.version as version,s.title as title , p.projectName as projectName,f.featureName as storyStatus,s.storyStatus as storyStatusId, f.featureId as featureId , "
//			+ " s.id as id, s.storyType as storyType,CONCAT(e1.firstName ,' ', e1.lastName) as owner, sp.sprintName as sprintName ,s.sprintId as sprintId,"
//			+ "CONCAT(e2.firstName ,' ', e2.lastName) as assignedTo,s.version as versionId ,s.createdOn as createdOn, v.versionName as versionName ,s.storyDescription as storyDescription ,s.owner as owenerid, s.assignTo as assignId, sl.labelName as storyLabel,"
//			+ "pt.platformName as platform, s.storyLabel as s.storyLabelId) 

	@Query("select   new Map( s.storyNo as storyNo ,s.version as version,s.title as title "
			+ ",f.featureName as storyStatus,s.storyStatus as storyStatusId, f.featureId as featureId, "
			+ "s.id as id, s.storyType as storyType,CONCAT(e1.firstName ,' ', e1.lastName) as owner, sp.sprintName as sprintName ,s.sprintId as sprintId,"
			+ "CONCAT(e2.firstName ,' ', e2.lastName) as assignedTo,s.version as versionId "
			+ ",s.createdOn as createdOn, v.versionName as versionName ,s.storyDescription as storyDescription ,s.owner as owenerid,"
			+ "s.assignTo as assignId, sl.labelName as storyLabel,"
			+ "pt.platformName as platform, s.platform as platformId ,s.storyLabel as storyLabelId) " + " From Story s"
			+ " left join Projects p on p.pId =s.projectId " + "left join Feature f on f.featureId=s.storyStatus "
			+ "left join Employee e1 on e1.eId=s.owner " + "left join Employee e2 on e2.eId=s.assignTo "
			+ "left join StoryLabel sl on sl.id=s.storyLabel " + " left join Platform pt on pt.id=s.platform "
			+ " left join Version v on v.id= s.version " + "left join Sprint sp on sp.id=s.sprintId "
			+ " where s.id=:storyId and s.projectId =:projectId ")
	Map getAllStoriesByStoryId(String projectId, String storyId);

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

	@Query(value = "select  s.story_no as storyNo ,s.version as version,s.title as title , s.created_on as createdOn, p.project_name as projectName,f.feature_name as storyStatus,  "
			+ " s.id as id, s.story_type as storyType,CONCAT(e1.frist_name ,' ', e1.last_name) as owner, "
			+ "CONCAT(e2.frist_name ,' ', e2.last_name) as assignedTo  , sl.label_name as storyLabel  "
			+ "from story s " + "left join project p on p.project_id =s.project_id "
			+ " left join feature f on f.feature_id=s.story_status_id "
			+ " left join employee e1 on e1.employee_id=s.owner  " + " left join platform pt on pt.id=s.platform  "
			+ " left join story_label sl on sl.id =s.story_label "
			+ " left join employee e2 on e2.employee_id=s.assign_to " + " Where s.project_id=:projectId and "
			+ " (:assignTo is null or find_in_set(s.assign_to ,:assignTo)) and "
			+ " (:platform is null or find_in_set(s.platform , :platform)) and  "
			+ " (:storyStatus is null or find_in_set(s.story_status_id ,(:storyStatus))) and "
			+ " (:storyType is null or find_in_set(s.story_type ,:storyType)) and "
			+ " (:storyLabel is null or find_in_set(s.story_label,:storyLabel)) and " + "(:searchString  is null "
			+ " OR  s.story_type Like %:searchString%  "
			+ " OR  CONCAT(e1.frist_name ,' ', e1.last_name) LIKE %:searchString% "
			+ " OR  CONCAT(e2.frist_name ,' ', e2.last_name) LIKE %:searchString% "
			+ " OR s.title LIKE %:searchString% " + " OR pt.platform_name LIKE %:searchString% "
			+ " OR f.feature_name LIKE %:searchString% "
			+ " OR s.story_no LIKE %:searchString% "
			+ " OR sl.label_name  LIKE %:searchString% "
			+ "  OR s.story_description Like %:searchString% )", nativeQuery = true)
	public List<Map> getStoryTesting(String projectId, String searchString, String assignTo, String platform,
			String storyStatus, String storyType, String storyLabel);

	@Query("select   new Map( s.storyNo as storyNo ,s.version as version,s.title as title , sl.labelName as storyLabel,"
			+ " p.projectName as projectName,f.featureName as storyStatus,  "
			+ " s.id as id, s.storyType as storyType,CONCAT(e1.firstName ,' ', e1.lastName) as owner,s.createdOn as createdOn,"
			+ "CONCAT(e2.firstName ,' ', e2.lastName) as assignedTo )"
			+ " From Story s left join Projects p on p.pId =s.projectId "
			+ "left join StoryLabel sl on sl.id=s.storyLabel " + "left join Feature f on f.featureId=s.storyStatus "
			+ "left join Employee e1 on e1.eId=s.owner " + "left join Employee e2 on e2.eId=s.assignTo"
			+ " where s.projectId=:projectId and s.storyStatus=:storystatusId")
	List<Map> getAllStoriesByStoryStaus(String projectId, String storystatusId);


	@Query("select   new Map(s.id as id,s.assignTo as assignTo,e.userCredientials.uid as uid )From Story s left join Employee e On s.assignTo=e.eId where s.assignTo=:assignTo")
	List<Map> getVendorNotification(String assignTo);
}

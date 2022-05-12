
package com.xyram.ticketingTool.Repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String>{
	
	
	
	@Query("Select distinct new map( a.announcementId as announcementId,a.title as title,a.searchLabels as searchLabels,a.status as status,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Announcement a where a.status = 'ACTIVE' "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> getAllActiveAnnouncements(Pageable pageable);
	
	@Query("Select distinct new map( a.announcementId as announcementId,a.title as title,a.searchLabels as searchLabels,a.status as status,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Announcement a "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> getAllAnnouncements(Pageable pageable);
	
	@Query("Select distinct new map( a.announcementId as announcementId,a.title as title,a.searchLabels as searchLabels,a.status as status,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Announcement a where a.userId=:userId  "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> getAllMyAnnouncements(Pageable pageable, String userId); 
	
	@Query("Select distinct new map( a.announcementId as announcementId,a.title as title,a.searchLabels as searchLabels,a.status as status,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Announcement a where "
			+ " (LOWER(a.title) like %:searchString% or LOWER(a.description) like %:searchString%) "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> searchAllAnnouncements(Pageable pageable, String searchString);
	
	@Query("Select distinct new map( a.announcementId as announcementId,a.title as title,a.searchLabels as searchLabels,a.status as status,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Announcement a where a.status = 'ACTIVE' and (a.title like %:searchString% or a.description like %:searchString% or a.searchLabels like %:searchString%) "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> searchAllActiveAnnouncements(Pageable pageable, String searchString);
	
	@Query("SELECT a from Announcement a where a.announcementId=:id")
	Announcement findAnnouncementById(String id);


}

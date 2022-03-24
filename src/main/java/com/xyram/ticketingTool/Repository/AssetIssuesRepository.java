package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.AssetIssues;

@Repository
@Transactional
public interface AssetIssuesRepository extends JpaRepository<AssetIssues, String>
{
	@Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate, "
			+ "i.description as description, i.Solution as Solution, "
			+ "i.Status as Solution, i.vendorId as vendorId, "
			+ "i.resolvedDate as resolvedDate) from AssetIssues i")
	Page<Map> getAllAssetIssues(Pageable pageable);
	
	
	@Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate,"
			+ "i.description as description, i.Solution as Solution,"
			+ "i.Status as Solution, i.vendorId as vendorId,"
            + "i.resolvedDate as resolvedDate) from AssetIssues i"
			+ "where i.status = 'open')")
	List<Map> searchAssetIssue(@Param("searchString") String issueId);
	
	
	
}

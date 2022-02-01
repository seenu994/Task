package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.JobInterviews;
import com.xyram.ticketingTool.entity.JobOffer;
import com.xyram.ticketingTool.entity.JobOpenings;

@Repository
public interface JobOfferRepository extends CrudRepository<JobOffer, Long>, JpaSpecificationExecutor<JobOffer> {

	@Query("Select jo from JobOffer jo where jo.Id = :jobOfferId")
	JobOffer getById(String jobOfferId);

	@Query("Select jo from JobOffer jo where jo.Id = :offerId")
	List<JobOffer> getAllJobOfferById(String offerId);

	@Query(value = " Select jo from JobOffer jo   where " + " (:userRole is null  or (:userRole IN('HR_ADMIN','TICKETINGTOOL_ADMIN')) ) and"
			+ " (:searchString is null  " + "  Or lower(jo.candidateEmail) LIKE %:searchString% "
			+ "  Or lower(jo.candidateMobile) LIKE %:searchString% "
			+ "  Or lower(jo.candidateName) Like %:searchString%" + "  Or lower(jo.status)  Like %:searchString%"
			+ "  Or lower(jo.jobTitle) LIKE %:searchString% " + "  Or lower(jo.doj) Like %:searchString%) ORDER BY jo.createdAt DESC")
	Page<JobOffer> getAllJobOffer(String searchString, String userRole, Pageable pageable);

	@Query(value = " Select jo from JobOffer jo   where  jo.applicationId.referredVendor = :name and "
			+ " (:searchString is null  " + "  Or lower(jo.candidateEmail) LIKE %:searchString% "
			+ "  Or lower(jo.candidateMobile) LIKE %:searchString% "
			+ "  Or lower(jo.candidateName) Like %:searchString%" + "  Or lower(jo.status)  Like %:searchString%"
			+ "  Or lower(jo.jobTitle) LIKE %:searchString% " + "  Or lower(jo.doj) Like %:searchString%)ORDER BY jo.createdAt DESC")
	Page<JobOffer> getAllJobOfferVendors(String searchString,String name, Pageable pageable);

}

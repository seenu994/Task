package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.JobOffer;
import com.xyram.ticketingTool.entity.JobOpenings;

@Repository
public interface JobOfferRepository extends CrudRepository<JobOffer,Long>,JpaSpecificationExecutor<JobOffer> {

	@Query("Select jo from JobOffer jo where jo.Id = :jobOfferId")
	JobOffer getById(String jobOfferId);

}

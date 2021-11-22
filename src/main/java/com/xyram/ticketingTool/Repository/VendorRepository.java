package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.JobVendorDetails;

public interface VendorRepository extends JpaRepository<JobVendorDetails,String> {

	@Query("Select jv from JobVendorDetails jv")
	List<Map> getJobVendors();

}

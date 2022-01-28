package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.JobVendorDetails;

public interface VendorRepository extends JpaRepository<JobVendorDetails,String> {

	@Query("Select jv from JobVendorDetails jv  Where "
			+ "(:searchString is null or jv.name LIKE %:searchString% )")
	Page<JobVendorDetails> getJobVendors(String searchString, Pageable pageable);
	
	@Query("Select jv from JobVendorDetails jv where jv.name LIKE %:vendorName% ")
	List<JobVendorDetails> serachJobVendors(String vendorName);

//	@Query("Select jv from JobVendorDetails jv Where jv.vId = :vendorId")
//	Map getJobVendorById1(String vendorId);
	

	@Query("Select jv from JobVendorDetails jv Where jv.vId = :vendorId")
	JobVendorDetails getJobVendorById(String vendorId);

}

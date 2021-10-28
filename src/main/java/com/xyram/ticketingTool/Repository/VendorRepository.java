package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.JobVendorDetails;

public interface VendorRepository extends JpaRepository<JobVendorDetails,String> {

}

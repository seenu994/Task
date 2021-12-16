package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.VendorType;

@Repository
public interface VendorTypeRepository extends JpaRepository<VendorType,String> {

	@Query(value ="SELECT a from VendorType a")
	List<VendorType> getJobVendorType();

}

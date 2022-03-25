package com.xyram.ticketingTool.Repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Projects;
@Repository
public interface AssetVendorRepository extends JpaRepository<AssetVendor, String> {


	
	      @Query("select  new map(p.vendorID as id,p.address as address,p,p.vendorName as vendorName,"
	      		+ "p.mobileNo as mobileNo,p.email as email,p.City as City,p.Country as Country,p.status as Status) from AssetVendor  p")
	Page<Map> getAllAssetVendorList(Pageable pageable);	
	      
	      @Query("Select distinct p from AssetVendor p where p.vendorID= :id")
	      AssetVendor getvebdorById(String id);

	      @Query("Select a.email from AssetVendor a where a.email = :email")
	  	String filterByEmail(String email);

	  

}

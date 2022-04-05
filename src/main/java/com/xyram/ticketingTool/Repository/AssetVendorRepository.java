package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Client;

@Repository
public interface AssetVendorRepository extends JpaRepository<AssetVendor, String> {

	@Query("SELECT a from AssetVendor a where a.vendorId =:vendorID")
	AssetVendor getByvendorId(String vendorID);


}

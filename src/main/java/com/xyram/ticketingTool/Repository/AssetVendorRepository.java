package com.xyram.ticketingTool.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetVendor;


@Repository
@Transactional
public interface AssetVendorRepository extends JpaRepository<AssetVendor, String> {

	static AssetVendor getById(AssetVendor assetVendor) {
		// TODO Auto-generated method stub
		return assetVendor;
	}

	
    
	
    
	


}

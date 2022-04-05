package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetStatus;

public interface AssetStatusRepository extends JpaRepository<AssetStatus, String>{

	@Query("SELECT a from AssetStatus a where a.statusName =:assetStatus")
	AssetStatus getByStatusName(String assetStatus);
	
	
}

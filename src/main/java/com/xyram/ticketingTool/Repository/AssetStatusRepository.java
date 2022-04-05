package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetStatus;

public interface AssetStatusRepository extends JpaRepository<AssetStatus, String>{
	
	
}

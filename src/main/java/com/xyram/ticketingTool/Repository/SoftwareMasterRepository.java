package com.xyram.ticketingTool.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.SoftwareMaster;
@Repository  
@Transactional
public interface SoftwareMasterRepository extends JpaRepository<AssetVendor, String> {

	SoftwareMaster save(SoftwareMaster softwareMaster);
	
//	@Query("select new map(s.softwareId as softwareId,s.softwareName as softwareName," 
// 	+ "s.softwareMasterStatus as softwareMasterStatus) from SoftwareMaster p")
//	

	//SoftwareMaster save(SoftwareMaster softwareMaster);

}

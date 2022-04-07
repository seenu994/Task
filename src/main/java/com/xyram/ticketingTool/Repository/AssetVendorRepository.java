package com.xyram.ticketingTool.Repository;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.AssetVendor;

@Repository  
@Transactional
public interface AssetVendorRepository  extends JpaRepository<AssetVendor, String> {
	
	
	@Query("select new map( p.vendorId as vendorId,p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as email, p.city as city, p.country as country, "
			 + "p.assetVendorStatus as assetVendorStatus) from AssetVendor p") //where p.assetVendorStatus != 'INACTIVE'") 
Page<Map> getAllVendorList(Pageable pageable);

		
	@Query("Select distinct p from AssetVendor p where p.vendorId=:id and p.assetVendorStatus != 'INACTIVE'")
	AssetVendor getVendorById(String id);
	
	
	@Query("Select e.email from AssetVendor e where e.email = :email")
	String filterByEmail(String email);

	@Query("Select distinct p from AssetVendor p where p.vendorId=:vendorId")
	AssetVendor getAssetVendorById(String vendorId);

	

	
//	String filterByEmail(String email);	
//	 @Query("Select distinct new map(p.vendorID as vendorID, p.vendorID asvendorID, p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as Email, p.City as City,Country as Country,"
//			  + "e.Status as Status from AssetVendor p where p.Status != 'INACTIVE'")
	
	//Page<Map> getAllVendorList( Pageable pageable);
//	
//  @Query("Select distinct new map(p.assetVendor as vendorID,  p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as Email, p.city as city,country as country,"
//	  + "e.Status as Status) from AssetVendor p where p.Status != 'INACTIVE'")
//	  <Map> void addAssestVendor(AssetVendor vendor);
	 	 
	
	
	
	
	

	
	


}

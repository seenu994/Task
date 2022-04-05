package com.xyram.ticketingTool.Repository;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.AssetVendor;

@Repository  
@Transactional
public interface AssetVendorRepository  extends JpaRepository<AssetVendor, String> {
	
	
	//@Query("select new map( p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as email, p.city as city, p.country as country, "
			 //+ "p.assetVendorStatus as assetVendorStatus) from AssetVendor p where p.assetVendorStatus != 'INACTIVE'") 
//List<Map> 	findVendorDetailswithVendorId();

		return assetVendor;
//	@Query("Select distinct p from AssetVendor p where p.assetVendor=:id and p.status != 'INACTIVE'")
//	Projects getVendorById(String id);
//	

	
//	String filterByEmail(String email);	
//	 @Query("Select distinct new map(p.vendorID as vendorID, p.vendorID asvendorID, p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as Email, p.City as City,Country as Country,"
//			  + "e.Status as Status from AssetVendor p where p.Status != 'INACTIVE'")
	
	//Page<Map> getAllVendorList(String scopeId, Pageable pageable);
//	
//  @Query("Select distinct new map(p.assetVendor as vendorID,  p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as Email, p.city as city,country as country,"
//	  + "e.Status as Status) from AssetVendor p where p.Status != 'INACTIVE'")
//	  <Map> void addAssestVendor(AssetVendor vendor);
	 	 
	@Query("Select e.email from AssetVendor e where e.email = :email")
	String filterByEmail(String email);
//
//	@Query("Select distinct new map(p.vendorID as vendorID, p.vendorID asvendorID, p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as Email, p.City as City,Country as Country,"
//			  + "e.Status as Status) from AssetVendor p where p.Status != 'INACTIVE'")
  // Page<Map> getAllVendorList(String assetVendor);
//  
	

//Page<Map> getAllVendorList(String scopeId, Pageable pageable);
	

	//@Query("Select distinct new map(p.assetVendor as Id,p.address as address,p.vendorName as vendorname,p.mobileNo as mobileno,p.email as email,p.city as city,p.country as country") from AssetVendor;


	

	
	


}

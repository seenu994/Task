package com.xyram.ticketingTool.Repository;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetVendor;

@Repository  

public interface AssetVendorRepository  extends JpaRepository<AssetVendor, String> {
	
	
//	@Query("select new map( p.vendorId as vendorId,p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as email, p.city as city, p.country as country, "
//			 + "p.assetVendorStatus as assetVendorStatus) from AssetVendor p") //where p.assetVendorStatus != 'INACTIVE'") 
//Page<Map> getAllVendorList(Pageable pageable);

		
//	@Query("select p from AssetVendor p where p.vendorId =:id")
//	AssetVendor getVendorById(String id);

	@Query("select p from AssetVendor p where p.vendorId =:vendorId")
	AssetVendor getVendorById(String vendorId);
	
	@Query("Select e.email from AssetVendor e where e.email = :email")
	String filterByEmail(String email);

	@Query("Select distinct p from AssetVendor p where p.vendorId=:vendorId")
	AssetVendor getAssetVendorById(String vendorId);

	@Query("Select distinct p from AssetVendor p where p.vendorId=:vendorId")
	AssetVendor getVendorById1(String vendorId);

	//Page<Map> getAllVendor(String vendorId, String country, String city, Pageable peageble);

	//AssetVendor getAssetVendorName(String vendorName);

//   @Query("select distinct p from AssetVendor p where P.vendorName=:vendorName")
//   AssetVendor getVendorName(String vendorName);


	


	@Query("select distinct new map( p.vendorId as vendorId, p.vendorName as vendorName, p.mobileNo as mobileNo, "
			+ "p.email as email, p.city as city, p.country as country,p.assetVendorStatus as assetVendorStatus) from AssetVendor p where "
			+ "(:vendorId is null OR p.vendorId =:vendorId) AND"
	        + "(:country is null OR p.country =:country) AND "
            + "(:city is null OR p.city =:city)" )
	 Page<Map> getAllVendor(String vendorId, String country, String city, Pageable peageble);

	
	
	
	@Query("Select distinct p from AssetVendor p where p.vendorName=:vendorName")
	List<AssetVendor> searchVendorName(String vendorName); 
		
	



//	@Query("Select distinct p from AssetVendor p where p.vendorId=:vendorId")
//	AssetVendor getVendorById1(String vendorId);


//	AssetVendor getVendorById(AssetVendor vendorId);

	

	
//	String filterByEmail(String email);	
//	 @Query("Select distinct new map(p.vendorID as vendorID, p.vendorID asvendorID, p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as Email, p.City as City,Country as Country,"
//			  + "e.Status as Status from AssetVendor p where p.Status != 'INACTIVE'")
	
	//Page<Map> getAllVendorList( Pageable pageable);
 
	
	
	
	
	

	
	


}

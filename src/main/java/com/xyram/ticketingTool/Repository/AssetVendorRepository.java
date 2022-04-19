package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetVendor;

@Repository

public interface AssetVendorRepository extends JpaRepository<AssetVendor, String> {

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

	// AssetVendor getAssetVendorName(String vendorName);

//   @Query("select distinct p from AssetVendor p where P.vendorName=:vendorName")
//   AssetVendor getVendorName(String vendorName);

	@Query("select distinct new map( p.vendorId as vendorId, p.vendorName as vendorName, p.mobileNo as mobileNo, "
			+ "p.email as email, p.city as city, p.country as country,p.assetVendorStatus as assetVendorStatus) from AssetVendor p where "
			+ "(:vendorId is null OR p.vendorId =:vendorId) AND" + "(:country is null OR p.country =:country) AND "
			+ "(:city is null OR p.city =:city)")
	Page<Map> getAllVendor(String vendorId, String country, String city, Pageable peageble);

//@Query("Select distinct p from AssetVendor p where p.vendorName=:vendorName")
//	List<AssetVendor> searchVendorName(String vendorName);

	@Query("select distinct p from AssetVendor p where"
			+ "(:searchString is null OR p.vendorName LIKE %:searchString% OR p.vendorId like  %:searchString%) ")

//@Query("select distinct new map (p.vendorId as vendorId, p.vendorName as vendorName,"
//	+"	p.email as email, p.city as city, p.country as country,p.address as address ,"
//	+ "p.mobileNo as mobileNo,p.assetVendorStatus as assetVendorStatus)  from AssetVendor p where "
//    + "(:searchString is null OR p.vendorName LIKE %:searchString% OR p.vendorId LIKE  %:searchString%")
	AssetVendor searchAssetVendor(String searchString);

//@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) from Employee e "
//		+ "where e.status = 'ACTIVE' and e.email like %:searchString% ")

//@Query("Select new map(e.eId as id,e.email as email,e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
//		+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.location as location,e.position as position,e.wings as wings,e.profileUrl as profileUrl) from Employee e "
//		+ "left join e.wings w JOIN Role r On e.roleId = r.Id JOIN  Designation d On e.designationId=d.Id where r.Id !='R1' and (e.email like %:searchString% or e.firstName like %:searchString% or e.middleName like %:searchString% or e.lastName like %:searchString% or d.designationName like %:searchString%  or e.id like %:searchString% "
//		+ "  or e.mobileNumber like %:searchString%  or w.wingName like %:searchString% or e.location like %:searchString%  or e.position like %:searchString%)")
//List<Map> searchEmployee(@Param("searchString") String searchString);

//	@Query("Select distinct p from AssetVendor p where p.vendorId=:vendorId")
//	AssetVendor getVendorById1(String vendorId);

//	AssetVendor getVendorById(AssetVendor vendorId);

//	String filterByEmail(String email);	
//	 @Query("Select distinct new map(p.vendorID as vendorID, p.vendorID asvendorID, p.vendorName as vendorName, p.mobileNo as mobileNo, p.email as Email, p.City as City,Country as Country,"
//			  + "e.Status as Status from AssetVendor p where p.Status != 'INACTIVE'")

	// Page<Map> getAllVendorList( Pageable pageable);

}

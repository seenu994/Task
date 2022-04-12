package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.AssetEmployee;

@Repository
public interface AssetEmployeeRepository extends JpaRepository<AssetEmployee, String>{

	@Query("SELECT a from AssetEmployee a where a.assetId =:assetId")
	AssetEmployee getByassetId(String assetId);

	@Query("SELECT a from AssetEmployee a where a.empId =:empId")
	AssetEmployee getByempId(String empId);

//	 @Query("Select distinct new map(a.empId as empId,a.issuedDate as issuedDate,"		
//	            + "a.bagIssued as bagIssued,a.powercordIssued as powercordIssued,a.mouseIssued as mouseIssued,"
//	    		+ "a.returnDate as returnDate,a.returnType as returnType,"
//	    	    + "a.assetEmployeeStatus as assetEmployeeStatus) from AssetEmployee a where a.assetId =:assetId")
//	AssetEmployee getAssetEmployeeById(String assetId);


	
	
}

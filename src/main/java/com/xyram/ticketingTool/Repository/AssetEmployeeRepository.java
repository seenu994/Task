package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.AssetEmployee;

@Repository
public interface AssetEmployeeRepository extends JpaRepository<AssetEmployee, String>{

	@Query("SELECT a from AssetEmployee a where a.assetId =:assetId")
	AssetEmployee getByAssetId(String assetId);

//	@Query("SELECT a from AssetEmployee a where a.empId =:empId")
//	AssetEmployee getByEmpId(String empId);

	
	@Query("Select distinct new map(e.empId as empId,e.issuedDate as issuedDate,"		
            + "e.bagIssued as bagIssued,e.powercordIssued as powercordIssued,e.mouseIssued as mouseIssued,"
    		+ "e.returnDate as returnDate,e.returnType as returnType,"
    	    + "e.assetEmployeeStatus as assetEmployeeStatus) from AssetEmployee e "
    	    + "where e.assetId =:assetId")
	Page<Map> getAssetEmployeeById(String assetId, Pageable pageable);

	@Query("select e.issuedDate from AssetEmployee e where e.assetId = :assetId")
	Date getIssuedDateById(String assetId);

	


//	 @Query("Select distinct new map(a.empId as empId,a.issuedDate as issuedDate,"		
//	            + "a.bagIssued as bagIssued,a.powercordIssued as powercordIssued,a.mouseIssued as mouseIssued,"
//	    		+ "a.returnDate as returnDate,a.returnType as returnType,"
//	    	    + "a.assetEmployeeStatus as assetEmployeeStatus) from AssetEmployee a where a.assetId =:assetId")
//	AssetEmployee getAssetEmployeeById(String assetId);


	
	
}

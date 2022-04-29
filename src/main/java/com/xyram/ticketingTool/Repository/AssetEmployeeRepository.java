package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.AssetEmployee;
import com.xyram.ticketingTool.enumType.ModuleStatus;

@Repository
public interface AssetEmployeeRepository extends JpaRepository<AssetEmployee, String>{

	@Query("SELECT a from AssetEmployee a where a.assetId =:assetId")
	AssetEmployee getByAssetId(String assetId);

//	@Query("SELECT a from AssetEmployee a where a.empId =:empId")
//	AssetEmployee getByEmpId(String empId);

	
	@Query("Select distinct new map(e.empId as empId,CONCAT(em.firstName ,' ', em.lastName) as EmployeeName,e.issuedDate as issuedDate,"		
            + "e.bagIssued as bagIssued,e.powercordIssued as powercordIssued,e.mouseIssued as mouseIssued,"
    		+ "e.returnDate as returnDate,e.returnType as returnType,"
    	    + "e.assetEmployeeStatus as assetEmployeeStatus) from AssetEmployee e "
    	    + "left join Employee em on em.eId = e.empId where e.assetId =:assetId")
	Page<Map> getAssetEmployeeById(String assetId, Pageable pageable);

	@Query("select e.issuedDate from AssetEmployee e where e.assetEmpId = :assetEmpId")
	Date getIssuedDateById(String assetEmpId);

	@Query("SELECT distinct a from AssetEmployee a where a.empId =:empId AND a.assetEmployeeStatus != 'INACTIVE' ")
	AssetEmployee getEmpById(String empId);
 
	@Query("select distinct e from AssetEmployee e where e.assetId =:assetId AND e.assetEmployeeStatus != 'INACTIVE' ")
	AssetEmployee getAssetById(String assetId);

	@Query("SELECT a from AssetEmployee a where a.assetEmpId =:assetEmpId")
	AssetEmployee getByAssetEmpId(String assetEmpId);

//	 @Query("Select distinct new map(a.empId as empId,a.issuedDate as issuedDate,"		
//	            + "a.bagIssued as bagIssued,a.powercordIssued as powercordIssued,a.mouseIssued as mouseIssued,"
//	    		+ "a.returnDate as returnDate,a.returnType as returnType,"
//	    	    + "a.assetEmployeeStatus as assetEmployeeStatus) from AssetEmployee a where a.assetId =:assetId")
//	AssetEmployee getAssetEmployeeById(String assetId);


	
	
}

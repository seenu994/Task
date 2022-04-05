package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetEmployee;
import com.xyram.ticketingTool.entity.Employee;

@Repository
public interface AssetEmployeeRepository extends JpaRepository<AssetEmployee, String>{

	@Query("SELECT a from AssetEmployee a where a.assetId =:assetId")
	AssetEmployee getByassetId(String assetId);

	@Query("SELECT a from AssetEmployee a where a.empId =:empId")
	AssetEmployee getByempId(String empId);


	
	
}

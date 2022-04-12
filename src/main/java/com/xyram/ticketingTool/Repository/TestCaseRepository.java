package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.TestCases;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCases, String>{
	
	//@Query(value = "Select a.* from TestCases a where a.", nativeQuery = true)
	//List<Map> getTestCasePMHDetails(Pageable pageable, String testCasePMID);
	
	//@Query("from TestCases r inner join fetch r.tCPrjModule where r.tCPrjModule.tcpmId = :id")
	//@Query("select r.ptId as ptid, r.testCaseName as name from TestCases as r inner join fetch r.tCPrjModule where r.tCPrjModule.tcpmId = :id")
	//List<TestCases> getTestCasePMHDetails(Pageable pageable, @Param("id") String id);
	
	@Query("select new map (r.ptId as ptid, r.testCaseName as name, r.status as status, r.owner as owner) from TestCases as r inner join TCProjectModules s on "
			+ "r.tCPrjModule.tcpmId = s.tcpmId where r.tCPrjModule.tcpmId = :id")
	//@Query("select new map (r.ptId as ptid, r.testCaseName as name, r.status as status, r.owner as owner) from TestCases as r inner join fetch r.tCPrjModule where r.tCPrjModule.tcpmId = :id")
	List<Map> getTestCasePMHDetails(Pageable pageable, @Param("id") String id);

}

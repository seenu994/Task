package com.xyram.ticketingTool.Repository;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.enumType.SoftwareEnum;

@Repository  
@Transactional
public interface SoftwareMasterRepository extends JpaRepository<SoftwareMaster , String> {
  
	@Query("select new map(p.softwareId as softwareId,p.softwareName as softwareName," 
	+ "p.softwareMasterStatus as softwareMasterStatus) from SoftwareMaster p where p.softwareMasterStatus != 'INACTIVE'")
	Page<Map> getAllsoftwareMasterList(Pageable peageble);
	
	@Query("Select distinct p from SoftwareMaster p where p.softwareId=:softwareId")
	SoftwareMaster getSoftwareById(String softwareId);

	@Query("Select distinct p from SoftwareMaster p where p.softwareId=:softwareId")
	SoftwareMaster getBysoftId(String softwareId);

	@Query("select p from SoftwareMaster p where p.softwareName = :softwareName")
	SoftwareMaster getBySoftwareMasterName(String softwareName);

	
	@Query("select distinct new map(p.softwareId as softwareId,p.softwareName as softwareName,p.softwareMasterStatus as softwareMasterStatus) from SoftwareMaster p where "
			+ "(:softwareEnum is null OR p.softwareMasterStatus=:softwareEnum)")
	Page<SoftwareMaster> getAllsoftwareMaster(SoftwareEnum softwareEnum, Pageable peageble);

	
		
	@Query("select distinct p from SoftwareMaster p where p.softwareId = :softwareId")
	List<SoftwareMaster> searchsoftwareId(String softwareId);

	

	//SoftwareMaster getById(SoftwareMaster softwareId);

	//SoftwareMaster getsoftwareId(SoftwareMaster softwareId);

	

	//SoftwareMaster save(SoftwareMaster softwareMaster);
	
//	@Query("select new map(s.softwareId as softwareId,s.softwareName as softwareName," 
// 	+ "s.softwareMasterStatus as softwareMasterStatus) from SoftwareMaster p")
//	

	//SoftwareMaster save(SoftwareMaster softwareMaster);

}

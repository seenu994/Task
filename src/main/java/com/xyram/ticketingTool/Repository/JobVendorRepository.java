package com.xyram.ticketingTool.Repository;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.JobVendorDetails;
@Repository
@Transactional
public interface JobVendorRepository extends JpaRepository<JobVendorDetails, String> {

	
	@Query("select e from JobVendorDetails e left join User u On e.userCredientials.id=u.id where e.userCredientials.id= :userId ")

	JobVendorDetails getVendorByUserIs(String userId);

}

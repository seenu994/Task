package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xyram.ticketingTool.entity.CompanyWings;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.enumType.JobOpeningStatus;

@Repository
@Transactional
public interface JobRepository extends CrudRepository<JobOpenings, Long>, JpaSpecificationExecutor<JobOpenings> {

//	@Query(value = "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.last_updated_at as last_updated_at, "
//			+ "a.created_by as created_by, a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp "
//			+ "FROM ticketdbtool.ticket a "
//			+ "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
//			+ "left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
//			+ "left join ticketdbtool.employee e on b.employee_id = e.employee_id "
//			+ "where a.ticket_description like %:searchString% ", nativeQuery = true)
	@Query(value = "SELECT * FROM ticketdbtool.job_openings jo", nativeQuery = true)
	List<Map> getAllJobOpenings();

	
	
	
	@Query(value = "SELECT cw.wing_id, cw.wing_name FROM ticketdbtool.company_wings cw", nativeQuery = true)
	List<Map> getAllCompanyWings();

	@Query(value = "SELECT ts.skill_id, ts.skill_name FROM ticketdbtool.tech_skils ts", nativeQuery = true)
	List<Map> getAllTechSkills();

	@Query(value = "SELECT * from ticketdbtool.job_openings jo where jo.job_code = :jobCode ", nativeQuery = true)
	JobOpenings getJobOpeningFromCode(String jobCode);

	@Query(value = "SELECT jo.job_code as jobCode,jo.job_title as jobTitle,jo.job_description as jobDescription,jo.notify_vendor as notifyVendor"
			+ "jo.job_skills as jobSkills,jo.total_openings as jobOpenings,jo.filled_positions as jobPositions,"
			+ "jo.min_exp as jobMinExperience,jo.max_exp as jobMaxExperience,jo.wing_id as jobWing,jo.status as jobstatus,"
			+ "jo.salary as jobSalary,jo.created_at as jobCreatedAt,jo.created_by as jobCreatedBy,jo.last_updated_at as jobLastUpdatedAt,"
			+ "jo.updated_by as jobLastUpdatedBy,e.frist_name as name, u.user_role as role,"
			+ "w.wing_name as wingName from ticketdbtool.job_openings jo left join user u ON jo.created_by = u.user_id"
			+ " left join employee e ON e.user_id = u.user_id left join company_wings w ON jo.wing_id = w.wing_id  where jo.job_id = :jobOpeningId ", nativeQuery = true)
	Map getJobOpeningById(String jobOpeningId);

	@Query(value = "SELECT * from ticketdbtool.job_openings jo where jo.job_id = :jobOpeningId ", nativeQuery = true)
	JobOpenings getById(String jobOpeningId);

	@Query(value = "SELECT * from ticketdbtool.job_openings jo where jo.job_id = :jobOpeningId ", nativeQuery = true)
	Map getJobById(String jobOpeningId);
	
	@Query(value = "SELECT jo from JobOpenings jo")
	List<JobOpenings> getList();

	@Query(value = "SELECT new map(jo.jobCode as jobCodes,jo.Id as  id ) from JobOpenings jo where "
			+ "((:userRole!='JOB_VENDOR') or (:userRole='JOB_VENDOR' and TRUE =(jo.notifyVendor))) and "
			+ "jo.jobStatus NOT In ('COMPLETED','CANCELLED')")
	List<Map> getAllJobCodes(String userRole);

	@Query(value = "SELECT j from JobOpenings j WHERE j.jobCode =:jobCode ")
	JobOpenings getJobCode(String jobCode);
	
	@Query(value = "SELECT new map(jo.jobCode as jobCodes,jo.Id as  id,jo.jobTitle as jobTitle ) from JobOpenings jo "
			+ "WHERE lower(jo.jobTitle) LIKE %:searchString% AND jo.jobStatus=:status ")
	List<Map> searchJobOpenings(String searchString, JobOpeningStatus status);

//	public List<JobOpenings> findByCriteria(String employeeName){
//        return employeeDAO.findAll(new Specification<Employee>() {
//            @Override
//            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicates = new ArrayList<>();
//                if(employeeName!=null) {
//                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employeeName"), employeeName)));
//                }
//                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        });
//	}
	@Query(value = "SELECT  cw.wing_name FROM ticketdbtool.company_wings cw", nativeQuery = true)
	Map GetJobWingName();

	/*
	 * @Query("select new map(jo.createdBy as createdBy) from JobApplication jo left join Employee e On jo.createdBy=e.eId and e.userCredientials.uid as uid where jo.createdBy=e.eId "
	 * ) List<Map> getCreadtedBy();
	 */
	@Query(value = "SELECT cw.wing_id, cw.wing_name FROM ticketdbtool.company_wings cw", nativeQuery = true)
	CompanyWings getWingById(String id);

	@Query("select count(j)>0 from JobOpenings j where j.jobCode= :jobCode")
	boolean findb(String jobCode);

	@Query(value = "SELECT j from JobOpenings j WHERE j.jobCode =:jobCode ")
	JobOpenings getJobCodeValidation(String jobCode);
  
	@Query(value = "SELECT j from JobOpenings j WHERE j.Id =:id ")
	JobOpenings getJobOpeningsById(String id);



	@Query(value = " SELECT new map(j.Id as Id, j.jobDescription as jobDescription , j.jobCode as jobCode, j.jobTitle as jobTitle , "
			+ "j.jobSkills as jobSkills , w.wingName as wingName, j.jobCode as jobCode, j.maxExp as maxExp, j.minExp as minExp, j.totalOpenings as totalOpenings ,"
			+ " j.filledPositions as filledPositions, j.jobStatus as jobStatus,j.jobSalary as jobSalary) from JobOpenings "
			+ " j left join j.wings as w  where"
			+ " (:wing is null or  lower(w.wingName)=:wing ) and "
			+ "(:status is null or j.jobStatus=:status) and "
			+ "(:userRole is null   Or (:userRole!='JOB_VENDOR' ) OR"
			+ " (:userRole='JOB_VENDOR' and TRUE =(j.notifyVendor ) ))and "
			+ " (:searchString is null  "
			+ " Or lower(j.jobTitle) LIKE %:searchString% "
			+ " Or lower(j.jobSkills) LIKE %:searchString% "
			+"  Or lower(j.jobCode) Like %:searchString%"
			+"  Or lower(w.wingName) Like %:searchString%"
			+"  Or lower(j.jobDescription) Like %:searchString%"
			+"  Or lower(j.jobCode) Like %:searchString%"
			+"  Or lower(j.maxExp) Like %:searchString%"
			+ " Or lower(j.minExp) LIKE %:searchString%) ORDER BY j.createdAt DESC")		
	Page<List<Map>> getAllOpenings(String searchString, JobOpeningStatus status, String wing, String userRole,
		 Pageable pageable);
	
	@Query(value = " SELECT new map(j.Id as Id, j.jobDescription as jobDescription , j.jobCode as jobCode, j.jobTitle as jobTitle , "
			+ "j.jobSkills as jobSkills , w.wingName as wingName, j.jobCode as jobCode, j.maxExp as maxExp, j.minExp as minExp, j.totalOpenings as totalOpenings ,"
			+ " j.filledPositions as filledPositions, j.jobStatus as jobStatus) from JobOpenings "
			+ " j left join j.wings as w  where"
			+ " (:wing is null or  lower(w.wingName)=:wing ) and "
			+ "(:status is null or j.jobStatus=:status) and "
			+ "(:userRole is null   Or (:userRole!='JOB_VENDOR' ) OR"
			+ " (:userRole='JOB_VENDOR' and TRUE =(j.notifyVendor ) ))and "
			+ " (:searchString is null  "
			+ " Or lower(j.jobTitle) LIKE %:searchString% "
			+ " Or lower(j.jobSkills) LIKE %:searchString% "
			+"  Or lower(j.jobCode) Like %:searchString%"
			+"  Or lower(w.wingName) Like %:searchString%"
			+"  Or lower(j.jobDescription) Like %:searchString%"
			+"  Or lower(j.jobCode) Like %:searchString%"
			+"  Or lower(j.maxExp) Like %:searchString%"
			+ " Or lower(j.minExp) LIKE %:searchString%) ORDER BY j.createdAt DESC")		
	Page<List<Map>> getAllOpeningsWithoutPackage(String searchString, JobOpeningStatus status, String wing, String userRole,
		 Pageable pageable);



	@Query(value = "SELECT j.jobCode from JobOpenings j WHERE j.Id =:jobId ")
	String getJobCodeById(String jobId);
	
	
	

	
	
}

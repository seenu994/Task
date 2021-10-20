package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.xyram.ticketingTool.entity.JobOpenings;


@Repository
@Transactional
public interface JobRepository extends CrudRepository<JobOpenings,Long>,JpaSpecificationExecutor<JobOpenings>{
	
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
	
	@Query(value = "SELECT jo.job_code as jobCode,jo.job_title as jobTitle,jo.job_description as jobDescription,jo.job_skills as jobSkills,jo.total_openings as jobOpenings,jo.filled_positions as jobPositions,jo.min_exp as jobMinExperience,jo.max_exp as jobMaxExperience,jo.wing_id as jobWing,jo.status as jobstatus,jo.salary as jobSalary,jo.created_at as jobCreatedAt,jo.created_by as jobCreatedBy,jo.last_updated_at as jobLastUpdatedAt,jo.updated_by as jobLastUpdatedBy from ticketdbtool.job_openings jo where jo.job_id = :jobOpeningId ", nativeQuery = true)
	Map getJobOpeningById(String jobOpeningId);
	
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

}

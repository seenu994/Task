package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Module;

@Repository
public interface ModuleRespository extends JpaRepository<Module, String > {

	@Query(value="select m from Module m where m.moduleId=:moduleId")
	Module getbyModuleId(Integer moduleId);

}

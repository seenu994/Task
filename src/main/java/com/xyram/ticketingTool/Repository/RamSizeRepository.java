package com.xyram.ticketingTool.Repository;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Brand;
import com.xyram.ticketingTool.entity.RamSize;

@Repository
@Transactional
public interface RamSizeRepository extends JpaRepository<RamSize, String> {

	
    @Query("Select r from RamSize r where r.ramId =:ramId")
	RamSize getRamById(String ramId);

    @Query("Select distinct new map(r.ramId as ramId, r.ramSize as ramSize, r.ramStatus as ramStatus) from RamSize r")
	Page<Map> getAllRam(Pageable pageable);

    @Query("Select r from RamSize r where r.ramSize =:ram")
	RamSize getRamSize(String ram);

    @Query("Select r.ramId from RamSize r where r.ramSize =:ramSize")
	String getRamId(String ramSize);

    

	

}

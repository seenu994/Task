package com.xyram.ticketingTool.Repository;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Brand;

@Repository
@Transactional
public interface BrandRepository extends JpaRepository<Brand, String>{

	@Query("Select b from Brand b where b.brandId =:brandId")
	Brand getBrandById(String brandId);
	

	@Query("Select distinct new map(b.brandId as brandId, b.brandName as brandName, b.brandStatus as brandStatus) from Brand b")
	Page<Map> getAllBrand(Pageable pageable);

	@Query("Select b from Brand b where b.brandName =:brand")
	Brand getBrand(String brand);
	
	@Query("Select b.brandId from Brand b where b.brandName =:brandName")
	String getBrandName(String brandName);

	@Query("Select b.brandName from Brand b where b.brandId =:brandId")      
	String getBrandNameById(String brandId);
	
	

}

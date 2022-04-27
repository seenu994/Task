package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Brand;

public interface BrandService {

	ApiResponse addbrand(Brand brand);

	ApiResponse editbrand(Brand brand, String brandId);

	ApiResponse getAllBrand(Pageable pageable);

	ApiResponse deleteBrand(String brandId);

}

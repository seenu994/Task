package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.Brand;
import com.xyram.ticketingTool.service.BrandService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController
public class BrandController {
	private final Logger logger = LoggerFactory.getLogger(BrandController.class);
	
	@Autowired
	BrandService brandService;
	
	@PostMapping("/addBrand")
	public ApiResponse addBrand(@RequestBody Brand brand) {
		logger.info("Received request to add Brand");
		return brandService.addbrand(brand);
	}
	
	@PutMapping("/editBrand/{brandId}")
    public ApiResponse editBrand(@RequestBody Brand brand, @PathVariable String brandId) {
        logger.info("Received request to edit Brand");
        return brandService.editbrand(brand, brandId);
	}
        
     @GetMapping("/getAllBrand")
      public ApiResponse getAllBrand(Pageable pageable) {
  	        logger.info("Received request to get all Brand");
  			return brandService.getAllBrand(pageable);
  	 }
     
    @DeleteMapping("/deleteBrand/{brandId}")
 	ApiResponse deleteBrand(@PathVariable String brandId) {
 		logger.info("Received request to delete brand");
 		return brandService.deleteBrand(brandId);
 	}
    
    @GetMapping("/searchBrand/{searchString}")
	public ApiResponse searchBrand(@PathVariable String searchString) {
		logger.info("Received request to search Brand");
		return brandService.searchBrand(searchString);
	}

}
	


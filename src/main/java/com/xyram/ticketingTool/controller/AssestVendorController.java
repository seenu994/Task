package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.enumType.AssetVendorEnum;
import com.xyram.ticketingTool.service.AssetvendorService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class AssestVendorController {

	private final Logger logger = LoggerFactory.getLogger(AssestVendorController.class);

	@Autowired
	AssetvendorService assetvendorService;

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createVendor",
			AuthConstants.INFRA_USER_BASEPATH + "/createVendor", AuthConstants.INFRA_ADMIN_BASEPATH + "/createVendor" })
	public ApiResponse addAssestVendor(@RequestBody AssetVendor vendor) {
		logger.info("Received request to add Asset vendor");
		return assetvendorService.addAssestVendor(vendor);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editVendorById/{vendorId}",
			AuthConstants.INFRA_USER_BASEPATH + "/editVendorById/{vendorId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editVendorById/{vendorId}" })
	public ApiResponse editassetVendor(@RequestBody AssetVendor AssetVendorRequest, @PathVariable String vendorId) {
		logger.info("Received request to edit Asset vendor");

		return assetvendorService.editassetVendor(AssetVendorRequest, vendorId);
	}

//	
//	@GetMapping(value = "/getAllVendorList")
//	public ApiResponse getAllVendorList(Pageable pageable) {
//		logger.info("Received request to getAllVendorList");
//		return assetvendorService.getAllVendorList(pageable);
//	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllVendor",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllVendor",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllVendor" })
	public ApiResponse getAllVendor(@RequestParam Map<String, Object> filter, Pageable pageable) {
		// System.out.println(filter);
		logger.info("Received request to getAllAssetVendorList");
		return assetvendorService.getAllVendor(filter, pageable);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getVendorById/{vendorId}",
			AuthConstants.INFRA_USER_BASEPATH + "/getVendorById/{vendorId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getVendorById/{vendorId}" })
	public ApiResponse getVendorById(@PathVariable String vendorId) {
		logger.info("Received request to get Vendor by Id");
		return assetvendorService.getVendorById(vendorId);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/changeStatus/{vendorId}/{assetVendorEnum}",
			AuthConstants.INFRA_USER_BASEPATH + "/changeStatus/{vendorId}/{assetVendorEnum}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/changeStatus/{vendorId}/{assetVendorEnum}" })
	public ApiResponse editassetVendorStatus(@PathVariable String vendorId,
			@PathVariable AssetVendorEnum assetVendorEnum) {
		logger.info("Received request to change vendor status to: " + assetVendorEnum + "for vendorId: " + vendorId);
		return assetvendorService.updateassetVendorStatus(vendorId, assetVendorEnum);
	}

	/*
	 * @GetMapping(value = { AuthConstants.ADMIN_BASEPATH +
	 * "/searchVendor/{vendorName}", AuthConstants.INFRA_ADMIN_BASEPATH +
	 * "/searchVendor/{vendorName}"}) public ApiResponse
	 * searchVendorName(@PathVariable String vendorName) {
	 * logger.info("Received request to search VendorName"); return
	 * assetvendorService.searchVendorName(vendorName); }
	 */

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchAssetVendor/{searchString}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/searchAssetVendor/{searchString}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/searchAssetVendor/{searchString}" })
	public ApiResponse searchAssetVendor(@PathVariable String searchString) {
		logger.info("Received request to search vendor ");
		return assetvendorService.searchAssetVendor(searchString);
	}

}

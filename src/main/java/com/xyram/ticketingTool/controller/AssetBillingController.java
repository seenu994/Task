package com.xyram.ticketingTool.controller;

import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.request.AssetBillingRequest;
import com.xyram.ticketingTool.service.AssetBillingService;
import com.xyram.ticketingTool.util.AuthConstants;



@RestController
@CrossOrigin
public class AssetBillingController 
{
    private final Logger logger = LoggerFactory.getLogger(AssetBillingController.class);
    
	@Autowired
	AssetBillingService assetBillingService;
	
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addPurchaseAssetBill",
			AuthConstants.INFRA_USER_BASEPATH + "/addPurchaseAssetBill",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/addPurchaseAssetBill"})
	public ApiResponse addPurchaseAssetBill(@RequestBody AssetBilling assetBilling)
	{
		logger.info("received request to add assetBilling");
		return assetBillingService.addPurchaseAssetBill(assetBilling);
	}
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editPurchaseAssetBill/{assetBillId}",
			AuthConstants.INFRA_USER_BASEPATH + "/editPurchaseAssetBill/{assetBillId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editPurchaseAssetBill/{assetBillId}"})
	public ApiResponse editPurchaseAssetBill(@RequestBody AssetBilling assetBilling, @PathVariable String assetBillId)
	{
		logger.info("received request to edit asset purchase bill");
		return assetBillingService.editPurchaseAssetBill(assetBilling, assetBillId);
	}
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addRepairAssetBill",
			AuthConstants.INFRA_USER_BASEPATH + "/addRepairAssetBill",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/addRepairAssetBill"})
	public ApiResponse addRepairAssetBill(@RequestBody AssetBilling assetBilling)
	{
		logger.info("received request to add assetBilling");
		return assetBillingService.addRepairAssetBill(assetBilling);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editRepairAssetBill/{assetBillId}",
			AuthConstants.INFRA_USER_BASEPATH + "/editRepairAssetBill/{assetBillId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editRepairAssetBill/{assetBillId}"})
	public ApiResponse editRepairAssetBill(@RequestBody AssetBilling assetBilling, @PathVariable String assetBillId)
	{
		logger.info("received request to edit asset purchase bill");
		return assetBillingService.editRepairAssetBill(assetBilling, assetBillId);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/returnFromRepair/{assetBillId}",
			AuthConstants.INFRA_USER_BASEPATH + "/returnFromRepair/{assetBillId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/returnFromRepair/{assetBillId}"})
	public ApiResponse returnFromRepair(@RequestBody AssetBilling assetBilling, @PathVariable String assetBillId)
	{
		logger.info("received request to return from repair");
		return assetBillingService.returnFromRepair(assetBilling,assetBillId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllAssetBillingByAssetId/{assetId}",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllAssetBillingByAssetId/{assetId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllAssetBillingByAssetId/{assetId}"})
    public ApiResponse getAllAssetBillingByAssetId(@PathVariable String assetId) {
	        logger.info("Received request to get Asset Billing by Id");
			return assetBillingService.getAllAssetBillingByAssetId(assetId);
	}
    
}





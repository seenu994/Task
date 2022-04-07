package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	/*@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createAssetBilling"})
	public ApiResponse addAssetBilling(@RequestBody AssetBilling assetBilling)
	{
		logger.info("received request to add assetBilling");
		return assetBillingService.addAssetBilling(assetBilling);
	}*/
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createPurchaseAssetBill"})
	public ApiResponse addPurchaseAssetBill(@ModelAttribute AssetBillingRequest assetBilling)
	{
		logger.info("received request to add assetBilling");
		System.out.println("assetBill");
		return assetBillingService.addPurchaseAssetBill(assetBilling);
	}
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editPuchaseAssetBill"})
	public ApiResponse editPurchaseAssetBill(@ModelAttribute AssetBillingRequest assetBilling)
	{
		logger.info("received request to edit asset purchase bill");
		return assetBillingService.editPurchaseAssetBill(assetBilling);
	}
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createRepairAssetBill"})
	public ApiResponse addRepairAssetBill(@ModelAttribute AssetBillingRequest assetBilling)
	{
		logger.info("received request to add assetBilling");
		return assetBillingService.addRepairAssetBill(assetBilling);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editRepairAssetBill"})
	public ApiResponse editRepairAssetBill(@ModelAttribute AssetBillingRequest assetBilling)
	{
		logger.info("received request to edit asset purchase bill");
		return assetBillingService.editRepairAssetBill(assetBilling);
	}
	/*
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/returnFromRepair" })
	public ApiResponse returnFromRepair(@PathVariable String assetBillId)
	{
		logger.info("received request to return from repair");
		return assetBillingService.returnFromRepair(assetBillId);
	}
	
	/*@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllAssetBillingList"})
    public ApiResponse getAllAssetBillingList (Pageable pageable) {
	        logger.info("inside AssetContoller :: getAllAssets");
			return assetBillingService.getAllAssetBillingList(pageable);
	}*/
	
}





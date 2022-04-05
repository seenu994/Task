package com.xyram.ticketingTool.service.impl;

//import java.awt.print.Pageable;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetBillingRepository;
import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.admin.model.User;
//import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
//import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.CompanyWings;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.service.AssetBillingService;
import com.xyram.ticketingTool.service.AssetIssuesService;
import com.xyram.ticketingTool.service.AssetvendorService;
//import com.xyram.ticketingTool.service.AssetIssuesService;
//import com.xyram.ticketingTool.service.AssetvendorService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssetBillingServiceImpl implements AssetBillingService
{
	private static final Logger logger = LoggerFactory.getLogger(AssetBillingServiceImpl.class);

	@Autowired
	AssetBillingRepository  assetBillingRepository;
	
	@Autowired
	AssetRepository  assetRepository;
	
	@Autowired
	AssetBillingService assetBillingService;
	
	@Autowired
	AssetvendorService assetVendorService;
	
	@Autowired
	AssetVendorRepository assetVendorRepository;
	
	@Autowired
	AssetIssuesService assetIssuesService;

	@Autowired
	AssetIssuesRepository assetIssuesRepository;
	
	//String billingType[] = {"purchase", "repair"};
	
	public ApiResponse addPurchaseAssetBill(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse();
		response = validateAssetBilling(assetBilling);
		
		System.out.println(assetBilling);
		if(response.isSuccess())
		{
			if(assetBilling != null)
			{
				assetBilling.setAssetIssues(null);
			    assetBilling.setUnderWarrenty(null);
			    assetBilling.setReturnDate(null);
				assetBillingRepository.save(assetBilling);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_PURCHASE_BILL_ADDED_SUCCESSFULLY);
			}
		
		}
		return response;
	}


	private ApiResponse validateAssetBilling(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse(false);
		 //validate asset id
		 if(assetBilling.getAsset() == null)
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory !!");
		 }
		 else
		 {
			 Asset asset = getAssetById(assetBilling.getAsset().getAssetId());
			 if(asset.getAssetId() == null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id not valid!!!");
			 }
			 else {
				   assetBilling.setAsset(asset);
				 }
		 }
		 
		 //validate vendor id
		 if(assetBilling.getAssetVendor() == null)
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandatory !!");
		 }
		 else
		 {
			 AssetVendor assetVendor = assetVendorRepository.getById(assetBilling.getAssetVendor().getVendorId());
			 if(assetVendor.getVendorId() == null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid asset vendor");
			 }
			 else {
			   assetBilling.setAssetVendor(assetVendor);	 
			 }
		 }
		 //validate billingType
		 //if(assetBilling.getBillingType().equals("purchase"))
		 //{
			 
			    //assetBilling.setAssetIssues(null);
			    //assetBilling.setUnderWarrenty(null);
			    //assetBilling.setReturnDate(null);
//			     
		 //}
		
		 //validate billingtype
		 if(assetBilling.getBillingType() == null)
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "billing type is mandatory");
		 }
		
		//validate billphotourl
		if(assetBilling.getBillPhotoUrl() == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bill photo url is mandatory");
		}
		
		 
		
	   //validate transactionDate
		 if(assetBilling.getTransactionDate() == null)
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "transaction date is mandatory !!");
		 }
//		 else
//		 {
//			Asset asset = AssetRepository.getPurchaseDate(assetBilling.getPurchaseDate());
//			if(asset.getPurchaseDate() == null)
//			{
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "transaction date is not valid!!!");
//			}
//		 }
		 
		
		response.setSuccess(true);
		return response;
	
}
	private ApiResponse validateAssetIssue(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse(false);
		if(assetBilling.getAssetIssues() == null)
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "issue id is mandatory !!");
		 }
		else
		{
			 AssetIssues assetIssues = assetIssuesRepository.getById(assetBilling.getAssetIssues().getAssetIssueId());
			 if(assetIssues== null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid asset issue");
			 }
			 else {
			   assetBilling.setAssetIssues(assetIssues);	 
			 }
		}
		return response;
	}
	
	private boolean validateUnderWarrenty(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse();
		//validate underWarrenty
		 if(assetBilling.getUnderWarrenty() == true)
		 {
            //AssetBilling assetBilling2 = AssetBillingRepository.getUnderWarrenty(assetBilling.getUnderWarrenty());
            assetBilling.setAssetAmount(null);
            assetBilling.setGstAmount(null);
            assetBilling.setAmountPaid(null);
		 }
		 return true;
		 
	}


	@Override
	public ApiResponse getAllAssestVendor(String vendorID) {
		ApiResponse response = new ApiResponse();
		return response;

	}
	
	
	public Asset getAssetById(String aId) {
		
		return assetRepository.getById(aId);
		
	}
   public AssetBilling getAssetAmount(Integer assetAmount) {
		
		return assetBillingRepository.getAssetAmount(assetAmount);
		
	}
	public AssetVendor getVendorId(String vendorId) {
		// TODO Auto-generated method stub
		return assetVendorRepository.getById(vendorId);
	}
	
	
	public ApiResponse editPurchaseAssetBill(AssetBilling assetBilling) 
	{

		ApiResponse response = new ApiResponse();
		 AssetBilling assetBillingObj = assetBillingRepository.getById(assetBilling.getAssetBillId());
		 if(assetBillingObj != null)
		 {
			 response = validateAssetBilling(assetBilling);
             if (response.isSuccess()) 
			 {
				assetBilling.setAssetBillId(assetBilling.getAssetBillId());
				//assetBilling.setaId(assetBilling.getaId());
				assetBilling.setBillingType(assetBilling.getBillingType());
				assetBilling.setAssetAmount(assetBilling.getAssetAmount());
				assetBilling.setGstAmount(assetBilling.getGstAmount());
				assetBilling.setTransactionDate(assetBilling.getTransactionDate());
				//assetBilling.setAssetVendor(assetBilling.getAssetVendor());
				assetBilling.setBillPhotoUrl(assetBilling.getBillPhotoUrl());
				assetBilling.setAmountPaid(assetBilling.getAmountPaid());
				
				
				AssetBilling assetBillingAdded = assetBillingRepository.save(assetBilling);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_PURCHASE_BILL_EDIT_SUCCESSFULLY);
//				Map content = new HashMap();
//			
//				content.put("id", assetBilling.getId());
//				
//				
//				response.setContent(content);
//				System.out.println("message ->"+response.getMessage());
			} 
		 }
             else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ID_INVALID);
				//response.setContent(null);
			}
		return response;
	}	


	@Override
	public ApiResponse addRepairAssetBill(AssetBilling assetBilling) {
		ApiResponse response = new ApiResponse();
		response = validateAssetBilling(assetBilling);
        if(response.isSuccess())
        {
			validateAssetIssue(assetBilling);
			validateUnderWarrenty(assetBilling); 
			if(assetBilling != null)
			{
				assetBillingRepository.save(assetBilling);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_REPAIR_BILL_ADDED_SUCCESSFULLY);
			}
		
        }
		return response;
		
	}


	@Override
	public ApiResponse editRepairAssetBill(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse();
		AssetBilling assetBillingObj = assetBillingRepository.getById(assetBilling.getAssetBillId());
		 if(assetBillingObj != null)
		 {
		   response = validateAssetBilling(assetBilling);
		
		  if(response.isSuccess())
		  {
				assetBilling.setAssetBillId(assetBilling.getAssetBillId());
				//assetBilling.setaId(assetBilling.getaId());
				//assetBilling.setBillingType(assetBilling.getBillingType());
				//assetBilling.setAssetIssues(assetBilling.getAssetIssues());
				response = validateAssetIssue(assetBilling);
				assetBilling.setAssetIssues(assetBilling.getAssetIssues());
				assetBilling.setUnderWarrenty(assetBilling.getUnderWarrenty());
				assetBilling.setTransactionDate(assetBilling.getTransactionDate());
				assetBilling.setAssetVendor(assetBilling.getAssetVendor());
				

				assetBilling.setBillPhotoUrl(assetBilling.getBillPhotoUrl());
				assetBilling.setAmountPaid(assetBilling.getAmountPaid());
				
				
				AssetBilling assetBillingAdded = assetBillingRepository.save(assetBilling);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_REPAIR_BILL_EDITED_SUCCESSFULLY);
				Map content = new HashMap();
			
				content.put("Id", assetBilling.getAssetBillId());
				//content.put("assetVendor", assetBilling.getAssetVendor());
				
				
				response.setContent(content);
				System.out.println("message ->"+response.getMessage());
			} 
		 }
		 else 
		 {
				response.setSuccess(false);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid billing id !!");
				//response.setMessage(ResponseMessages.ID_INVALID);
				//response.setContent(null);
			}
		return response;
	}


	@Override
	public ApiResponse returnFromRepair(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse(false);
		response = validateAssetBilling(assetBilling);
		if(response.isSuccess())
		{
		   response = validateAssetIssue(assetBilling);
	       
			if(assetBilling != null)
			{
				assetBillingRepository.save(assetBilling);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.RETURN_FROM_REPAIR);
			}
		}
		return response;
	}


	@Override
	public ApiResponse getAllAssetBillingList() 
	{
		List<Map> assetBillingList = assetBillingRepository.getAllAssetBilling();
		Map content = new HashMap();
		content.put("assetBillingList", assetBillingList);
		ApiResponse response = new ApiResponse(true);
		
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
   







	

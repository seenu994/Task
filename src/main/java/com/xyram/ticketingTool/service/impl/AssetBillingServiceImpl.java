package com.xyram.ticketingTool.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyram.ticketingTool.Repository.AssetBillingRepository;
import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.AssetVendorRepository;
//import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
//import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.request.AssetBillingRequest;
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
	
	//String billingType[] = {"purchase", "repair", "return"};
	@Override
	public ApiResponse addPurchaseAssetBill(AssetBillingRequest assetBilling) 
	{
		ApiResponse response = new ApiResponse();
		ObjectMapper mapper = new ObjectMapper();
		//assetBilling = validateAssetBilling(assetBilling);
		AssetBilling assetBillingObj = new AssetBilling() ;
		
		try
		{
			assetBillingObj  = mapper.readValue(assetBilling.getAssetBilling(), AssetBilling.class);
		}
		catch (JsonMappingException e) 
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	    catch (JsonProcessingException e) 
		{
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());



		}
		catch (Exception e) {
		// TODO Auto-generated catch block
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		response = validateAssetBilling(assetBillingObj);
		assetBillingObj = assetBillingRepository.save(assetBillingObj);
		
		
		if(response.isSuccess())
		{
		   //System.out.println(assetBilling);
			if(assetBilling != null)
			{
				//assetBilling.setAssetIssueId(null);
			    //assetBilling.setUnderWarrenty(null);
			    //assetBilling.setReturnDate(null);
				//assetBillingRepository.save(assetBilling);
				assetBillingObj = assetBillingRepository.save(assetBillingObj);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_PURCHASE_BILL_ADDED_SUCCESSFULLY);
			}
		}
		return response;
	}


	public ApiResponse validateAssetBilling(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse(false);
		 //validate asset id
		
		 if(assetBilling.getAssetId() == null || assetBilling.getAssetId().equals(""))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory !!");
		 }
		 else
		 {
			 Asset asset = assetRepository.getByassetId(assetBilling.getAssetId());
			 if(asset == null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id not valid!!!");
			 }
//			 else {
//				   assetBilling.setAsset(asset);
//				 }
		 }
		 
		 //validate vendor id
		 if(assetBilling.getVendorId() == null || assetBilling.getVendorId().equals(" "))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandatory !!");
		 }
		 else
		 {
			 AssetVendor assetVendor = assetVendorRepository.getAssetVendorById(assetBilling.getVendorId());
			 if(assetVendor == null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid vendor id");
			 }
			 //else {
			   //assetBilling.setVendorId(vendorId);	 
			 //}
		 }
		
		 //validate billingtype
		 if(assetBilling.getBillingType() == null)
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "billing type is mandatory");
		 }
		
		//validate billphotourl

		
		 
		
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
	private ApiResponse validateAssetIssueId(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse(false);
		if(assetBilling.getAssetIssueId() == null || assetBilling.getAssetIssueId().equals(" "))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "issue id is mandatory !!");
		 }
		else
		{
			 AssetIssues assetIssues = assetIssuesRepository.getAssetIssueById(assetBilling.getAssetIssueId());
			 if(assetIssues== null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid asset issue");
			 }
			 //else {
			   //assetBilling.setAssetIssuesId(assetIssuesId);	 
			 //}
		}
		return response;
	}
	
	private boolean validateUnderWarrenty(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse();
		//validate underWarrenty
		//AssetBilling assetBillingObj = new AssetBilling() ;
		 if(assetBilling.getUnderWarrenty() == true)
		 {
            //AssetBilling assetBilling2 = AssetBillingRepository.getUnderWarrenty(assetBilling.getUnderWarrenty());
            /*assetBillingObj.setAssetAmount(null);
            assetBillingObj.setGstAmount(null);
            assetBillingObj.setAmountPaid(false);*/
			 response.setMessage(ResponseMessages.ASSET_IS_UNDERWARRENTY_NO_NEED_TO_PAY_ANY_AMOUNT);
			 assetBilling.setAmountPaid(false);
			 
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

    @Override
	public ApiResponse editPurchaseAssetBill(AssetBillingRequest assetBilling) 
	{
    	ApiResponse response = new ApiResponse();
		ObjectMapper mapper = new ObjectMapper();
		//assetBilling = validateAssetBilling(assetBilling);
		AssetBilling assetBillingObj = new AssetBilling() ;
		
		try
		{
			assetBillingObj  = mapper.readValue(assetBilling.getAssetBilling(), AssetBilling.class);
		}
		catch (JsonMappingException e) 
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	    catch (JsonProcessingException e) 
		{
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());



		}
		catch (Exception e) {
		// TODO Auto-generated catch block
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		//assetBillingObj = assetBillingRepository.save(assetBillingObj);
		//System.out.println("assetBillingObj.getAssetBillId() - " + assetBillingObj.getAssetBillId());
		
		AssetBilling assetBillingObject = assetBillingRepository.getAssetBillId(assetBillingObj.getAssetBillId());
		 //System.out.println("assetBillingObject.getAssetBillId() - " + assetBillingObject.getAssetId());
		 
		 if(assetBillingObject != null)
		 {
			 //System.out.println("IN IF");   
			 assetBillingObj.setAssetBillId(assetBillingObject.getAssetBillId());
			     /*response= validateAssetBilling(assetBillingObj);
				if(response.isSuccess())
				{
					assetBillingObject.setAssetId(assetBillingObj.getAssetId());
					assetBillingObject.setVendorId(assetBillingObj.getVendorId());
					assetBillingObject.setAssetAmount(assetBillingObj.getAssetAmount());
					assetBillingObject.setGstAmount(assetBillingObj.getGstAmount());
					assetBillingObject.setTransactionDate(assetBillingObj.getTransactionDate());
					assetBillingObject.setBillPhotoUrl(assetBillingObj.getBillPhotoUrl());
					assetBillingObject.setAmountPaid(assetBillingObj.getAmountPaid());
				
					assetBillingObject = assetBillingRepository.save(assetBillingObj);
				//AssetBilling assetBillingObject = assetBillingRepository.save(assetBilling);

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
				
				//response.setMessage(ResponseMessages.ID_INVALID);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid asset bill id");
				//response.setContent(null);
			}*/
			 if(assetBillingObj.getAssetId() != null)
			 {
				 checkAssetId(assetBillingObj.getAssetId());
				 assetBillingObject.setAssetId(assetBillingObj.getAssetId());
			 }
			 if(assetBillingObj.getVendorId() != null) 
			 {
			    	checkVendorId(assetBillingObj.getVendorId());
			    	assetBillingObject.setVendorId(assetBillingObj.getVendorId());
			 }
			 if(assetBillingObj.getTransactionDate() != null)
			 {
				 assetBillingObject.setTransactionDate(new Date());
			 }
			 /*if(assetBillingObj.getAssetAmount() != null)
			 {
				 checkAssetAmount(assetBillingObj.getAssetAmount());
				 assetBillingObject.setAssetAmount(assetBillingObj.getAssetAmount());
			 }
			 if(assetBillingObj.getAssetAmount() != null)
			 {
				 checkAssetAmount(assetBillingObj.getAssetAmount());
				 assetBillingObject.setAssetAmount(assetBillingObj.getAssetAmount());
			 }
			 if(assetBillingObj.getGstAmount() != null)
			 {
				 checkAssetAmount(assetBillingObj.getAssetAmount());
				 assetBillingObject.setAssetAmount(assetBillingObj.getAssetAmount());
			 }*/
			   assetBillingObject = assetBillingRepository.save(assetBillingObj);
				//AssetBilling assetBillingObject = assetBillingRepository.save(assetBilling);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_PURCHASE_BILL_EDIT_SUCCESSFULLY);
			  } 

          else {
				response.setSuccess(false);
				
				
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid asset bill id");
				//response.setContent(null);
			 
		 }
		return response;
	}


	//private boolean checkAssetAmount(Double assetAmount) 
	//{
	   //return true;
		
	//}


	private boolean checkAssetId(String assetId) 
	{
		Asset asset = assetRepository.getByassetId(assetId);
		if(asset == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
		}
		else
		{
		return true;
		}
	}


	private boolean checkVendorId(String vendorId) 
	{
	    	AssetVendor vendor = assetVendorRepository.getVendorById(vendorId);
			if (vendor == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid");
			}
			else {
				return true;
			}
			
		}
@Override
public ApiResponse addRepairAssetBill(AssetBillingRequest assetBilling) {
	
	ApiResponse response = new ApiResponse();
	ObjectMapper mapper = new ObjectMapper();
	//assetBilling = validateAssetBilling(assetBilling);
	AssetBilling assetBillingObj = new AssetBilling() ;
	
	try
	{
		assetBillingObj  = mapper.readValue(assetBilling.getAssetBilling(), AssetBilling.class);
	}
	catch (JsonMappingException e) 
	{
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
	}
    catch (JsonProcessingException e) 
	{
	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());



	}
	catch (Exception e) {
	// TODO Auto-generated catch block
	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	response = validateAssetBilling(assetBillingObj);
	//assetBillingObj = assetBillingRepository.save(assetBillingObj);
		if(response.isSuccess())
		{
		    response = validateAssetIssueId(assetBillingObj);
			if(assetBillingObj != null)
			{
				assetBillingObj = assetBillingRepository.save(assetBillingObj);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_REPAIR_BILL_ADDED_SUCCESSFULLY);
			}
		}

	return response;
	
   }

	@Override
	public ApiResponse editRepairAssetBill(AssetBillingRequest assetBilling) 
	{
		
		ApiResponse response = new ApiResponse();
		ObjectMapper mapper = new ObjectMapper();
		AssetBilling assetBillingObj = new AssetBilling() ;
		
		try
		{
			assetBillingObj  = mapper.readValue(assetBilling.getAssetBilling(), AssetBilling.class);
		}
		catch (JsonMappingException e) 
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	    catch (JsonProcessingException e) 
		{
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());



		}
		catch (Exception e) {
		// TODO Auto-generated catch block
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		AssetBilling assetBillingObjects = assetBillingRepository.getAssetBillId(assetBillingObj.getAssetBillId());
		
		 
		 if(assetBillingObjects != null)
		 {  
			 assetBillingObj.setAssetBillId(assetBillingObjects.getAssetBillId());
			     
			 if(assetBillingObj.getAssetId() != null)
			 {
				 checkAssetId(assetBillingObj.getAssetId());
				 assetBillingObjects.setAssetId(assetBillingObj.getAssetId());
			 }
			 if(assetBillingObj.getVendorId() != null) 
			 {
			    	checkVendorId(assetBillingObj.getVendorId());
			    	assetBillingObjects.setVendorId(assetBillingObj.getVendorId());
			 }
			 if(assetBillingObj.getTransactionDate() != null)
			 {
				 //checkTransactionDate(assetBillingObj.getTransactionDate());
				 assetBillingObjects.setTransactionDate(assetBillingObj.getTransactionDate());
			 }
			 //response = validateAssetIssueId(assetBillingObj);
			 
			 if(assetBillingObj.getAssetIssueId() != null)
			 {
				 checkAssetIssueId(assetBillingObj.getAssetIssueId());
				 assetBillingObjects.setAssetIssueId(assetBillingObj.getAssetIssueId());
			 }
			 if(assetBillingObj.getUnderWarrenty() != null)
			 {
				 checkUnderWarrenty(assetBillingObj.getUnderWarrenty());
				 assetBillingObjects.setUnderWarrenty(assetBillingObj.getUnderWarrenty());
			 }
			    assetBillingObj.setAssetAmount(assetBillingObj.getAssetAmount());
				assetBillingObj.setGstAmount(assetBillingObj.getGstAmount());
				assetBillingObj.setAmountPaid(assetBillingObj.getAmountPaid());
				
			   assetBillingObjects = assetBillingRepository.save(assetBillingObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_REPAIR_BILL_EDITED_SUCCESSFULLY);
			  } 
		 

          else {
				response.setSuccess(false);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid asset bill id");
			 
		 }
		
		
		
		
		
		
		
		return response;
	}


	private boolean checkAssetIssueId(String assetIssueId) 
	{
		AssetIssues issue = assetIssuesRepository.getAssetIssueById(assetIssueId);
		if (issue == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid");
		}
		else 
		{
			return true;
		}
		
	}


	private boolean checkUnderWarrenty(Boolean underWarrenty) 
	{
		ApiResponse response = new ApiResponse();
		AssetBilling assetBillingObj = new AssetBilling();
		if(assetBillingObj.getUnderWarrenty() == true)
		{
			response.setMessage(ResponseMessages.ASSET_IS_UNDERWARRENTY_NO_NEED_TO_PAY_ANY_AMOUNT);
		}
		return true;
			
}


	


	
}

	/*@Override
	public ApiResponse returnFromRepair(AssetBilling assetBilling) 
	{
		ApiResponse response = new ApiResponse(false);
		//assetBilling = validateAssetBilling(assetBilling);
		response = validateAssetBilling(assetBilling);
		if(response.isSuccess())
		{
		   response = validateAssetIssue(assetBilling);
	       
			if(assetBilling != null)
			{
				validateReturnDate(assetBilling);
				assetBillingRepository.save(assetBilling);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.RETURN_FROM_REPAIR);
			}
		}
		return response;
	}
   public ApiResponse validateReturnDate(AssetBilling assetBilling) 
   {
      ApiResponse response = new ApiResponse(false);
      if(assetBilling.getReturnDate() == null)
      {
    	  throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "return date is mandatory");
      }
      return response;
   }

	/*@Override
	public ApiResponse getAllAssetBillingList(Pageable pageable) 
	{
		Page<Map> assetBillingList = assetBillingRepository.getAllAssetBilling();
		Map content = new HashMap();
		content.put("assetBillingList", assetBillingList);
		ApiResponse response = new ApiResponse(true);
		
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}*/
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
   







	

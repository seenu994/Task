package com.xyram.ticketingTool.service.impl;

import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.xyram.ticketingTool.enumType.AssetIssueStatus;
import com.xyram.ticketingTool.request.AssetBillingRequest;
import com.xyram.ticketingTool.service.AssetBillingService;
import com.xyram.ticketingTool.service.AssetIssuesService;
import com.xyram.ticketingTool.service.AssetService;
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
	AssetService assetService;
	

	@Autowired
	AssetIssuesRepository assetIssuesRepository;
	
	//String billingType[] = {"purchase", "repair", "return"};
	@Override
	public ApiResponse addPurchaseAssetBill(AssetBillingRequest assetBilling) 
	{
		ApiResponse response = new ApiResponse();
		ObjectMapper mapper = new ObjectMapper();
		//assetBilling = validateAssetBilling(assetBilling);
		AssetBilling assetBillingObj = null;
		
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
		 
			if(assetBilling != null)
			{
				assetBillingObj.setBillingType("purchase");
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
			 
			 AssetBilling assetBillings = assetBillingRepository.getAssetById(assetBilling.getAssetId());
			 System.out.println(assetBillings);
			 if(assetBillings != null)
			 {
			   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id already exists!!!");
			 }
			 
			// int Integer;
			 //String s1 = getBillingDetailByAssetId(assetId);
			 //System.out.println("asset.assetId - " + asset.getAssetId());
			 //int Integer;
			
			
			//System.out.println("asset.assetId - " + assetId.getBillingDetailByAssetId(assetId));
			 //if(assetId == null )
			 //{
				 //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id not valid!!!");
			 //}
			
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
		 }
	
		
		 //validate billingtype
		 if(assetBilling.getBillingType() == null || assetBilling.getBillingType().equals(""))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "billing type is mandatory");
		 }
		 
		
	   //validate transactionDate
		 if(assetBilling.getTransactionDate() == null)
		 {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "transaction date is mandatory !!");
		 
		 }
		 else
		 {
			 validateTransactionDate(assetBilling.getTransactionDate());
		 }
		 /*else
		 {
			 Date purchaseDate = assetRepository.getPurchaseDateById(assetBilling.getAssetId());
			 if(assetBilling.getTransactionDate().equals(purchaseDate))
			 {
				 
			 }
			 assetBilling.setTransactionDate(purchaseDate);
		 }*/
		 if(assetBilling.getAssetAmount() == null || assetBilling.getAssetAmount().equals(""))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset amount is mandatory !!");
		 }
		 if(assetBilling.getGstAmount() == null || assetBilling.getGstAmount().equals(""))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "gst amount is mandatory !!");
		 }
		 //String assetBillId = assetBilling.getAssetBillId();
		 //checkAmountPaid(assetBilling.isAmountPaid(),assetBillId);
		 assetBilling.setAmountPaid(true);
		
    	response.setSuccess(true);
		return response;
	
}

	

	private String getBillingDetailByAssetId(String assetId) {
		
		return assetId;
	}


	private boolean validateTransactionDate(Date transactionDate) 
    {
    	AssetBilling assetBillingObj = new AssetBilling();
    	 Date purchaseDate = assetRepository.getPurchaseDateById(assetBillingObj.getAssetId());
		 if(assetBillingObj.getTransactionDate() ==(purchaseDate))
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
		
		
	}


	/*private boolean checkAmountPaid(boolean amountPaid, String assetBillId) 
    {
    	//AssetBilling assetBillingObj = new AssetBilling();
    	//System.out.println(assetBillingObj);
		System.out.println("assetBillId"+ assetBillId);
		AssetBilling assetBillingObj = assetBillingRepository.getAssetAmount(assetBillId);
		assetBillingObj = assetBillingRepository.getGstAmount(assetBillId);
		
    	if(assetBillingObj.getAssetAmount() == null && assetBillingObj.getAssetAmount().equals("") &&
    	assetBillingObj.getGstAmount() == null && assetBillingObj.getGstAmount().equals(""))
        {
    		System.out.println(assetBillingObj.isAmountPaid());
    	   return false;
        }
    	else
    	{
    		System.out.println(assetBillingObj.isAmountPaid());
    	   return true;
    	}
	}*/


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
		response.setSuccess(true);
		return response;
	}


	@Override
	public ApiResponse getAllAssestVendor(String vendorID) {
		ApiResponse response = new ApiResponse(false);
		return response;

	}

    @Override
	public ApiResponse editPurchaseAssetBill(AssetBillingRequest assetBilling) 
	{
    	ApiResponse response = new ApiResponse();
		ObjectMapper mapper = new ObjectMapper();
		//assetBilling = validateAssetBilling(assetBilling);
		AssetBilling assetBillingObj  = null;
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
		
		AssetBilling assetBillingObject = assetBillingRepository.getAssetBillById(assetBillingObj.getAssetBillId());
		 //System.out.println("assetBillingObject.getAssetBillId() - " + assetBillingObject.getAssetId());
		 
		 if(assetBillingObject != null)
		 {
			 //System.out.println("IN IF");   
			 if(assetBillingObj.getAssetId() != null)
			 {
				 checkAssetId(assetBillingObject.getAssetId());
				 assetBillingObject.setAssetId(assetBillingObj.getAssetId());
			 }
			 if(assetBillingObj.getVendorId() != null) 
			 {
			    	checkVendorId(assetBillingObject.getVendorId());
			    	assetBillingObject.setVendorId(assetBillingObj.getVendorId());
			 }
			 if(assetBillingObj.getBillingType() != null)
			 {
				 assetBillingObject.setBillingType("purchase");
			 }
			 if(assetBillingObj.getTransactionDate() != null)
			 {
				 assetBillingObject.setTransactionDate(new Date());
			 }
			 if(assetBillingObj.getAssetAmount() != null)
			 {
				 //checkAssetAmount(assetBilling.)
				assetBillingObject.setAssetAmount(assetBillingObj.getAssetAmount());
			 }
		     if(assetBillingObj.getGstAmount() != null)
			 {
				 assetBillingObject.setAssetAmount(assetBillingObj.getAssetAmount());
			 }
		     assetBillingObj.setAmountPaid(true);
		     
			   assetBillingRepository.save(assetBillingObject);
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

	


	private boolean checkAssetId(String assetBillId) 
	{
		Asset asset = assetRepository.getByAssetId(assetBillId);
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
	AssetBilling assetBillingObj = null;
	
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
				assetBillingObj.setBillingType("repair");
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
		AssetBilling assetBillingObj = null;
		
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
		
		AssetBilling assetBillingObjects = assetBillingRepository.getAssetBillById(assetBillingObj.getAssetBillId());
		
		 
		 if(assetBillingObjects != null)
		 {  
			 assetBillingObj.setAssetBillId(assetBillingObjects.getAssetBillId());
			     
			 if(assetBillingObj.getAssetId() != null)
			 {
				 checkAssetId(assetBillingObj.getAssetId());
				 //assetBillingObjects.setAssetId(assetBillingObj.getAssetId());
			 }
			 if(assetBillingObj.getVendorId() != null) 
			 {
			    	checkVendorId(assetBillingObj.getVendorId());
			    	assetBillingObjects.setVendorId(assetBillingObj.getVendorId());
			 }
			 if(assetBillingObj.getBillingType() != null)
			 {
				 assetBillingObjects.setBillingType("repair");
			 }
			 if(assetBillingObj.getTransactionDate() != null)
			 {
				 assetBillingObjects.setTransactionDate(new Date());
			 }
			 //response = validateAssetIssueId(assetBillingObj);
			 
			 if(assetBillingObj.getAssetIssueId() != null)
			 {
				 checkAssetIssueId(assetBillingObj.getAssetIssueId());
				 assetBillingObjects.setAssetIssueId(assetBillingObj.getAssetIssueId());
			 }
			 if(assetBillingObj.getUnderWarrenty() != null)
			 {
			    assetBillingObjects=checkUnderWarrenty1(assetBilling, assetBillingObj);
			 }
			    
				
			   assetBillingRepository.save(assetBillingObjects);

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


	private AssetBilling checkUnderWarrenty1(AssetBillingRequest assetBillingObj,AssetBilling assetBilling) 
	{
		if(assetBilling.getUnderWarrenty())
		{
			
        	assetBilling.setAmountPaid(false);
        	assetBilling.setUnderWarrenty(true);
        	return assetBilling;
        	
		}
		
		else
		{
			assetBilling.setAssetAmount(assetBilling.getAssetAmount());
			assetBilling.setGstAmount(assetBilling.getGstAmount());
			assetBilling.setAmountPaid(false);
			assetBilling.setUnderWarrenty(false);
			return assetBilling;
		}
			
}
    @Override
	public ApiResponse returnFromRepair(AssetBillingRequest assetBilling) 
	{
    	ApiResponse response = new ApiResponse();
		ObjectMapper mapper = new ObjectMapper();
		AssetBilling assetBillingObj = null;
		
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
		
		AssetBilling billingObj = assetBillingRepository.getAssetBillById(assetBillingObj.getAssetBillId());
		
		 if(billingObj != null) 
		    {	
				if(assetBillingObj.getAssetId() != null)
				{
					 checkAssetId(billingObj.getAssetId());
					 billingObj.setAssetId(assetBillingObj.getAssetId());
				}
				if(assetBillingObj.getVendorId() != null)
				{
					checkVendorId(billingObj.getVendorId());
					billingObj.setVendorId(assetBillingObj.getVendorId());
				}
				
				if(assetBillingObj.getBillingType() != null)
				{
					billingObj.setBillingType("return");
				}
				if(assetBillingObj.getTransactionDate() != null)
				{
					assetBillingObj.setTransactionDate(assetBillingObj.getTransactionDate());
				}
				if(assetBillingObj.getUnderWarrenty() != false)
				{
					checkUnderWarrenty1(assetBilling,billingObj);
					billingObj.setUnderWarrenty(assetBillingObj.getUnderWarrenty());
				}
                if(assetBillingObj.getAssetIssueId() != null)
                {
                	checkAssetIssueId(billingObj.getAssetIssueId());
                	billingObj.setAssetIssueId(assetBillingObj.getAssetIssueId());
                }
                billingObj.setReturnDate(new Date());
				/*if(assetBillingObj.getReturnDate()!= null)
				{
					assetBillingObj.setReturnDate(new Date());
					//assetIssues.setComplaintRaisedDate(new Date())
					
				}*/
				assetBillingRepository.save(billingObj);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.RETURN_REPAIR);
				
			}

			else 
			{
				response.setSuccess(false);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid assetBillId");
				
			}
	       return response;
	    }
		
	


	private boolean checkReturnDate(Date returnDate, String assetBillId) 
	{
		    Date transactionDate = assetBillingRepository.getTransationDate(assetBillId);
			Date d1 = transactionDate;
			Date d2 = returnDate;
			System.out.println("d1"+ transactionDate);
			if(d1.after(d2) || d1.equals(d2))
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " returnDate is mandatory");
			}
			else
			{
				return true;
			}
		}
		



	@Override
	public ApiResponse getAllAssetBilling(Pageable pageable) {
		ApiResponse response = new ApiResponse();
		Page<Map> assetBilling = assetBillingRepository.getAllAssetBilling(pageable);
		Map content = new HashMap();
		content.put("assetBilling", assetBilling);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("asset billing data Retrieved Successfully");
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}
}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
   







	

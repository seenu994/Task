package com.xyram.ticketingTool.service.impl;


import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
//import com.xyram.ticketingTool.Repository.AssetIssuesStatusRepository;
import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetIssues;
//import com.xyram.ticketingTool.entity.AssetIssuesStatus;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.enumType.AssetIssueStatus;
import com.xyram.ticketingTool.enumType.AssetStatus;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.AssetIssuesService;
import com.xyram.ticketingTool.util.ExcelUtil;
import com.xyram.ticketingTool.util.ExcelWriter;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssetIssuesServiceImpl implements AssetIssuesService
{
	private static final Logger logger = LoggerFactory.getLogger(AssetIssuesServiceImpl.class);
	@Autowired
	AssetIssuesRepository  assetIssuesRepository;
	
	@Autowired
	AssetRepository  assetRepository;
	
	@Autowired
	AssetVendorRepository assetVendorRepository;
	
	@Autowired
	CurrentUser currentUser;

	
	@Override
	public ApiResponse addAssetIssues(AssetIssues assetIssues) 
	{
		ApiResponse response = new ApiResponse(false);
		//AssetIssues assetIssue = new AssetIssues();
		response = validateAssetIssues(assetIssues);
		//response = validateAssetIssueStatus(assetIssues);
		if(response.isSuccess()) 
		{
			if(assetIssues != null)
			{
				assetIssues.setCreatedAt(new Date());
				assetIssues.setCreatedBy(currentUser.getName());
				assetIssuesRepository.save(assetIssues);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_ISSUES_ADDED_SUCCESSFULLY);
			}
		}
	     return response;
		}
	
	private ApiResponse validateAssetIssues(AssetIssues assetIssues) 
	{
		ApiResponse response = new ApiResponse(false);
		
		 //validate asset id
		 if(assetIssues.getAssetId() == null || assetIssues.getAssetId().equals(""))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory !!");
		 }
		 else
		 {
			 //Asset asset = assetRepository.getaId(assetIssues.getaId());
			 Asset asset = getAssetById(assetIssues.getAssetId());
			 if(asset == null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid asset reference");
			 }
		 }

		 if(assetIssues.getVendorId() == null ||assetIssues.getVendorId().equals(""))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandatory !!");
		 }
		 else
		 {
			 AssetVendor vendor = assetVendorRepository.getVendorById(assetIssues.getVendorId());
			 if(vendor == null)
			 {
				 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid!!!");
			 }
			 
		 }	
		 
		 if(assetIssues.getComplaintRaisedDate() == null)
		 {
			 assetIssues.setComplaintRaisedDate(new Date());
		    //Date currentUtilDate = new Date();
			// Date currentDate = Calendar.getInstance().getTime();
		 }
		 
		 if(assetIssues.getAssetIssueStatus() == null)
	     {
	    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandatory");
	     }
		 if(assetIssues.getDescription() == null || assetIssues.getDescription().equals(""))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description is mandatory");
		 }
		 if(assetIssues.getDescription().length() <= 10 || (assetIssues.getDescription().length() >= 500))
		 {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description length should be minimum 10 characters and max 500 characters");
		 }
		 
		 response.setSuccess(true);
		 return response;
	}

	public Asset getAssetById(String aId) {
		
		return assetRepository.getAssetById(aId);
		
	}


	
	
	public ApiResponse editAssetIssues(AssetIssues assetIssues, String assetIssueId) 
	{
		
        ApiResponse response = new ApiResponse(false);
		//AssetIssues assetIssue;
		AssetIssues assetIssuesObj = assetIssuesRepository.getAssetIssueById(assetIssueId);
		 
		if(assetIssuesObj != null) 
	    {	
			if(assetIssues.getAssetId() != null)
			{
				 checkAssetId(assetIssuesObj.getAssetId());
				 assetIssuesObj.setAssetId(assetIssues.getAssetId());
			}
			if(assetIssues.getVendorId() != null || !(assetIssues.getVendorId().equals("")))
			{
				checkVendorId(assetIssues.getVendorId());
				assetIssuesObj.setVendorId(assetIssues.getVendorId());
			}
			else
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"vendor id is mandatory");
			}
			if(assetIssues.getComplaintRaisedDate()!= null)
			{
				assetIssuesObj.setComplaintRaisedDate(assetIssues.getComplaintRaisedDate());
			}
			if(assetIssues.getAssetIssueStatus() != null)
			{
				//(assetIssuesObj.getAssetIssueStatus());
				assetIssuesObj.setAssetIssueStatus(assetIssues.getAssetIssueStatus());
			}
			if(assetIssues.getDescription() != null || !assetIssues.getDescription().equals(""))
			{
				checkDescription(assetIssues.getDescription());
				assetIssuesObj.setDescription(assetIssues.getDescription());
			}
			else
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"description is mandatory");
			}
			assetIssuesObj.setLastUpdatedAt(new Date());
			assetIssuesObj.setUpdatedBy(currentUser.getName());
			assetIssuesRepository.save(assetIssuesObj);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_ISSUES_EDIT_SUCCESSFULLY);
			
		}
		
		else 
		{
			response.setSuccess(false);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid assetIssueId");
			
		}
       return response;
    }


	private boolean checkDescription(String description) 
	{
//		if(description == null || description.equals(""))
//		{
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description is mandatory");
//		}
		if(description.length() <= 10 || description.length() >= 500)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description length should be minimum 10 characters and max 500 characters");
		}
		return true;
		
	}

	private boolean checkVendorId(String vendorId) 
	{
//		if(vendorId == null || vendorId.equals(""))
//		{
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandatory");
//		}
		AssetVendor assetVendor = assetVendorRepository.getVendorById(vendorId);
		if(assetVendor == null || assetVendor.equals(""))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid");
		}
		else
		{
		   return true;
		}
	}

	private boolean checkAssetId(String assetId) 
	{
//		if(assetId == null || assetId.equals(""))
//		{
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory");
//		}
		Asset asset = assetRepository.getByAssetId(assetId);
		if(asset == null || asset.equals(""))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is invalid");
		}
		else
		{
		   return true;
		}
    }

	@Override
	public ApiResponse returnRepair(AssetIssues assetIssues,String assetIssueId) 
	{
         ApiResponse response = new ApiResponse(false);
		 AssetIssues assetIssuesObj = assetIssuesRepository.getAssetIssueById(assetIssueId);
		 if(assetIssuesObj != null) 
		    {	
				if(assetIssues.getAssetId() != null || !assetIssues.getAssetId().equals(""))
				{
					 checksAssetId(assetIssuesObj.getAssetIssueId(), assetIssues.getAssetId());
					 assetIssuesObj.setAssetId(assetIssues.getAssetId());
				}
				if(assetIssues.getVendorId() != null || !assetIssues.getVendorId().equals(""))
				{
					checksVendorId(assetIssuesObj.getAssetIssueId(),assetIssues.getVendorId());
					assetIssuesObj.setVendorId(assetIssues.getVendorId());
				}
				else
				{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor Id mandatory");
				}
				if(assetIssues.getAssetIssueStatus() != null)
				{
					//checkAssetIssuesStatus(assetIssues.getAssetIssueStatus());
					assetIssuesObj.setAssetIssueStatus(AssetIssueStatus.CLOSE);
				}
				
				if(assetIssues.getDescription() != null || !(assetIssues.getDescription().equals("")))
				{
					checkDescription(assetIssues.getDescription());
				    assetIssuesObj.setDescription(assetIssues.getDescription());
				}
				else
				{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description should be mandatory");
				}
				assetIssuesObj.setResolvedDate(new Date());
				
				//assetIssuesObj.setSolution(true);
				if(assetIssues.isSolution() == true)
				{
					assetIssuesObj.setSolution(true);
					if(assetIssues.getComments() == null || assetIssues.getComments().equals(""))
					{
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comments should be mandatory");
					}
					
				    validateComments(assetIssues.getComments());
					assetIssuesObj.setComments(assetIssues.getComments());
					//assetIssuesObj.setSolution(true);
					//assetIssuesObj.returnFromRepair(assetIssues);
					
				}
				else
				{
					assetIssuesObj.setSolution(false);
					assetIssuesObj.setComments(null);
				}
				assetIssuesObj.setLastUpdatedAt(new Date());
				assetIssuesObj.setUpdatedBy(currentUser.getName());
				System.out.println("solution boolean value :" +  assetIssuesObj.isSolution());
				
				assetIssuesRepository.save(assetIssuesObj);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.RETURNS_REPAIR);
				
			}

			else 
			{
				response.setSuccess(false);
				
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid assetIssueId");
				
			}
	       return response;
	    }
		

	private boolean checksVendorId(String assetIssueId, String vendorId) 
	{
//		if(vendorId == null || vendorId.equals(""))
//		{
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id mandatory");
//		}
		AssetIssues assetIssues = assetIssuesRepository.getVendorById(assetIssueId, vendorId);
		if(assetIssues == null || assetIssues.equals(""))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid vendor Id");
		}
		else
		{
		   return true;
		}
	}

	private boolean checksAssetId(String assetIssueId, String assetId) 
	{
//		if(assetId == "" || assetId == null)
//		{
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory");
//		}
		AssetIssues assetIssues = assetIssuesRepository.getAssetById(assetIssueId, assetId);
		if(assetIssues == null || assetIssues.equals(""))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid asset id");
		}
		
		return true;
		
	}

	private boolean validateComments(String comments) 
	{
//		if(comments == null || comments.equals(""))
//		{
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comments should be manadatory");
//		}
		if(comments.length() <= 10 || comments.length() >= 300)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comments length should be minimum 10 characters and max 300 characters");
		}
		return true;
		
	}

	/*private boolean checkResolvedDate(Date resolvedDate,String assetIssueId) 
	{
		
		Date compaintRaisedDate = assetIssuesRepository.getCompaintRaisedDate(assetIssueId);
		Date d1 = compaintRaisedDate;
		System.out.println("d1"+ compaintRaisedDate);
		Date d2 = resolvedDate;
		System.out.println(resolvedDate);
		Date d3 = new Date();
		
	    if (d1.after(d2) && d3.after(d2)) 
	    {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"resolved date should be greater than complaint raised date");
		}
		else 
		{
			return true;
		}
		
	}*/
		

//	private boolean checkAssetIssuesStatus(AssetIssueStatus assetIssueStatus)
//	{
//		//AssetIssues assetIssue = new AssetIssues();
//		if(assetIssueStatus == null)
//		{
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
//		}
//		else
//		{
//			return true;
//		}
//		
//	}
	
	@Override
	public ApiResponse returnDamage(AssetIssues assetIssues,String assetIssueId) 
	{
         ApiResponse response = new ApiResponse(false);
		 AssetIssues assetIssuesObj = assetIssuesRepository.getAssetIssueById(assetIssueId);
		 //Asset asset = getAssetById(assetIssues.getAssetId());
		 
		if(assetIssues != null) 
	    {	
			if(assetIssues.getAssetId() != null || !(assetIssues.getAssetId().equals("")))
			{
				 checksAssetId(assetIssuesObj.getAssetIssueId(), assetIssues.getAssetId());
				 assetIssuesObj.setAssetId(assetIssues.getAssetId());
			}
		
			if(assetIssues.getVendorId() != null || !(assetIssues.getVendorId().equals("")))
			{
				checksVendorId(assetIssuesObj.getAssetIssueId(),assetIssues.getVendorId());
				assetIssuesObj.setVendorId(assetIssues.getVendorId());
			}
			else
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandetory");
			}
//			if(assetIssues.getComplaintRaisedDate()!= null)
//			{
//				assetIssues.setComplaintRaisedDate(assetIssuesObj.getComplaintRaisedDate());
//			}
			if(assetIssues.getDescription() != null || !(assetIssues.getDescription().equals("")))
			{
				checkDescription(assetIssues.getDescription());
				assetIssuesObj.setDescription(assetIssues.getDescription());
			}
			else
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description is mandetory");	
			}
			if(assetIssues.getAssetIssueStatus() != null)
			{
				//checkAssetIssuesStatus(assetIssues.getAssetIssueStatus());
				assetIssuesObj.setAssetIssueStatus(AssetIssueStatus.DAMAGE);
			}
			assetIssuesObj.setResolvedDate(new Date());
			//if(assetIssues.getResolvedDate()!= null)
			//{
				//checkResolvedDate(assetIssues.getResolvedDate(),assetIssueId);
				//assetIssuesObj.setResolvedDate(assetIssues.getResolvedDate());
			//}

			if(assetIssues.isSolution() == true)
			{
				assetIssuesObj.setSolution(true);
				if(assetIssues.getComments() == null || assetIssues.getComments().equals(""))
				{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comments should be mandatory");
				}
				
			    validateComments(assetIssues.getComments());
				assetIssuesObj.setComments(assetIssues.getComments());
				//assetIssuesObj.setSolution(true);
				//assetIssuesObj.returnFromRepair(assetIssues);
				
			}
			else
			{
				assetIssuesObj.setSolution(false);
				assetIssuesObj.setComments(null);
			}
	        
		    assetIssuesObj.setLastUpdatedAt(new Date());
		    assetIssuesObj.setUpdatedBy(currentUser.getName());
		    
		assetIssuesRepository.save(assetIssuesObj);
		response.setMessage(ResponseMessages.RETURN_DAMAGE);
		response.setSuccess(true);

	    }
		else
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid assetIssueId");
		}
		return response;
		
	}


	private boolean checkStatus(AssetIssueStatus assetIssueStatus) 
	{
			AssetIssues assetIssue = new AssetIssues();
			if(assetIssueStatus == null)
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
			}
			else
			{
				assetIssue.setAssetIssueStatus(AssetIssueStatus.DAMAGE);
			}
			return true;
			
	}


       @Override
	public ApiResponse getAllAssetsIssues(Map<String, Object> filter, Pageable pageable) {
		
		ApiResponse response = new ApiResponse(false);
		
		String searchString = filter.containsKey("searchString") ? ((String) filter.get("searchString"))
				: null;
		String assetIssueStatus = filter.containsKey("assetIssueStatus") ? ((String) filter.get("assetIssueStatus")).toUpperCase()
					: null;
		String assetId = filter.containsKey("assetId") ? ((String) filter.get("assetId"))
				: null;
		String vendorId = filter.containsKey("vendorId") ? ((String) filter.get("vendorId"))
				: null;
		String fromDate = filter.containsKey("fromDate") ? filter.get("fromDate").toString(): null;
		String toDate = filter.containsKey("toDate") ? filter.get("toDate").toString():null;
		
		Date parsefromDate = null;
		Date parsetoDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(fromDate != null && toDate != null)
		{
			try
			{
				parsefromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
				parsetoDate = toDate != null ? dateFormat.parse(toDate) : null;
			}
			catch(ParseException e)
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid date format date should be yyyy-MM-dd");
			}
			
		}
		
		AssetIssueStatus assetIssuestatus = null;
		if(assetIssueStatus!=null) {
			try {
				assetIssuestatus = assetIssueStatus != null ? AssetIssueStatus.toEnum(assetIssueStatus) : null;
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						filter.get("status").toString() + " is not a valid status");
			}
		}
				
		Page<Map> assetIssues = assetIssuesRepository.getAllAssetsIssues(assetIssuestatus, assetId, vendorId, searchString, fromDate, toDate, pageable);
		
		if(assetIssues.getSize() > 0) {
			Map content = new HashMap();
			content.put("assetissues", assetIssues);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("Asset Issue List retreived successfully.");
		}else {
			response.setSuccess(false);
			response.setMessage("List is empty.");	
		}
		return response;
	}

       @Override
       public ApiResponse getAssetIssuesById(String assetIssueId) 
       {
    	   
	       ApiResponse response = new ApiResponse();
	       
	       Map assetIssues = assetIssuesRepository.getByAssetIssueId(assetIssueId);
	       
	       if (assetIssues != null && assetIssues.size() > 0) 
	       {
	    	   Map content = new HashMap<>();
			   content.put("Asset", assetIssues);
		       response.setSuccess(true);
		       //System.out.println(assetIssues);
		       System.out.println("hi");
		       response.setMessage("Asset Issue Retrieved Successfully");
		       response.setContent(content);
		   }
	       else {
	       response.setSuccess(false);
	       response.setMessage("Could not retrieve data");
	       }
	       
	       
	       return response;
       }
       
      

       

	@Override
	public ApiResponse downloadAllAssetIssues(Map<String, Object> filter) {
		ApiResponse response = new ApiResponse();

		String assetIssueStatus = filter.containsKey("assetIssueStatus") ? ((String) filter.get("assetIssueStatus")).toUpperCase()
				: null;
		String assetId = filter.containsKey("assetId") ? ((String) filter.get("assetId"))
				: null;
		String vendorId = filter.containsKey("vendorId") ? ((String) filter.get("vendorId"))
				: null;
		
		String fromDateStr = filter.containsKey("fromDate") ? ((String) filter.get("fromDate")).toLowerCase()
				: null;
		Date fromDate = null;
		if(fromDateStr!=null) {
			try {
				fromDate=new SimpleDateFormat("yyyy-MM-dd").parse(fromDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		String toDateStr = filter.containsKey("toDate") ? ((String) filter.get("toDate")).toLowerCase()
				: null;
		Date toDate = null;
		if(toDateStr!=null) {
			try {
				toDate=new SimpleDateFormat("yyyy-MM-dd").parse(toDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		if(toDate == null || fromDate == null) {
			response.setMessage("From or To dates are missing");
			response.setStatus("failure");
		}
		AssetIssueStatus status = null;
		if(assetIssueStatus!=null) {
			try {
				status = assetIssueStatus != null ? AssetIssueStatus.toEnum(assetIssueStatus) : null;
				System.out.println(status);
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						filter.get("status").toString() + " is not a valid status");
			}
		}
		List<Map> assetIssuesList = assetIssuesRepository.downloadAllAssetIssues(status,assetId, vendorId, fromDateStr, toDateStr);
		Map<String, Object> fileResponse = new HashMap<>();

		Workbook workbook = prepareExcelWorkBook(assetIssuesList);
        
		byte[] blob = ExcelUtil.toBlob(workbook);
		
		try {
			ExcelUtil.saveWorkbook(workbook,"AssetIssues-report.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//fileResponse.put("fileName", "assetIssues-report.xlsx");
		fileResponse.put("fileName", "AssetIssues-report-"+fromDateStr+"-"+toDateStr+".xlsx");
		fileResponse.put("type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileResponse.put("blob", blob);
		response.setFileDetails(fileResponse);
		System.out.println(fileResponse);
		response.setStatus("success");
		response.setMessage("report exported Successfully");

		response.setSuccess(true);
		return response;
	}

	private Workbook prepareExcelWorkBook(List<Map> assetIssuesList) 
	{
		List<String> headers = Arrays.asList("assetIssueId","assetId","vendorId","complaintRaisedDate","description","solution","assetIssueStatus",
				"resolvedDate","comments");
		List data = new ArrayList<>();

		for (Map assetIssue : assetIssuesList) 
		{
            Map row = new HashMap<>();

			row.put("assetIssueId",assetIssue.get("assetIssueId") != null ? assetIssue.get("assetIssueId").toString(): "");
			row.put("assetId",assetIssue.get("assetId") != null ? assetIssue.get("assetId").toString(): "");
			row.put("vendorId",assetIssue.get("vendorId") != null ? assetIssue.get("vendorId").toString(): "");
			row.put("complaintRaisedDate",assetIssue.get("complaintRaisedDate") != null ? assetIssue.get("complaintRaisedDate").toString(): "");
			row.put("description",assetIssue.get("description") != null ? assetIssue.get("description").toString(): "");
			row.put("solution",assetIssue.get("solution") != null ? assetIssue.get("solution").toString(): "");
			row.put("assetIssueStatus",assetIssue.get("assetIssueStatus") != null ? assetIssue.get("assetIssueStatus").toString(): "");
			row.put("resolvedDate",assetIssue.get("resolvedDate") != null ? assetIssue.get("resolvedDate").toString(): "");
			row.put("comments",assetIssue.get("comments") != null ? assetIssue.get("comments").toString(): "");
			
			
			data.add(row);

		}
        Workbook workbook = ExcelUtil.createSingleSheetWorkbook(ExcelUtil.createSheet("Asset issues report", headers, data));

		return workbook;
	}

	@Override
	public ApiResponse getAssetById1(String assetId) {
		
		ApiResponse response = new ApiResponse();
		List<Map> asset = assetIssuesRepository.getAllAssetById1(assetId);
		Map content = new HashMap();
		content.put("asset", asset);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset Retrieved Successfully");
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}

	@Override
	public ApiResponse changeAssetIssueStatus(String assetIssueId, AssetIssueStatus assetIssueStatus) 
	{
		ApiResponse response = new ApiResponse(false);
		//ApiResponse response = validateStatus(status);
		//if (response.isSuccess()) {
			AssetIssues assetIssue = assetIssuesRepository.getAssetIssueById(assetIssueId);
			if (assetIssue != null) {
				assetIssue.setAssetIssueStatus(assetIssueStatus);
				assetIssuesRepository.save(assetIssue);
		

				response.setSuccess(true);
				response.setMessage(ResponseMessages.STATUS_UPDATED_SUCCESSFULLY);
			}

			else {
				response.setSuccess(false);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid assetIssueId");
								//response.setContent(null);
			}

		return response;
	}
}

	
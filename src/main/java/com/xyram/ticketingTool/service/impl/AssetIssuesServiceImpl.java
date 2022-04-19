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
	//private static final ApiResponse assetIssues = null;
	@Autowired
	AssetIssuesRepository  assetIssuesRepository;
	
	@Autowired
	AssetRepository  assetRepository;
	
	@Autowired
	AssetIssuesService assetIssuesService;
	
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
		 assetIssues.setSolution(false);
		 response.setSuccess(true);
		 return response;
	}

	public Asset getAssetById(String aId) {
		
		return assetRepository.getAssetById(aId);
		
	}


	
	
	public ApiResponse editAssetIssues(AssetIssues assetIssues,String assetIssueId) 
	{
		
        ApiResponse response = new ApiResponse(false);
		//AssetIssues assetIssue;
		AssetIssues assetIssuesObj = assetIssuesRepository.getAssetIssueById(assetIssues.getAssetIssueId());
		
		 
		if(assetIssuesObj != null) 
	    {	
			if(assetIssues.getAssetId() != null)
			{
				 checkAssetId(assetIssuesObj.getAssetId());
				 assetIssuesObj.setAssetId(assetIssues.getAssetId());
			}
			if(assetIssues.getVendorId() != null)
			{
				checkVendorId(assetIssuesObj.getVendorId());
				assetIssuesObj.setVendorId(assetIssues.getVendorId());
			}
			if(assetIssues.getComplaintRaisedDate()!= null)
			{
				assetIssues.setComplaintRaisedDate(assetIssues.getComplaintRaisedDate());
			}
			if(assetIssues.getAssetIssueStatus() != null)
			{
				checkAssetIssuesStatus(assetIssuesObj.getAssetIssueStatus());
				assetIssuesObj.setAssetIssueStatus(assetIssues.getAssetIssueStatus());
			}
			if(assetIssues.getDescription() != null)
			{
				checkDescription(assetIssuesObj.getDescription());
				assetIssuesObj.setDescription(assetIssues.getDescription());
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
		
		return true;
		
	}

	private boolean checkVendorId(String vendorId) 
	{
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
		Asset asset = assetRepository.getByAssetId(assetId);
		if(asset == null || asset.equals(""))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
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
		 AssetIssues assetIssuesObj = assetIssuesRepository.getAssetIssueById(assetIssues.getAssetIssueId());
		 if(assetIssuesObj != null) 
		    {	
				if(assetIssues.getAssetId() != null)
				{
					 checkAssetId(assetIssuesObj.getAssetId());
					 assetIssuesObj.setAssetId(assetIssues.getAssetId());
				}
				if(assetIssues.getVendorId() != null)
				{
					checkVendorId(assetIssuesObj.getVendorId());
					assetIssuesObj.setVendorId(assetIssues.getVendorId());
				}
				
				if(assetIssues.getAssetIssueStatus() != null)
				{
					checkAssetIssuesStatus(assetIssuesObj.getAssetIssueStatus());
					assetIssuesObj.setAssetIssueStatus(AssetIssueStatus.CLOSE);
				}
				
				if(assetIssues.getDescription() != null)
				{
					checkDescription(assetIssuesObj.getDescription());
					assetIssuesObj.setDescription(assetIssues.getDescription());
				}
				//assetIssuesObj.setResolvedDate(new Date());
				if(assetIssues.getResolvedDate() != null)
				{
					//checkResolvedDate(assetIssues.getResolvedDate(), assetIssueId);
					assetIssuesObj.setResolvedDate(new Date());
				}
				if(assetIssues.isSolution()!= false)
				{
					CheckSolution(assetIssuesObj.isSolution());
					assetIssuesObj.setSolution(true);
					assetIssuesObj.setComments(assetIssues.getComments());
					//assetIssuesObj.setSolution(true);
					//assetIssuesObj.returnFromRepair(assetIssues);
					
				}
				
				assetIssuesObj.setLastUpdatedAt(new Date());
				assetIssuesObj.setUpdatedBy(currentUser.getName());
				
				assetIssuesRepository.save(assetIssuesObj);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.RETURN_REPAIR);
				
			}

			else 
			{
				response.setSuccess(false);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid assetIssueId");
				
			}
	       return response;
	    }
		

	private boolean CheckSolution(boolean solution) 
	{
		return true;
	}

	private boolean checkResolvedDate(Date resolvedDate,String assetIssueId) 
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
		
	}
		

	private boolean checkAssetIssuesStatus(AssetIssueStatus assetIssueStatus)
	{
		AssetIssues assetIssue = new AssetIssues();
		if(assetIssueStatus == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset issues status is mandetory");
		}
		else
		{
			return true;
			//assetIssue.setAssetIssueStatus(AssetIssueStatus.CLOSE);
		}
		
		
	}
	
	@Override
	public ApiResponse returnDamage(AssetIssues assetIssues,String assetIssueId) 
	{
         ApiResponse response = new ApiResponse(false);
		 AssetIssues assetIssuesObj = assetIssuesRepository.getAssetIssueById(assetIssues.getAssetIssueId());
		 //Asset asset = getAssetById(assetIssues.getAssetId());
		 
		if(assetIssues != null) 
	    {	
			if(assetIssues.getAssetId() != null)
			{
				 checkAssetId(assetIssuesObj.getAssetId());
				 assetIssuesObj.setAssetId(assetIssues.getAssetId());
			}
			if(assetIssues.getVendorId() != null)
			{
				checkVendorId(assetIssuesObj.getVendorId());
				assetIssuesObj.setVendorId(assetIssues.getVendorId());
			}
			/*if(assetIssues.getComplaintRaisedDate()!= null)
			{
				assetIssues.setComplaintRaisedDate(new Date());
			}*/
			if(assetIssues.getDescription() != null)
			{
				checkDescription(assetIssuesObj.getDescription());
				assetIssuesObj.setDescription(assetIssues.getDescription());
			}
			if(assetIssues.getAssetIssueStatus() != null)
			{
				checkAssetIssuesStatus(assetIssuesObj.getAssetIssueStatus());
				assetIssuesObj.setAssetIssueStatus(AssetIssueStatus.DAMAGE);
			}
			if(assetIssues.getResolvedDate()!= null)
			{
				checkResolvedDate(assetIssues.getResolvedDate(),assetIssueId);
				assetIssuesObj.setResolvedDate(assetIssues.getResolvedDate());
			}
		    if(assetIssues.isSolution() == true)
		    {
		    	//checkSolution(assetIssues.getSolution());
		    	assetIssuesObj.setComments(assetIssues.getComments());;
		    }
		    else
		    {
		    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"solution should be mandatory");
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

	/*@Override
	public ApiResponse getAssetIssuesById(String assetIssueId) 
	{
		ApiResponse response = new ApiResponse();
		AssetIssues assetIssues = assetIssuesRepository.getAssetIssueById(assetIssueId);
		System.out.println(assetIssues);
		Map content = new HashMap();
		content.put("assetIssues", assetIssues);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset Issues Retrieved Successfully");
			response.setContent(content);
			
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}*/
       @Override
       public ApiResponse getAssetIssuesById(String assetIssueId) 
       {
    	   
	       ApiResponse response = new ApiResponse();
	       
	       List<AssetIssues> assetIssues = assetIssuesRepository.getAssetIssuesById(assetIssueId);
	       
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
	public ApiResponse searchAssetIssue(String assetIssueId) 
	{
		ApiResponse response = new ApiResponse(false);
		AssetIssues assetIssues = new AssetIssues();
		assetIssues.setAssetIssueId(assetIssueId);
		List<Map> assetIssuesList = assetIssuesRepository.searchAssetIssue(assetIssueId);
		Map content = new HashMap();

		content.put("AssetIssuesList", assetIssuesList);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset issue Retrieved successfully");
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
		List<Map> assetIssuesList = assetIssuesRepository.downloadAllAssetIssues(assetId, vendorId, assetIssueStatus,fromDate, toDate);
		Map<String, Object> fileResponse = new HashMap<>();

		Workbook workbook = prepareExcelWorkBook(assetIssuesList);
        
		byte[] blob = ExcelUtil.toBlob(workbook);
		
		try {
			ExcelUtil.saveWorkbook(workbook, "report1.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fileResponse.put("fileName", "assetIssues-report.xlsx");
		fileResponse.put("type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileResponse.put("blob", blob);
		response.setFileDetails(fileResponse);
		System.out.println(fileResponse);
		response.setStatus("success");
		response.setMessage("report exported Successfully");

		
		return response;
	}

	private Workbook prepareExcelWorkBook(List<Map> assetIssuesList) 
	{
		List<String> headers = Arrays.asList("assetIssueId","assetId","vendorId","complaintRaisedDate","description","solution","assetIssueStatus",
				"resolvedDate");
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
			
			data.add(row);

		}
        Workbook workbook = ExcelUtil.createSingleSheetWorkbook(ExcelUtil.createSheet("Asset issues report", headers, data));

		return workbook;
		
	}
	/*
	 * public ApiResponse downloadAllAssetIssues(Map<String, Object> filter) {
		ApiResponse response = new ApiResponse();
		
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
		List<AssetIssues>assetIssuesList = assetIssuesRepository.downloadAllAssetIssues(assetId, vendorId, assetIssueStatus,fromDate, toDate);
		
		List excelHeaders = Arrays.asList("Asset IssueId", "Asset Id", "Vendor Name","complaint Raised Date", "Description", "Resolved Date", "Asset Issue Status", "Solution", "Comments");
		List excelData = new ArrayList<>();
		int index = 1;
		for (AssetIssues assetIssue : assetIssuesList) 
		{
            Map row = new HashMap<>();

			AssetVendor getVendorName = assetVendorRepository.getAssetVendorById(assetList.getVendorId());
			
			row.put("Asset IssueId", assetIssuesList.getAssetIssueId());
			row.put("Asset Id", assetIssuesList.getAssetId());
			row.put("Vendor Name", getVendorName.getVendorName());
			row.put("complaint Raised Date", assetIssuesList.getComplaint Raised Date());
			row.put("Description", assetIssuesList.getDescription());
			row.put("Resolved Date", assetIssuesList.getResolvedDate());
			row.put("Purchase on", assetIssuesList.getPurchaseDate());
			row.put("Warranty Date", assetIssuesList.getWarrantyDate());
			row.put("Asset Issue Status", assetIssuesList.getAssetIssueStatus());
			row.put("Solution", assetIssuesList.getSolution());
			row.put("Comments", assetIssuesList.getComments());
			
			
			excelData.add(row);
			index++;
		}

		XSSFWorkbook workbook = ExcelWriter.writeToExcel(excelHeaders, excelData, "Asset Details", null,
				"Asset Details", 1, 0);

		String filename = new SimpleDateFormat("'asset _issue_details_'yyyyMMddHHmmss'.xlsx'").format(new Date());

		Path fileStorageLocation = Paths.get(ResponseMessages.BASE_DIRECTORY + ResponseMessages.ASSET_ISSUE_DIRECTORY );
		Files.createDirectories(fileStorageLocation);

		try {
			FileOutputStream out = new FileOutputStream(
					new File(ResponseMessages.BASE_DIRECTORY + ResponseMessages.ASSET_ISSUE_DIRECTORY + filename));
			workbook.write(out);
			out.close();
//			logger.info(filename + " written successfully on disk.");
		} catch (Exception e) {
//			logger.error("Exception occured while saving pincode details" + e.getCause());
			throw e;
		}
		response.put("fileLocation", ResponseMessages.ASSET_ISSUE_DIRECTORY + filename);
		return response;
	 */
}
	
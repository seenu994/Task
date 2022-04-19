package com.xyram.ticketingTool.service.impl;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xyram.ticketingTool.enumType.AssetStatus;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.request.CurrentUser;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jcraft.jsch.Logger;
import com.xyram.ticketingTool.Repository.AssetEmployeeRepository;
import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetEmployee;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.CompanyWings;
import com.xyram.ticketingTool.entity.DateValidatorUsingDateFormat;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.JobVendorDetails;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.service.AssetService;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.util.AssetUtil;
import com.xyram.ticketingTool.util.ExcelUtil;
import com.xyram.ticketingTool.util.ExcelWriter;
import com.xyram.ticketingTool.util.ResponseMessages;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import io.swagger.models.Response;
import io.swagger.models.properties.StringProperty.Format;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {

	@Autowired
	AssetRepository assetRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	AssetEmployeeRepository assetEmployeeRepository;

	@Autowired
	AssetVendorRepository assetVendorRepository;
	
	@Autowired 
	CurrentUser currentUser;

	@Override
	public ApiResponse addasset(Asset asset) {
		ApiResponse response = new ApiResponse(false);
		response = validateAsset(asset); 
		if (response.isSuccess()) {
			if (asset != null) {
			    asset.setCreatedAt(new Date());
//			    asset.setLastUpdatedAt(new Date());
			    asset.setCreatedBy(currentUser.getName());
//     		    asset.setUpdatedBy(currentUser.getName());
				assetRepository.save(asset);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_ADDED);
			}

			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ASSET_NOT_ADDED);
			}
		}

		return response;
	}

	private ApiResponse validateAsset(Asset asset) {
		ApiResponse response = new ApiResponse(false);

//		private String vId; Mandatory
//		private String brand; Mandatory
//		public Date purchaseDate; Mandatory
//		private String modelNo;   Mandatory 
//		private String serialNo; Mandatory
//		private Date warrantyDate; Non Mandatory
//		private String ram; Mandatory
//		private boolean bagAvailable; Non Mandatory
//		private boolean powercordAvailable; Non Mandatory
//		private boolean mouseAvailable; Non Mandatory
//		private String assetPhotoUrl; Mandatory
//		private String assetStatus; Mandatory - BE should update 

		// Validate Vendor
		if (asset.getVendorId() == null || asset.getVendorId().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandatory");
		} else {
			// Validate Vendor
			AssetVendor vendor = assetVendorRepository.getVendorById1(asset.getVendorId());
			if (vendor == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid");
			}
		}

		// Brand Validating
		if (asset.getBrand() == null || asset.getBrand().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand is mandatory");
		} else {
			
			boolean isExist = false;
			// Check given brand is exist in brandList
			for (String brand : AssetUtil.brandList) {
				if(brand.equalsIgnoreCase(asset.getBrand())) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand is not valid");
			}
		}

		// purchase date Validating
		if (asset.getPurchaseDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "purchase date is mandatory");
		} else {
			Date d1 = asset.getPurchaseDate();
			Date d2 = new Date();
			if(d1.after(d2)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "purchase date should be less than current Date");
			}
		}

		// model no Validating
		if (asset.getModelNo() == null || asset.getModelNo().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "model no is mandatory");
		} else {
			// validate model no
			String s1 = asset.getModelNo();
			if (s1.length() < 7) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "model no is not valid");
			}
		}

		// serial no Validating
		if (asset.getSerialNo() == null || asset.getSerialNo().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "serial no is mandatory");
		} else {
			// validate serial no
			if (asset.getSerialNo().length() < 8) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "serial no is not valid");
			}
		}

		// warranty date Validating
		if (asset.getWarrantyDate() != null) {
			Date d1 = asset.getPurchaseDate();
			Date d2 = asset.getWarrantyDate();
			if (d1.after(d2) || d1.equals(d2)) 
				{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"warranty date should be greater than purchase date");
				}
			}

			// ram Validating
			if (asset.getRam() == null || asset.getRam().equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram is mandatory");
			} else {
				boolean isExist1 = false;
				// Check given ram is exist in ramList
				for (String list : AssetUtil.ram) {
					if (list.equalsIgnoreCase(asset.getRam())) {
						isExist1 = true;
						break;
					}
				}
				if (!isExist1) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram is not valid");
				}
			}

			// Validate asset status
			if (asset.getAssetStatus() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset status is mandatory");
			} 
			
			// Validate assignedTo emp_id
//			if (asset.getAssignedTo() != null) {
//				// Validate emp_id
//				              
//				Employee employee = employeeRepository.getByEmpName(asset.getAssignedTo());
//				if (employee == null) {
//					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employee id is not valid");
//				}
//			 }	
			response.setSuccess(true);
			return response;
		}
	

	@Override
	public ApiResponse editAsset(Asset asset,String Id) {

		ApiResponse response = new ApiResponse();

		Asset assetObj = assetRepository.getByAssetId(Id);
		
		if (assetObj != null) {
			
		    if(asset.getVendorId() != null) {
		    	checkVId(asset.getVendorId());
		    	assetObj.setVendorId(asset.getVendorId());
		    }
		    if(asset.getBrand() != null) {
		    	checkBrand(asset.getBrand());
		    	assetObj.setBrand(asset.getBrand());
		    }
		    if(asset.getPurchaseDate() != null) {
		    	assetObj.setPurchaseDate(asset.getPurchaseDate());
		    }
		    if(asset.getWarrantyDate() != null) {
		    	checkWarrantyDate(asset.getWarrantyDate(), Id);
		    	assetObj.setWarrantyDate(asset.getWarrantyDate());
		    }
		    if(asset.getModelNo() != null) {
		    	checkModelNo(asset.getModelNo());
		    	assetObj.setModelNo(asset.getModelNo());
		    }
		    if(asset.getSerialNo() != null) {
		    	checkSerialNo(asset.getSerialNo());
		    	assetObj.setSerialNo(asset.getSerialNo());
		    }
		    if(asset.getRam() != null) {
		    	checkRam(asset.getRam());
		    	assetObj.setRam(asset.getRam());
		    }
		    if(asset.getAssetStatus() != null) {
		    	assetObj.setAssetStatus(asset.getAssetStatus());
		    }
//		    if(asset.getAssignedTo()!= null) {
//		    	checkAssignedTo(asset.getAssignedTo());
//		    	assetObj.setAssignedTo(asset.getAssignedTo());
//		    }
		    assetObj.setBagAvailable(asset.isBagAvailable());
		    assetObj.setMouseAvailable(asset.isMouseAvailable());
		    assetObj.setPowercordAvailable(asset.isPowercordAvailable());
		    
		    assetObj.setLastUpdatedAt(new Date());
		    assetObj.setUpdatedBy(currentUser.getName());
			assetRepository.save(assetObj);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_EDITED);	
		}
		else {
			response.setSuccess(false);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Asset Id");
			
		}
		return response;
	}

	private boolean checkAssignedTo(String assignedTo) {
    	 Employee employee = employeeRepository.getByEmpName(assignedTo);
		 if (employee == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employee id is not valid");
		 }
		 else {
			 return true;
		 }
		
	}

	private boolean checkRam(String ram1) {
    	 boolean isExist1 = false;
			// Check given ram is exist in ramList
			for (String list : AssetUtil.ram) {
			if (list.equalsIgnoreCase(ram1)) {
				isExist1 = true;
			}
			}
			if (!isExist1) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram is not valid");
			}
			else {
				return true;
			}
	}

	private boolean checkSerialNo(String serialNo) {
     String s1 = serialNo;
     if (s1.length() < 8) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "serial no is not valid");
	 }
     else {
    	 return true;
     }
	}
	private boolean checkModelNo(String modelNo) {
    	 String s1 = modelNo;
		 if (s1.length() < 7) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "model no is not valid");
		 }
		 else {
			 return true;
		 }
	}
	
	private boolean checkWarrantyDate(Date warrantyDate,String id) {
		
		Date asset = assetRepository.getPurchaseDateById(id);
		Date d1 = asset;
		Date d2 = warrantyDate;
	  
		if (d1.after(d2) || d1.equals(d2)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "warranty date should be greater than purchase date");
		}
		else {
			return true;
		}
	}

	private boolean checkBrand(String brand) {
    	boolean isExist = false;
		// Check given brand is exist in brandList
		for (String list : AssetUtil.brandList) {
			if (list.equalsIgnoreCase(brand)) {
				isExist = true;
			}
		}
		if (!isExist) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand is not valid");
		}
		else {
			return true;
		}
		
	}
	private boolean checkVId(String vendorId) {
    	AssetVendor vendor = assetVendorRepository.getVendorById1(vendorId);
		if (vendor == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid");
		}
		else {
			return true;
		}
	}

//	@Override
//	public ApiResponse getAllAsset(Pageable pageable) {
//
//		ApiResponse response = new ApiResponse();
//		Page<Map> assets = assetRepository.getAllAsset(pageable);
//		Map content = new HashMap<>();
//		content.put("assets", assets);
//		if(content != null) {
//			response.setSuccess(true);
//			response.setMessage("Asset Retrieved Successfully");
//			response.setContent(content);
//			
//		}
//		else {
//			response.setSuccess(false);
//			response.setMessage("Could not retrieve data");
//		}
//
//		return response;
//	}

//	@Override
//	public ApiResponse searchAsset(String assetId) {
//		ApiResponse response = new ApiResponse();
//		List<Map> assetList = assetRepository.searchAsset(assetId);
//		Map content = new HashMap();
//		content.put("AssetList", assetList);
//		if(content != null) {
//			response.setSuccess(true);
//			response.setMessage("Asset Retrieved successfully");
//			response.setContent(content);
//		}
//		else {
//			response.setSuccess(false);
//			response.setMessage("Could not retrieve data");
//		}
//		return response;
//	}

	@Override
	public ApiResponse getAssetById(String assetId) {
		ApiResponse response = new ApiResponse();
		Asset asset = assetRepository.getAssetById(assetId);
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
	public ApiResponse getAllAssets(Map<String, Object> filter, Pageable pageable) {
		
		ApiResponse response = new ApiResponse(false);
		
		String searchString = filter.containsKey("searchString") ? ((String) filter.get("searchString"))
				: null;
		String ram = filter.containsKey("ram") ? ((String) filter.get("ram"))
				: null;
		String brand = filter.containsKey("brand") ? ((String) filter.get("brand"))
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
				e.printStackTrace();
			}  
		}
		
		if(toDate == null || fromDate == null) {
			response.setMessage("From or To dates are missing");
		}
		
		String assetStatus = filter.containsKey("assetStatus") ? ((String) filter.get("assetStatus")).toUpperCase()
					: null;
		
		AssetStatus status = null;
		if(assetStatus!=null) {
			try {
				status = assetStatus != null ? AssetStatus.toEnum(assetStatus) : null;
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						filter.get("status").toString() + " is not a valid status");
			}
		} 
		
		
				
		Page<Map> asset = assetRepository.getAllAssets(ram, brand, status, vendorId, searchString, fromDateStr, toDateStr, pageable);
		
		if(asset.getSize() > 0) {
			System.out.println(asset);
			Map content = new HashMap();
			content.put("asset", asset);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retrieved successfully");
		}
		else {
			response.setSuccess(false);
			response.setMessage("List is empty");
		}
		return response;
	}

//	@Override
//	public ApiResponse getAssetEmployeeById(String assetId, Pageable pageable) {
//		ApiResponse response = new ApiResponse();
//		List<Map> asset = assetRepository.getAssetEmployeeById(assetId, pageable);
//		System.out.println(asset);
//		Map content = new HashMap();
//		content.put("asset", asset);
//		if(content != null) {
//			response.setSuccess(true);
//			response.setMessage("Asset Employee Retrieved Successfully");
//			response.setContent(content);
//		}
//		else {
//			response.setSuccess(false);
//			response.setMessage("Could not retrieve data");
//		}
//		return response;
//	}

//	@Override
//	public ApiResponse getAssetBillingById(String assetId, Pageable pageable) {
//		ApiResponse response = new ApiResponse();
//		List<Map> asset = assetRepository.getAssetBillingById(assetId, pageable);
////		System.out.println(asset);
//		Map content = new HashMap();
//		content.put("asset", asset);
//		if(content != null) {
//			response.setSuccess(true);
//			response.setMessage("Asset Employee Retrieved Successfully");
//			response.setContent(content);
//		}
//		else {
//			response.setSuccess(false);
//			response.setMessage("Could not retrieve data");
//		}
//		return response;
//	}

//	@Override
//	public ApiResponse getAssetSoftwareById(String assetId, Pageable pageable) {
//		ApiResponse response = new ApiResponse();
//		List<Map> asset = assetRepository.getAssetSoftwareById(assetId, pageable);
////		System.out.println(asset);
//		Map content = new HashMap();
//		content.put("asset", asset);
//		if(content != null) {
//			response.setSuccess(true);
//			response.setMessage("Asset Software Retrieved Successfully");
//			response.setContent(content);
//		}
//		else {
//			response.setSuccess(false);
//			response.setMessage("Could not retrieve data");
//		}
//		return response;
//	}

//	@Override
//	public ApiResponse getAssetIssuesById(String assetId, Pageable pageable) {
//		ApiResponse response = new ApiResponse();
//		List<Map> asset = assetRepository.getAssetIssuesById(assetId, pageable);
////		System.out.println(asset);
//		Map content = new HashMap();
//		content.put("asset", asset);
//		if(content != null) {
//			response.setSuccess(true);
//			response.setMessage("Asset Issues Retrieved Successfully");
//			response.setContent(content);
//		}
//		else {
//			response.setSuccess(false);
//			response.setMessage("Could not retrieve data");
//		}
//		return response;
//	}

	@Override
	public Map downloadAssets(Map<String, Object> filter) throws ParseException, FileUploadException, IOException {
		Map response = new HashMap();
		String ram = filter.containsKey("ram") ? ((String) filter.get("ram"))
				: null;
		String brand = filter.containsKey("brand") ? ((String) filter.get("brand"))
				: null;
		String vendorId = filter.containsKey("vendorId") ? ((String) filter.get("vendorId"))
					: null;
		String assetStatus = filter.containsKey("assetStatus") ? ((String) filter.get("assetStatus")).toUpperCase()
					: null;
		
		AssetStatus status = null;
		if(assetStatus!=null) {
			try {
				status = assetStatus != null ? AssetStatus.toEnum(assetStatus) : null;
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						filter.get("status").toString() + " is not a valid status");
			}
		}
				
		List<Asset> asset = assetRepository.getAllAssetsForDownload(ram, brand, status, vendorId);
		List excelHeaders = Arrays.asList("Asset Id", "Model No", "Brand", "Serial No", "Purchase on", "Warranty Date", "Status", "Ram Size", "Vendor Name", "Assigned To");
		List excelData = new ArrayList<>();
		int index = 1;
		
		for (Asset assetList : asset) {
			Map row = new HashMap();
			AssetVendor getVendorName = assetVendorRepository.getAssetVendorById(assetList.getVendorId());
			row.put("Asset Id", assetList.getAssetId());
			row.put("Brand", assetList.getBrand());
			row.put("Serial No", assetList.getSerialNo());
			row.put("Model No", assetList.getModelNo());
			row.put("Purchase on", assetList.getPurchaseDate());
			row.put("Warranty Date", assetList.getWarrantyDate());
			row.put("Status", assetList.getAssetStatus());
			row.put("Vendor Name", getVendorName.getVendorName());
//			row.put("Assigned To", assetList.getAssignedTo());
			row.put("Ram Size", assetList.getRam());
			

			excelData.add(row);
			index++;
		}

		XSSFWorkbook workbook = ExcelWriter.writeToExcel(excelHeaders, excelData, "Asset Details", null,
				"Asset Details", 1, 0);
		
//        try {
//        	ExcelUtil.saveWorkbook(workbook, "report.xlsx");
//        }
//        catch(IOException e){
//        	e.printStackTrace();
//        }
        
		String filename = new SimpleDateFormat("'asset_details_'yyyyMMddHHmmss'.xlsx'").format(new Date());

		Path fileStorageLocation = Paths.get(ResponseMessages.BASE_DIRECTORY + ResponseMessages.ASSET_DIRECTORY );
		Files.createDirectories(fileStorageLocation);

		try {
			FileOutputStream out = new FileOutputStream(
					new File(ResponseMessages.BASE_DIRECTORY + ResponseMessages.ASSET_DIRECTORY + filename));
			workbook.write(out);
			out.close();
//			logger.info(filename + " written successfully on disk.");
		} catch (Exception e) {
//			logger.error("Exception occured while saving pincode details" + e.getCause());
			throw e;
		}
		response.put("fileLocation", ResponseMessages.ASSET_DIRECTORY + filename);
		return response;
	}
	

//	@Override
//	public ApiResponse getAssetByVendorName(Pageable pageable, String vendorName) {
//		ApiResponse response = new ApiResponse();
//	    AssetVendor vendorId = assetVendorRepository.getVendorIdByVendorName(vendorName);
//	    System.out.println(vendorId);
//	    Page<Map> asset = assetRepository.getAllAsset(pageable, vendorId);
//		Map content = new HashMap();
//		content.put("asset", asset);
//		if(content != null) {
//			response.setSuccess(true);
//			response.setMessage("Asset Retrieved Successfully");
//			response.setContent(content);		
//		}
//		else {
//			response.setSuccess(false);
//			response.setMessage("Could not retrieve data");
//		}
//		return response;
//	}

	
 
}

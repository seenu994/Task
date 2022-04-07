package com.xyram.ticketingTool.service.impl;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xyram.ticketingTool.enumType.AssetStatus;
import com.xyram.ticketingTool.enumType.TicketStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

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
import com.xyram.ticketingTool.util.ResponseMessages;

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

	String[] brandList = { "Lg", "Dell", "Lenovo", "Acer", "Hp" };

	String[] ram = { "2GB", "4GB", "6GB", "8GB", "16GB", "20GB", "32GB", "64GB", "128GB", "256GB" };

	@Override
	public ApiResponse addasset(Asset asset) {
		ApiResponse response = new ApiResponse(false);
		response = validateAsset(asset);
		if (response.isSuccess()) {
			if (asset != null) {
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
			AssetVendor vendor = assetVendorRepository.getVendorById(asset.getVendorId());
			if (vendor == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid");
			}
		}

		// Brand Validating
		if (asset.getBrand() == null || asset.getBrand().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand is mandatory");
		} else {
			boolean exist = false;
			// Check given brand is exist in brandList
			for (String list : brandList) {
				if (list.equalsIgnoreCase(asset.getBrand())) {
					exist = true;
				}
			}
			if (!exist) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand is not valid");
			}

		}

		// purchase date Validating
		if (asset.getPurchaseDate() == null || asset.getPurchaseDate().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "purchase date is mandatory");
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
		if (asset.getWarrantyDate() == null || asset.getWarrantyDate().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "warranty date is mandatory");
		} else {
			Date d1 = asset.getPurchaseDate();
			Date d2 = asset.getWarrantyDate();
			if (d1.after(d2) || d1.equals(d2)) {
				{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"warranty date should be greater than purchase date");
				}
			}

			// ram Validating
			if (asset.getRam() == null || asset.getRam().equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram is mandatory");
			} else {
				boolean exist1 = false;
				// Check given ram is exist in ramList
				for (String list : ram) {
					if (list.equalsIgnoreCase(asset.getRam())) {
						exist1 = true;
					}
				}
				if (!exist1) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram is not valid");
				}
			}

			// Validate asset status
			if (asset.getAssetStatus() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset status is mandatory");
			} 
			

			// Validate assignedTo emp_id
			if (asset.getAssignedTo() != null) {
				// Validate emp_id
				Employee employee = employeeRepository.getByEmpName(asset.getAssignedTo());
				if (employee == null) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employee id is not valid");
				}
			 }	
			
			response.setSuccess(true);
			return response;
		}
	}

	@Override
	public ApiResponse editAsset(Asset asset,String Id) {

		ApiResponse response = new ApiResponse();

		Asset assetObj = assetRepository.getByassetId(Id);
		
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
		    	assetObj.setPurchaseDate(new Date());
		    }
		    if(asset.getWarrantyDate() != null) {
		    	checkWarrantyDate(asset.getWarrantyDate(), Id);
		    	assetObj.setWarrantyDate(new Date());
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
		    if(asset.getAssignedTo()!= null) {
		    	checkAssignedTo(asset.getAssignedTo());
		    	assetObj.setAssignedTo(asset.getAssignedTo());
		    }
		    
			assetRepository.save(assetObj);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_EDITED);
			
		}

		else {
			response.setSuccess(false);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
			
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
    	 boolean exist1 = false;
			// Check given ram is exist in ramList
			for (String list : ram) {
			if (list.equalsIgnoreCase(ram1)) {
				exist1 = true;
			}
			}
			if (!exist1) {
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
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"warranty date should be greater than purchase date");
		}
		else {
			return true;
		}
	}

	private boolean checkBrand(String brand) {
    	boolean exist = false;
		// Check given brand is exist in brandList
		for (String list : brandList) {
			if (list.equalsIgnoreCase(brand)) {
				exist = true;
			}
		}
		if (!exist) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand is not valid");
		}
		else {
			return true;
		}
		
	}
	private boolean checkVId(String getvId) {
    	AssetVendor vendor = assetVendorRepository.getVendorById(getvId);
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

	@Override
	public ApiResponse searchAsset(String assetId) {
		ApiResponse response = new ApiResponse();
//		Asset assetRequest = new Asset();
//		assetRequest.setAssetId(assetId);
		List<Map> assetList = assetRepository.searchAsset(assetId);
		Map content = new HashMap();
		content.put("AssetList", assetList);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset Retrieved successfully");
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}

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
				
		Page<Map> asset = assetRepository.getAllAssets(ram, brand, status, vendorId, pageable);
		
		
		if(asset.getSize() > 0) {
			Map content = new HashMap();
			content.put("asset", asset);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retreived successfully.");
		}else {
			response.setSuccess(false);
			response.setMessage("List is empty.");
		}
		return response;
	}

	@Override
	public ApiResponse getAssetEmployeeById(String assetId, Pageable pageable) {
		ApiResponse response = new ApiResponse();
		List<Map> asset = assetRepository.getAssetEmployeeById(assetId, pageable);
		System.out.println(asset);
		Map content = new HashMap();
		content.put("asset", asset);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset Employee Retrieved Successfully");
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}

	@Override
	public ApiResponse getAssetBillingById(String assetId, Pageable pageable) {
		ApiResponse response = new ApiResponse();
		List<Map> asset = assetRepository.getAssetBillingById(assetId, pageable);
		System.out.println(asset);
		Map content = new HashMap();
		content.put("asset", asset);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset Employee Retrieved Successfully");
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
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

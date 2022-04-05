package com.xyram.ticketingTool.service.impl;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.AssetStatusRepository;
import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetStatus;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.CompanyWings;
import com.xyram.ticketingTool.entity.DateValidatorUsingDateFormat;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.service.AssetService;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.util.ResponseMessages;

import io.swagger.models.properties.StringProperty.Format;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {

	@Autowired
	AssetRepository assetRepository;
	
	@Autowired
	AssetServiceImpl assetServiceImpl;

	@Autowired
	AssetService assetService;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	AssetVendorRepository assetVendorRepository;

	@Autowired
	AssetStatusRepository assetStatusRepository;

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
		if (asset.getAssetId() == null || asset.getAssetId().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandatory");
		} else {
			// Validate Vendor
			AssetVendor vendor = assetVendorRepository.getVendorById(asset.getAssetId());
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
			if (asset.getAssetStatus() == null || asset.getAssetStatus().equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset status is mandatory");
			} else {
				// Validate asset status
				AssetStatus status = assetStatusRepository.getByStatusName(asset.getAssetStatus());
				if (status == null) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset status is not valid");
				}
			}

			// Validate assignedTo emp_id
			if (asset.getAssignedTo() != null) {
				// Validate emp_id
				Employee employee = employeeRepository.getByEmpName(asset.getAssignedTo());
				if (employee == null) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employee id is not valid");
				}
			 }
//				Asset asset1 = assetRepository.getByAssignedTo(asset.getAssignedTo());
//				if(asset1 != null) {
//					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "already asset is assigned to that employee");
//				}
//			}
			response.setSuccess(true);

			return response;
		}
	}

	@Override
	public ApiResponse editAsset(Asset asset,String Id) {

		ApiResponse response = new ApiResponse(false);

		Asset assetObj = assetRepository.getByassetId(Id);
		
		if (assetObj != null) {
			
			//if (response.isSuccess()) {
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
		    	checkWarrantyDate(asset.getWarrantyDate());
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
		    	checkAssetStatus(asset.getAssetStatus());
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

	private boolean checkAssetStatus(String assetStatus) {
    	 AssetStatus status = assetStatusRepository.getByStatusName(assetStatus);
			if (status == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset status is not valid");
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
	
	private boolean checkWarrantyDate(Date warrantyDate) {
		
		
//		Asset purchaseDate = assetRepository.getByPurchaseDate(Id);
//		Date d1 = purchaseDate;
//		Date d2 = warrantyDate;
//		if (d1.after(d2) || d1.equals(d2)) {
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//						"warranty date should be greater than purchase date");
//		}
//		else {
//			return true;
//		}
		return true;
	
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

	@Override
	public ApiResponse getAllAssets(Pageable pageable) {

		ApiResponse apiResponse = new ApiResponse();

		
		Page<Map> assets = assetRepository.getAllAsset(pageable);
		Map content = new HashMap<>();
		content.put("assets", assets);
		apiResponse.setContent(content);

		return apiResponse;

	}

	/*
	 * @Override public ApiResponse searchAsset(String searchString) {
	 * 
	 * ApiResponse response = new ApiResponse();
	 * 
	 * Object assetId = null; List<Map> assetList =
	 * assetRepository.searchAsset(assetId, searchString);
	 * response.setContent1(assetList); response.setStatus("success"); return
	 * response;
	 * 
	 * }
	 * 
	 * @Override public ApiResponse searchAsset(String aid) { ApiResponse response =
	 * new ApiResponse(false); Asset assetRequest = new Asset();
	 * assetRequest.setaId(aid); List<Map> assetList =
	 * assetRepository.searchAsset(aid); Map content = new HashMap();
	 * 
	 * content.put("AssetList", assetList); response.setSuccess(true);
	 * response.setContent(content); return response; }
	 * 
	 * 
	 */
}

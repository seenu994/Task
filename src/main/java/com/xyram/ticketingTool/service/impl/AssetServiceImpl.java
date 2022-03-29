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
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetStatus;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.DateValidatorUsingDateFormat;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.JobOpenings;
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
	AssetService assetService;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	AssetVendorRepository assetVendorRepository;

	@Autowired
	AssetStatusRepository assetStatusRepository;

	String[] brandList = { "Lg", "Dell", "Lenovo", "Acer" };
	

	String[] ram = { "2GB", "4GB", "8GB", "16GB", "20GB", "32GB", "64GB", "128GB", "256GB" };

	//String[] status = { "Available", "Alotted", "Repair", "Damaged/Broken" };

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

//				else {
//				response.setSuccess(false);
//				response.setMessage(ResponseMessages.ASSET_NOT_ADDED);
//			}
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
		if (asset.getvId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is mandatory");
		} else {
			// Validate Vendor
			AssetVendor vendor = assetVendorRepository.getById(asset.getvId());
			if (vendor == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vendor id is not valid");
			}
		}

		// Brand Validating
		if (asset.getBrand() == null) {
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
		if (asset.getPurchaseDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "purchase date is mandatory");
		}

		// model no Validating
		if (asset.getModelNo() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "model no is mandatory");
		} else {
			// validate model no
			if (asset.getModelNo().length() > 7) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "model no is not valid");
			}
		}

		// serial no Validating
		if (asset.getSerialNo() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "serial no is mandatory");
		} else {
			// validate serial no
			if (asset.getSerialNo().length() > 8) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "serial no is not valid");
			}
		}

		// warranty date Validating
		if (asset.getWarrantyDate() == null) {
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
			if (asset.getSerialNo() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ram is mandatory");
			} else {
				boolean exist1 = false;
				// Check given brand is exist in brandList
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
			} else {
				// Validate asset status
				AssetStatus status = assetStatusRepository.getById(asset.getAssetStatus());
				if (status == null) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset status is not valid");
				}
			}

			response.setSuccess(true);

			return response;
		}

		/*
		 * @Override public ApiResponse getAllAssets(Pageable pageable) {
		 * 
		 * return (ApiResponse) assetRepository.getAllAssets(pageable); }
		 * 
		 * 
		 * @Override public ApiResponse editAsset(Asset asset) { ApiResponse response =
		 * new ApiResponse(false);
		 * 
		 * 
		 * Asset assetObj = assetRepository.getById(asset.getaId());
		 * 
		 * 
		 * if(assetObj != null) { try {
		 * 
		 * assetRepository.save(assetObj);
		 * 
		 * response.setSuccess(true);
		 * response.setMessage(ResponseMessages.ASSET_EDITED);
		 * 
		 * }catch(Exception e) { response.setSuccess(false);
		 * response.setMessage(ResponseMessages.ASSET_NOT_EDITED); } }else {
		 * response.setSuccess(false);
		 * response.setMessage(ResponseMessages.ASSET_NOT_EDITED); }
		 * 
		 * return response; }
		 * 
		 * 
		 * 
		 * 
		 * /*
		 * 
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
		 */
		/*
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
}

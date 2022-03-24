package com.xyram.ticketingTool.service.impl;

import java.awt.print.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.AssetIssuesRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.service.AssetIssuesService;

@Service
@Repository
public class AssetIssuesServiceImpl implements AssetIssuesService
{
	@Autowired
	AssetIssuesRepository  assetIssuesRepository;

	@Override
	public ApiResponse addAssetIssues(AssetIssues assetIssues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse editAssetIssues(AssetIssues assetIssues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse getAssetIssues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse searchAssetIssues(String issueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse changeAssetIssuesStatus(String Status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse getAllAssetIssues(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}

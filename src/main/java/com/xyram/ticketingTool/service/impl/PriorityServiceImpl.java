package com.xyram.ticketingTool.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.PriorityRepository;
import com.xyram.ticketingTool.entity.Priority;
import com.xyram.ticketingTool.service.PriorityService;
@Service
@Transactional
public class PriorityServiceImpl implements PriorityService {

	@Autowired
	PriorityRepository priorityRepository;
	@Override
	public Page<Priority> getAllPriority(Pageable pageable) {
		// TODO Auto-generated method stub
		return priorityRepository.findAll(pageable);
	}

}

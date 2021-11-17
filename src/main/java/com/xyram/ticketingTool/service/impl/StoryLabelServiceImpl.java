package com.xyram.ticketingTool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.StoryLabelRepository;
import com.xyram.ticketingTool.entity.StoryLabel;
import com.xyram.ticketingTool.service.StoryLabelService;

@Service
public class StoryLabelServiceImpl implements StoryLabelService {

	@Autowired
	StoryLabelRepository storyLabelRepository;

	@Override
	public StoryLabel createStoryLabel(String id, StoryLabel storylabel) {

		if (storylabel.getProjectid() != null) {
			if (storylabel.getLabelName() != null) {
				storylabel.setLabelName(storylabel.getLabelName());
				return storyLabelRepository.save(storylabel);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label cannot be empty");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project id not found");
		}
		// TODO Auto-generated method stub

	}

	@Override
	public StoryLabel updateStoryLabel(String id, StoryLabel storylabel) {
		// TODO Auto-generated method stub
		if (storylabel.getId() != null) {
			storylabel.setLabelName(storylabel.getLabelName());
			return storyLabelRepository.save(storylabel);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project id not found");
		}
	}

	@Override
	public List<StoryLabel> getStoryLabel() {
		// TODO Auto-generated method stub
	return	storyLabelRepository.findAll();
		
	}

}

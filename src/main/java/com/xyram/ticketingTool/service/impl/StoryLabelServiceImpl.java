package com.xyram.ticketingTool.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.StoryLabelRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.StoryLabel;
import com.xyram.ticketingTool.service.StoryLabelService;

@Service
public class StoryLabelServiceImpl implements StoryLabelService {

	@Autowired
	StoryLabelRepository storyLabelRepository;

	@Override
	public StoryLabel createStoryLabel(StoryLabel storylabel) {

		if (storylabel.getProjectId() != null) {
			if (storylabel.getLabelName() != null) {
				storylabel.setLabelName(storylabel.getLabelName());
				return storyLabelRepository.save(storylabel);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label cannot be empty");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project id is mandatory");
		}
		// TODO Auto-generated method stub

	}

	@Override
	public StoryLabel updateStoryLabel(String id, StoryLabel storylabel) {

		if (storylabel.getId() != null) {
			storylabel.setLabelName(storylabel.getLabelName());
			return storyLabelRepository.save(storylabel);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " id not found");
		}
	}

	@Override
	public List<StoryLabel> getStoryLabel() {
		// TODO Auto-generated method stub
		return storyLabelRepository.findAll();

	}

	@Override
	public IssueTrackerResponse getStoryLabelByProjectId(String projectId)

	{

		IssueTrackerResponse response = new IssueTrackerResponse();
		List<Map> storyLabelList = storyLabelRepository.getStoryLabelByproject(projectId);

		response.setContent(storyLabelList);

		response.setStatus("success");

		return response;

	}

}

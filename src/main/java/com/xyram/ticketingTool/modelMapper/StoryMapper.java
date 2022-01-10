package com.xyram.ticketingTool.modelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.xyram.ticketingTool.entity.Story;
import com.xyram.ticketingTool.vo.StoryVo;

@Component
public class StoryMapper {

	/**
	 * <p>
	 * This method is responsible to convert StoryVo.java to Story object
	 * 
	 * @method getEntityFromVo
	 * @param StoryVo
	 * @return Story object
	 * @see Story
	 */

	public Story getEntityFromVo(StoryVo storyVo) {

		ModelMapper StoryModelMapper = new ModelMapper();

		StoryModelMapper.getConfiguration().setAmbiguityIgnored(true);
		Story story = null;

		PropertyMap<StoryVo, Story> storyVoToEntityPropertyMap = new PropertyMap<StoryVo, Story>() {

			protected void configure() {

				map().setTitle(source.getTitle());
				map().setStoryDescription(source.getStoryDescription());
				map().setStoryLabel(source.getStoryLabel());
				map().setProjectId(source.getProjectId());
				map().setStoryType(source.getStoryType());
				map().setStoryStatus(source.getStoryStatus());
				map().setAssignTo(source.getAssignTo());
				map().setPlatform(source.getPlatform());
				map().setSprintId(source.getSprintId());

			}
		};
		StoryModelMapper.addMappings(storyVoToEntityPropertyMap);
		story = StoryModelMapper.map(storyVo, Story.class);

		return story;

	}
}
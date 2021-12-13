package com.xyram.ticketingTool.modelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.xyram.ticketingTool.entity.Story;
import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.vo.StoryCommentVo;
import com.xyram.ticketingTool.vo.StoryVo;

@Component
public class StoryCommentMapper {

	
	/**
	 * <p>
	 * This method is responsible to convert StoryCommentVo.java to StoryComments object
	 * 
	 * @method getEntityFromVo
	 * @param StoryCommentVo
	 * @return StoryComments object
	 * @see StoryComments
	 */

	public StoryComments getEntityFromVo(StoryCommentVo storyCommentVo) {

		ModelMapper StoryModelMapper = new ModelMapper();

		StoryComments storyComments = null;
		StoryModelMapper.getConfiguration().setAmbiguityIgnored(true);

		PropertyMap<StoryCommentVo, StoryComments> storyVoToEntityPropertyMap = new PropertyMap<StoryCommentVo, StoryComments>() {

			protected void configure() {

				map().setDescription(source.getDescription());
				map().setProjectId(source.getProjectId());
				map().setStoryId(source.getStoryId());
				map().setMentionTo(source.getMentionTo());

			}
		};
		StoryModelMapper.addMappings(storyVoToEntityPropertyMap);
		storyComments = StoryModelMapper.map(storyCommentVo, StoryComments.class);

		return storyComments;

	}
}

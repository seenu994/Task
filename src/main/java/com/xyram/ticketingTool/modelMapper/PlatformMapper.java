package com.xyram.ticketingTool.modelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.vo.PlatformVo;

@Component
public class PlatformMapper {

	
	/**
	 * <p>
	 * This method is responsible to convert plaftormVo.java to Story object
	 * 
	 * @method getEntityFromVo
	 * @param platformVo
	 * @return Platform object
	 * @see platform
	 */

	public Platform getEntityFromVo(PlatformVo platformVo) {

		ModelMapper StoryModelMapper = new ModelMapper();

		Platform platform = null;

		PropertyMap<PlatformVo, Platform> storyVoToEntityPropertyMap = new PropertyMap<PlatformVo, Platform>() {

			protected void configure() {

				map().setPlatformName(source.getPlatformName());
				map().setProjectId(source.getProjectId());

			}
		};
		StoryModelMapper.addMappings(storyVoToEntityPropertyMap);
		platform = StoryModelMapper.map(platformVo, Platform.class);

		return platform;

	}
}

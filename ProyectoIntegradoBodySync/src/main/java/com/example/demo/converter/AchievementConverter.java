package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.model.AchievementModel;

@Component("achievementConverter")
public class AchievementConverter {

	public AchievementConverter transform(AchievementModel achievement) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(achievement, AchievementConverter.class);
	}
	
	public AchievementModel transform(AchievementConverter achievement) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(achievement, AchievementModel.class);
	}
	

}

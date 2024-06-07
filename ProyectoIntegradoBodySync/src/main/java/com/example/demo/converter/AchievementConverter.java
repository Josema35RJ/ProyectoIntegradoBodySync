package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Achievement;
import com.example.demo.model.AchievementModel;

@Component("achievementConverter")
public class AchievementConverter {

	public Achievement transform(AchievementModel achievement) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(achievement, Achievement.class);
	}
	
	public AchievementModel transform(Achievement achievement) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(achievement, AchievementModel.class);
	}
	

}

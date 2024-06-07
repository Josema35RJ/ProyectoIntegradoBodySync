package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.MealLog;
import com.example.demo.model.MealLogModel;

@Component("mealLogConverter")
public class MealLogConverter {

	public MealLog transform(MealLogModel mealLog) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(mealLog, MealLog.class);
	}
	
	public MealLogModel transform(MealLog mealLog) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(mealLog, MealLogModel.class);
	}
	

}

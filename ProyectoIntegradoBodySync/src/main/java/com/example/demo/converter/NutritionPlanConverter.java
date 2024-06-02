package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.NutritionPlan;
import com.example.demo.model.NutritionPlanModel;

@Component("nutritionPlanConverter")
public class NutritionPlanConverter {

	public NutritionPlan transform(NutritionPlanModel nutritionPlan) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(nutritionPlan, NutritionPlan.class);
	}
	
	public NutritionPlanModel transform(NutritionPlan nutritionPlan) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(nutritionPlan, NutritionPlanModel.class);
	}
	

}

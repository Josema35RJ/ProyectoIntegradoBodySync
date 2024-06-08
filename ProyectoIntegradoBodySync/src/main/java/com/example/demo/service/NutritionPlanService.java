package com.example.demo.service;

import java.util.List;

import com.example.demo.model.NutritionPlanModel;

public interface NutritionPlanService {
	List<NutritionPlanModel> ListAllNutritionPlan();

	void update(NutritionPlanModel plan);

	void save(NutritionPlanModel plan);

	NutritionPlanModel getNutritionPlan(int id);
}


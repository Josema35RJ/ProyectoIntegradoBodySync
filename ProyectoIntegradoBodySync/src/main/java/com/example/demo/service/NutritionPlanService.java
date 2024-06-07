package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.NutritionPlan;
import com.example.demo.model.NutritionPlanModel;

public interface NutritionPlanService {
	List<NutritionPlan> ListAllNutritionPlan();

	void update(NutritionPlan plan);

	void save(NutritionPlan plan);

	NutritionPlanModel getNutritionPlan(int id);
}


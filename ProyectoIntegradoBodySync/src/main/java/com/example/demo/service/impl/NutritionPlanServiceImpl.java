package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.NutritionPlanConverter;
import com.example.demo.entity.NutritionPlan;
import com.example.demo.model.NutritionPlanModel;
import com.example.demo.repository.NutritionPlanRepository;
import com.example.demo.service.NutritionPlanService;

@Service("nutritionPlanService")
public class NutritionPlanServiceImpl implements NutritionPlanService {

	  @Autowired
	    @Qualifier("nutritionPlanRepository")
	    private NutritionPlanRepository nutritionPlanRepository;
	  
	  @Autowired
	    @Qualifier("nutritionPlanConverter")
	    private NutritionPlanConverter nutritionPlanConverter;
	  
	@Override
	public List<NutritionPlan> ListAllNutritionPlan() {
		// TODO Auto-generated method stub
		
		return nutritionPlanRepository.findAll();
	}

	@Override
    public void save(NutritionPlan plan) {
        nutritionPlanRepository.save(plan);
    }

    @Override
    public void update(NutritionPlan plan) {
        nutritionPlanRepository.save(plan); 
    }

	@Override
	public NutritionPlanModel getNutritionPlan(int id) {
		// TODO Auto-generated method stub
		return nutritionPlanConverter.transform(nutritionPlanRepository.findById(id));
	}

   
}

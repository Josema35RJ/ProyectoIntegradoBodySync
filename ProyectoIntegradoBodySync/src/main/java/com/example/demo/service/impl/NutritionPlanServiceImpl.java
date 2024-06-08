package com.example.demo.service.impl;

import java.util.ArrayList;
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
	public List<NutritionPlanModel> ListAllNutritionPlan() {
		// TODO Auto-generated method stub
		List<NutritionPlanModel> l = new ArrayList<>();
		for(NutritionPlan n : nutritionPlanRepository.findAll()) {
			l.add(nutritionPlanConverter.transform(n));
		}
		return l;
	}

	@Override
    public void save(NutritionPlanModel plan) {
        nutritionPlanRepository.save(nutritionPlanConverter.transform(plan));
    }

    @Override
    public void update(NutritionPlanModel plan) {
        nutritionPlanRepository.save(nutritionPlanConverter.transform(plan)); 
    }

	@Override
	public NutritionPlanModel getNutritionPlan(int id) {
		// TODO Auto-generated method stub
		return nutritionPlanConverter.transform(nutritionPlanRepository.findById(id));
	}

   
}

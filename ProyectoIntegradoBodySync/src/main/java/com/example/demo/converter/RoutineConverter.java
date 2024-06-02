package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.NutritionPlan;
import com.example.demo.entity.Routine;
import com.example.demo.model.RoutineModel;

@Component("routineConverter")
public class RoutineConverter {

	public Routine transform(RoutineModel routine) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(routine, Routine.class);
	}
	
	public RoutineModel transform(Routine routine) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(routine, RoutineModel.class);
	}
	

}

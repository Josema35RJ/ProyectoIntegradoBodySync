package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Workout;
import com.example.demo.model.WorkoutModel;

@Component("workoutConverter")
public class WorkoutConverter {

	public Workout transform(WorkoutModel workout) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(workout, Workout.class);
	}
	
	public WorkoutModel transform(Workout workout) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(workout, WorkoutModel.class);
	}
	

}

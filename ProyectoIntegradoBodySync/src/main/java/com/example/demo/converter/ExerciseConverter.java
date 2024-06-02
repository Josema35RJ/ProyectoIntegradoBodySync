package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Exercise;
import com.example.demo.model.ExerciseModel;

@Component("exerciseConverter")
public class ExerciseConverter {

	public Exercise transform(ExerciseModel exercise) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(exercise, Exercise.class);
	}
	
	public ExerciseModel transform(Exercise exercise) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(exercise, ExerciseModel.class);
	}
	

}

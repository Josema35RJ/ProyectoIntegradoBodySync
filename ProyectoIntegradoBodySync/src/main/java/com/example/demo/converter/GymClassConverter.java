package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.GymClass;
import com.example.demo.model.GymClassModel;

@Component("gymClassConverter")
public class GymClassConverter {

	public GymClass transform(GymClassModel gymClass) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(gymClass, GymClass.class);
	}
	
	public GymClassModel transform(GymClass gymClass) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(gymClass, GymClassModel.class);
	}
	

}

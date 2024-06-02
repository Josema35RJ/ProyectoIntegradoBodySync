package com.example.demo.converter;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import com.example.demo.entity.GymUser;
import com.example.demo.model.GymUserModel;

@Component("gymUserConverter")
public class GymUserConverter {

	
    
	public GymUser transform(GymUserModel gymUserModel) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(gymUserModel, GymUser.class);
	}
	
	public GymUserModel transform(GymUser gymUser) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(gymUser, GymUserModel.class);
	}
	

}

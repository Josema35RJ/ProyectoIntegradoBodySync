package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserInjury;
import com.example.demo.model.UserInjuryModel;

@Component("userInjuryConverter")
public class UserInjuryConverter {
	public UserInjury transform(UserInjuryModel userInjuryModel) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userInjuryModel, UserInjury.class);
	}
	
	public UserInjuryModel transform(UserInjury userInjury) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userInjury, UserInjuryModel.class);
	}
}

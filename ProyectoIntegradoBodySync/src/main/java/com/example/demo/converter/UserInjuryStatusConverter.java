package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserInjuryStatus;
import com.example.demo.model.UserInjuryStatusModel;

@Component("userInjuryStatusConverter")
public class UserInjuryStatusConverter {

	public UserInjuryStatus transform(UserInjuryStatusModel userInjuryStatus) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userInjuryStatus, UserInjuryStatus.class);
	}
	
	public UserInjuryStatusModel transform(UserInjuryStatus userInjuryStatus) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userInjuryStatus, UserInjuryStatusModel.class);
	}
	

}

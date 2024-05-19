package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Speciality;
import com.example.demo.model.SpecialityModel;

@Component("specialityConverter")
public class SpecialityConverter {

	public Speciality transform(SpecialityModel speciality) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(speciality, Speciality.class);
	}
	
	public SpecialityModel transform(Speciality speciality) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(speciality, SpecialityModel.class);
	}
	

}

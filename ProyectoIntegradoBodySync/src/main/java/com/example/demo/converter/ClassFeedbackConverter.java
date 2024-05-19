package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.ClassFeedback;
import com.example.demo.model.ClassFeedbackModel;

@Component("classFeedbackConverter")
public class ClassFeedbackConverter {

	public ClassFeedback transform(ClassFeedbackModel classFeedback) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(classFeedback, ClassFeedback.class);
	}
	
	public ClassFeedbackModel transform(ClassFeedback classFeedback) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(classFeedback, ClassFeedbackModel.class);
	}
	

}

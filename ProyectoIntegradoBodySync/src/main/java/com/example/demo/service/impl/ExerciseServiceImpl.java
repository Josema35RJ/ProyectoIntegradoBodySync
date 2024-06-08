package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.ExerciseConverter;
import com.example.demo.entity.Exercise;
import com.example.demo.model.ExerciseModel;
import com.example.demo.repository.ExerciseRepository;
import com.example.demo.service.ExerciseService;

@Service("exerciseService")
public class ExerciseServiceImpl implements ExerciseService {

	  @Autowired
	    @Qualifier("exerciseRepository")
	    private ExerciseRepository exerciseRepository;
	  
	  @Autowired
	    @Qualifier("exerciseConverter")
	    private ExerciseConverter exerciseConverter;

	@Override
	public List<ExerciseModel> ListExercise() {
		// TODO Auto-generated method stub
		List<ExerciseModel> l = new ArrayList<>();
		for(Exercise e: exerciseRepository.findAll()) {
			l.add(exerciseConverter.transform(e));
		}
		return l;
	}

	@Override
	public void create(ExerciseModel exercise) {
		// TODO Auto-generated method stub
		exerciseRepository.save(exerciseConverter.transform(exercise));
	}

	@Override
	public void update(ExerciseModel exercise) {
		// TODO Auto-generated method stub
		exerciseRepository.save(exerciseConverter.transform(exercise));
	}

	@Override
	public ExerciseModel getExercise(int id) {
		
	    return exerciseConverter.transform(exerciseRepository.findById(id).get());
	}

}

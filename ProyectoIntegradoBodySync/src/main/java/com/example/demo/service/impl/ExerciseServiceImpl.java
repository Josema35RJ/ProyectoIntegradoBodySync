package com.example.demo.service.impl;

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
	public List<Exercise> ListExercise() {
		// TODO Auto-generated method stub
		return exerciseRepository.findAll();
	}

	@Override
	public void create(Exercise exercise) {
		// TODO Auto-generated method stub
		exerciseRepository.save(exercise);
	}

	@Override
	public void update(Exercise exercise) {
		// TODO Auto-generated method stub
		exerciseRepository.save(exercise);
	}

	@Override
	public Exercise getExercise(int id) {
		
	    return exerciseRepository.findById(id).get();
	}

}

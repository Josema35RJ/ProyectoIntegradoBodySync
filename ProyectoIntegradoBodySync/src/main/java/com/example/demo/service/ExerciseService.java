package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Exercise;
import com.example.demo.model.ExerciseModel;

public interface ExerciseService {

	 List<ExerciseModel> ListExercise ();

	void create(ExerciseModel exercise);

	void update(ExerciseModel exercise);

	 ExerciseModel getExercise(int id);

}

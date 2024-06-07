package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Exercise;
import com.example.demo.model.ExerciseModel;

public interface ExerciseService {

	 List<Exercise> ListExercise ();

	void create(Exercise exercise);

	void update(Exercise exercise);

	 ExerciseModel getExercise(int id);

}

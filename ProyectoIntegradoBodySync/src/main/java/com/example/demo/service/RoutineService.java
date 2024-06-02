package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Routine;
import com.example.demo.model.GymUserModel;

public interface RoutineService {
	Routine findByGymUser (GymUserModel gymUserModel);
	 List<Routine> ListRoutine ();
	void update(Routine routine);
	void create(Routine routine);
	Routine getById(int id);

}

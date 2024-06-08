package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Routine;
import com.example.demo.model.GymUserModel;
import com.example.demo.model.RoutineModel;

public interface RoutineService {
	RoutineModel findByGymUser (GymUserModel gymUserModel);
	 List<RoutineModel> ListRoutine ();
	void update(RoutineModel routine);
	void create(RoutineModel routine);
	RoutineModel getById(int id);

}

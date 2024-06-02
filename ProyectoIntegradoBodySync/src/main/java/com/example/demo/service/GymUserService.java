 package com.example.demo.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.example.demo.entity.GymUser;
import com.example.demo.entity.Routine;
import com.example.demo.model.ExerciseModel;
import com.example.demo.model.GymUserModel;

public interface GymUserService {

	 List<GymUserModel> ListAllGymUsers();
	 public List<GymUserModel> ListAllGymUsersInstructores();
	 public int calculateAge(Date birthDate);
	 public float calculateBMI(GymUserModel user);
	 public LocalDate convertToLocalDateViaInstant(Date dateToConvert);
	   public GymUser registrar(GymUserModel gymUserModel);
	   public boolean existeUsername(String email);
	   public boolean activarDesactivar(int gymUserId);
	   public boolean eliminarGymUser(int id);
	   public GymUserModel getGymUserById(int id);
	   GymUser updateUser (GymUserModel gymUserModel);
	   List<String> getEmails();
	   List<GymUser> ListGymUsersByClassId(int classId);
	List<ExerciseModel> ListExercisesModelByGymUser(int id);
	void setListExercisesModelByGymUser(List<ExerciseModel> exercises,int  id);
	void addRoutineToUser(Integer userId, Routine routine);

}

package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.GymUser;
import com.example.demo.model.GymUserModel;

public interface InstructorService {
	 List<GymUserModel> getAllInstructors();
    GymUserModel getInstructorById(int id);
    void createInstructor(GymUserModel instructor);
    void updateInstructor( GymUserModel instructor);
    void deleteInstructor(int id);
}

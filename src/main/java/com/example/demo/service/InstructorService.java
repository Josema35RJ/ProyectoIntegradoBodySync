package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.GymUser;

public interface InstructorService {
    List<GymUser> getAllInstructors();
    GymUser getInstructorById(int id);
    void createInstructor(GymUser instructor);
    void updateInstructor( GymUser instructor);
    void deleteInstructor(int id);
}

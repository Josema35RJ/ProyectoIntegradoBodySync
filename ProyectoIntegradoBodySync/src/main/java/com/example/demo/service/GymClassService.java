package com.example.demo.service;

import java.util.List;

import com.example.demo.model.GymClassModel;
import com.example.demo.model.GymUserModel;

public interface GymClassService {
    List<GymClassModel> getAllClasses();
   List<GymClassModel>getAllClassesFinByInstructorId(GymUserModel instructor);
    GymClassModel getClassById(int id);
    void addClass(GymClassModel gymClass);
   void updateClass(GymClassModel gymClass);
   void assignInstructor(int classId, int instructorId);
   List<GymClassModel> obtenerDatosAsistencia();
   List<GymClassModel> obtenerDatosPopularidad();
    void deleteClass(int id);

}

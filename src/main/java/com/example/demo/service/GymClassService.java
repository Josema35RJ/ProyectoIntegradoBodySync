package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.GymClass;
import com.example.demo.entity.GymUser;
import com.example.demo.model.GymUserModel;

public interface GymClassService {
    List<GymClass> getAllClasses();
   List<GymClass>getAllClassesFinByInstructorId(GymUser instructor);
    GymClass getClassById(int id);
    void addClass(GymClass gymClass);
   void updateClass(GymClass gymClass);
   void assignInstructor(int classId, int instructorId);
   List<GymClass> obtenerDatosAsistencia();
   List<GymClass> obtenerDatosPopularidad();
    void deleteClass(int id);

}

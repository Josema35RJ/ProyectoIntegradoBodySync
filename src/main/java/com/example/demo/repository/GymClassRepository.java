package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GymClass;
import com.example.demo.entity.GymUser;

@Repository("gymClassRepository")
public interface GymClassRepository extends JpaRepository<GymClass, Serializable> {
    // Aquí puedes agregar métodos personalizados si los necesitas
	List<GymClass> findByInstructorId (int id );
}
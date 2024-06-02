package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GymUser;
import com.example.demo.entity.Routine;

@Repository("routineRepository")
public interface RoutineRepository extends JpaRepository<Routine, Integer> {

	Routine findByGymUser(GymUser gymUser);

}

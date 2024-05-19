package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GymUser;

@Repository("instructorRepository")
public interface InstructorRepository extends JpaRepository<GymUser, Serializable> {
	List<GymUser>findByDeletedAndRole(boolean x,String role);
}


package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ClassFeedback;
import com.example.demo.entity.GymClass;

@Repository("classFeedbackRepository")
public interface ClassFeedbackRepository extends JpaRepository<ClassFeedback, Serializable> {

	List<ClassFeedback> findByGymClass(GymClass gymClass);

}

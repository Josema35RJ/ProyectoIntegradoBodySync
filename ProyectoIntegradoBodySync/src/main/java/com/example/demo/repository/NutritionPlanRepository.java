package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GymUser;
import com.example.demo.entity.NutritionPlan;

@Repository("nutritionPlanRepository")
public interface NutritionPlanRepository  extends JpaRepository<NutritionPlan, Serializable>{
      NutritionPlan findByGymUser (GymUser gymUser);
      NutritionPlan findById(int id);
}

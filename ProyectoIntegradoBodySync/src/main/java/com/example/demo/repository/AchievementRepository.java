package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Achievement;

@Repository("achievementRepository")
public interface AchievementRepository  extends JpaRepository<Achievement, Serializable>{
	Achievement findById (int id);
}

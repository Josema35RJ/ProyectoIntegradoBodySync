package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Achievement;

public interface AchievementService {
    List<Achievement> ListAchievement ();
	void save(Achievement achievement);
	void update(Achievement achievement);
	void deleteAchievement(Achievement achievement);
	Achievement getById(int id);
}


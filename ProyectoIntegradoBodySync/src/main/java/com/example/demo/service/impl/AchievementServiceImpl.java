package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.AchievementConverter;
import com.example.demo.entity.Achievement;
import com.example.demo.repository.AchievementRepository;
import com.example.demo.service.AchievementService;

@Service("achievementService")
public class AchievementServiceImpl implements AchievementService {

	  @Autowired
	    @Qualifier("achievementRepository")
	    private AchievementRepository achievementRepository;
	  
	  @Autowired
	    @Qualifier("achievementConverter")
	    private AchievementConverter achievementConverter;

	@Override
	public List<Achievement> ListAchievement() {
		// TODO Auto-generated method stub
		return achievementRepository.findAll();
	}

	@Override
	public void save(Achievement achievement) {
		// TODO Auto-generated method stub
		achievementRepository.save(achievement);
	}

	@Override
	public void update(Achievement achievement) {
		// TODO Auto-generated method stub
		achievementRepository.save(achievement);
	}

	@Override
	public void deleteAchievement(Achievement achievement) {
		// TODO Auto-generated method stub
		achievementRepository.delete(achievement);
	}

	@Override
	public Achievement getById(int id) {
		// TODO Auto-generated method stub
		return achievementRepository.findById(id);
	}
   
}

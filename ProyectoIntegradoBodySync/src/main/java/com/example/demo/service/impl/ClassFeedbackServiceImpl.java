package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.ClassFeedbackConverter;
import com.example.demo.converter.GymUserConverter;
import com.example.demo.entity.ClassFeedback;
import com.example.demo.repository.ClassFeedbackRepository;
import com.example.demo.repository.GymClassRepository;
import com.example.demo.repository.GymUserRepository;
import com.example.demo.service.ClassFeedbackService;

@Service("classFeedbackService")
public class ClassFeedbackServiceImpl implements ClassFeedbackService {
 
	@Autowired
	@Qualifier("classFeedbackRepository")
    private ClassFeedbackRepository classFeedbackRepository;
	
	@Autowired
	@Qualifier("classFeedbackConverter")
	private ClassFeedbackConverter classFeedbackConverter;	
	
	 @Autowired
	    @Qualifier("gymClassRepository")
	    private GymClassRepository gymClassRepository;

    @Override
    public List<ClassFeedback> getFeedbackByGymClassId(int classId) {
    	
        return classFeedbackRepository.findByGymClass(gymClassRepository.findById(classId).get());
    }

    @Override
    public void addFeedback(ClassFeedback feedback) {
        classFeedbackRepository.save(feedback);
    }
}
package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.ClassFeedbackConverter;
import com.example.demo.entity.ClassFeedback;
import com.example.demo.model.ClassFeedbackModel;
import com.example.demo.repository.ClassFeedbackRepository;
import com.example.demo.repository.GymClassRepository;
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
    public List<ClassFeedbackModel> getFeedbackByGymClassId(int classId) {
    	List<ClassFeedbackModel> l = new ArrayList<>();
    	for(ClassFeedback c : classFeedbackRepository.findByGymClass(gymClassRepository.findById(classId).get())){
    		l.add(classFeedbackConverter.transform(c));
    	}
        return l;
    }

    @Override
    public void addFeedback(ClassFeedbackModel feedback) {
        classFeedbackRepository.save(classFeedbackConverter.transform(feedback));
    }
}
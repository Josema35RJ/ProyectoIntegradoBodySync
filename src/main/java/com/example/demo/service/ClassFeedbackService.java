package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.ClassFeedback;

public interface ClassFeedbackService {
    List<ClassFeedback> getFeedbackByGymClassId(int classId);
    void addFeedback(ClassFeedback feedback);
}


package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.ClassFeedback;
import com.example.demo.entity.GymClass;
import com.example.demo.entity.GymUser;
import com.example.demo.model.GymUserModel;
import com.example.demo.service.ClassFeedbackService;
import com.example.demo.service.GymClassService;
import com.example.demo.service.GymUserService;

@Controller
public class GymClassController {
	
	private static final String ADDCLASS_VIEW = "/auth/GymOwner/addClass";
	private static final String GESTIONCLASES_VIEW = "/auth/GymOwner/GestionClases";
	
    @Autowired
    @Qualifier("gymClassService")
    private GymClassService gymClassService;
    
    @Autowired
	@Qualifier("gymUserService")
	private GymUserService gymUserService;


	@Autowired
	@Qualifier("classFeedbackService")
	private ClassFeedbackService classFeedbackService;

    @GetMapping("/auth/gymOwner/deleteClass/{id}")
    public String deleteClass(@PathVariable int id) {
        gymClassService.deleteClass(id);
        return "redirect:/auth/gymOwner/GestionClases";
    }
    
    @GetMapping("/auth/gymOwner/viewFeedback/{id}")
    public String viewFeedback(@PathVariable int id, Model model) {
        List<ClassFeedback> feedbacks = classFeedbackService.getFeedbackByGymClassId(id);
        if (feedbacks == null) {
            feedbacks = new ArrayList<>();
        }
        model.addAttribute("feedbacks", feedbacks);
        return "auth/gymOwner/GestionClases";
    }
   
}

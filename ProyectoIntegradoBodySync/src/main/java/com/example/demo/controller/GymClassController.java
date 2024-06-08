package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.ClassFeedbackModel;
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
    
    @GetMapping("/auth/gymOwner/viewFeedback/{Id}")
    @ResponseBody
    public List<ClassFeedbackModel> viewFeedback(@PathVariable int Id) {
        // Obtener los feedbacks por el Id de la clase de gimnasio
        return classFeedbackService.getFeedbackByGymClassId(Id);
    }
   
}

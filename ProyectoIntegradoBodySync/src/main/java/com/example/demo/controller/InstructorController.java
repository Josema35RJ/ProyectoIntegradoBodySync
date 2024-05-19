package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.converter.GymUserConverter;
import com.example.demo.converter.GymUserToDataFrameConverter;
import com.example.demo.entity.GymClass;
import com.example.demo.entity.GymUser;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.GymClassService;
import com.example.demo.service.GymUserService;
import com.example.demo.service.InstructorService;
import com.example.demo.service.SpecialityService;
import com.example.demo.service.impl.ChurnPredictionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class InstructorController {

	private static final String ADDINSTRUCTOR_VIEW = "/auth/GymOwner/addUpdateInstructor";
	private static final String GESTIONCLASES_VIEW = "/auth/GymInstructor/instructorPanel";
    private static final String GESTIONINSTRUCTORES_VIEW = "/auth/GymOwner/GestionInstructores";
	private static final String GESTIONMIEMBROS_VIEW = "/auth/GymInstructor/GestionMiembros";
    
    @Autowired
    @Qualifier("instructorService")
    private InstructorService instructorService;
    
	@Autowired
	@Qualifier("gymUserService")
	private GymUserService gymUserService;
    
    @Autowired
    @Qualifier("specialityService")
    private SpecialityService specialityService;
    
    @Autowired
	@Qualifier("gymUserConverter")
	private GymUserConverter gymUserConverter;
    
    @Autowired
	@Qualifier("gymClassService")
	private GymClassService gymClassService;
    
    @Autowired
	@Qualifier("churnPredictionService")
	private ChurnPredictionService churnPredictionService;

	@Autowired
	@Qualifier("gymUserToDataFrameConverter")
	private GymUserToDataFrameConverter gymUserToDataFrameConverter;

    @GetMapping("/auth/gymInstructor/instructorPanel")
    public String GestionClases(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Obtener el ID del instructor autenticado
        int instructorId = userDetails.getId();
        
        // Obtener las clases del instructor utilizando su ID
        List<GymClass> gymClasses = gymClassService.getAllClassesFinByInstructorId(instructorService.getInstructorById(instructorId));
        
        // Agregar los datos al modelo
        model.addAttribute("gymClasses", gymClasses);
        
        return GESTIONCLASES_VIEW;
    }

    @GetMapping("/auth/gymInstructor/GestionMiembros")
    public String GestionMiembros(HttpSession session, Model model) {
        // Obtener el ID de la clase almacenado en la sesión del usuario
        int classId = (int) session.getAttribute("classId");
        // Guardar el ID de la clase en la sesión
        session.setAttribute("classId", classId);
        List<GymUser> members = gymUserService.ListGymUsersByClassId(classId);

        // Realizar operaciones necesarias en los miembros
        // Filtrar los miembros activos e inactivos
        List<GymUser> activeMembers = members.stream()
                .filter(gymUser -> gymUser.isEnabled() && !gymUser.isDeleted())
                .collect(Collectors.toList());
        List<GymUser> inactiveMembers = members.stream()
                .filter(gymUser -> !gymUser.isEnabled() && !gymUser.isDeleted())
                .collect(Collectors.toList());

        // Calcular la edad y el IMC para cada miembro
        List<Integer> ages = members.stream()
                .map(GymUser::getBirthDate)
                .map(gymUserService::calculateAge)
                .collect(Collectors.toList());
      

      
        // Añadir los miembros activos e inactivos, las edades y los IMCs al modelo
        model.addAttribute("activeMembers", activeMembers);
        model.addAttribute("inactiveMembers", inactiveMembers);
        model.addAttribute("ages", ages);
     

        return GESTIONMIEMBROS_VIEW;
    }

    @PostMapping("/auth/gymOwner/addInstructor")
    public String addInstructor(@ModelAttribute GymUser instructor) {
        gymUserService.registrar(gymUserConverter.transform(instructor));
        return GESTIONINSTRUCTORES_VIEW;
    }

    @GetMapping("/auth/gymOwner/addInstructor")
    public String showAddInstructorForm(Model model) {
        model.addAttribute("instructor", new GymUser());
        model.addAttribute("allSpecialities", specialityService.getAllISpecialities());
        return ADDINSTRUCTOR_VIEW;
    }

    @GetMapping("/auth/gymOwner/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        GymUser instructor = instructorService.getInstructorById(id);
        model.addAttribute("instructor", instructor);
        return ADDINSTRUCTOR_VIEW;
    }

    @PostMapping("/auth/gymOwner/update/{id}")
    public String updateInstructor(@PathVariable("id") int id, @ModelAttribute GymUser instructor) {
        instructorService.updateInstructor( instructor);
        return "redirect:" + GESTIONINSTRUCTORES_VIEW;
    }

    @GetMapping("/auth/gymOwner/delete/{id}")
    public String deleteInstructor(@PathVariable("id") int id) {
        gymUserService.eliminarGymUser(id);
        return "redirect:" + GESTIONINSTRUCTORES_VIEW;
    }
}

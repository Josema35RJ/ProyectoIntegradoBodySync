package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.converter.GymUserConverter;
import com.example.demo.converter.GymUserToDataFrameConverter;
import com.example.demo.entity.Achievement;
import com.example.demo.entity.Exercise;
import com.example.demo.entity.GymClass;
import com.example.demo.entity.GymUser;
import com.example.demo.entity.NutritionPlan;
import com.example.demo.entity.Routine;
import com.example.demo.model.ExerciseModel;
import com.example.demo.model.GymClassModel;
import com.example.demo.model.GymUserModel;
import com.example.demo.model.NutritionPlanModel;
import com.example.demo.model.RoutineModel;
import com.example.demo.repository.GymUserRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.AchievementService;
import com.example.demo.service.ExerciseService;
import com.example.demo.service.GymClassService;
import com.example.demo.service.GymUserService;
import com.example.demo.service.InstructorService;
import com.example.demo.service.NutritionPlanService;
import com.example.demo.service.RoutineService;
import com.example.demo.service.SpecialityService;
import com.example.demo.service.impl.ChurnPredictionServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import smile.data.DataFrame;

@Controller
public class InstructorController {

	private static final String ADDINSTRUCTOR_VIEW = "/auth/GymOwner/addUpdateInstructor";
	private static final String GESTIONCLASES_VIEW = "/auth/GymInstructor/instructorPanel";
	private static final String GESTIONINSTRUCTORES_VIEW = "/auth/GymOwner/GestionInstructores";
	private static final String GESTIONMIEMBROS_VIEW = "/auth/GymInstructor/GestionMiembros";
	private static final String GESTIONRUTINAS_VIEW = "/auth/GymInstructor/GestionRutinas";
	private static final String GESTIONNUTRICION_VIEW = "/auth/GymInstructor/GestionNutricion";
	private static final String GESTIONEJERCICIOS_VIEW = "/auth/GymInstructor/GestionEjercicios";
	private static final String GESTIONACHIEVEMENT_VIEW = "/auth/GymInstructor/GestionAchievement";
	private static final String VIEWACHIEVEMNET_VIEW = "/auth/GymInstructor/viewAchievementGymUser";

	@Autowired
	@Qualifier("instructorService")
	private InstructorService instructorService;
	
	@Autowired
	@Qualifier("gymUserRepository")
	private GymUserRepository gymUserRepository;

	@Autowired
	@Qualifier("nutritionPlanService")
	private NutritionPlanService nutritionPlanService;

	@Autowired
	@Qualifier("achievementService")
	private AchievementService achievementService;

	@Autowired
	@Qualifier("routineService")
	private RoutineService routineService;

	@Autowired
	@Qualifier("exerciseService")
	private ExerciseService exerciseService;

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
	private ChurnPredictionServiceImpl churnPredictionService;

	@Autowired
	@Qualifier("gymUserToDataFrameConverter")
	private GymUserToDataFrameConverter gymUserToDataFrameConverter;

	@GetMapping("/auth/gymInstructor/instructorPanel")
	public String GestionClases(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		// Obtener el ID del instructor autenticado
		int instructorId = userDetails.getId();

		// Obtener las clases del instructor utilizando su ID
		List<GymClassModel> gymClasses = gymClassService
				.getAllClassesFinByInstructorId(instructorService.getInstructorById(instructorId));

		// Agregar los datos al modelo
		model.addAttribute("gymClasses", gymClasses);

		return GESTIONCLASES_VIEW;
	}

	@PostMapping("/auth/gymInstructor/GestionMiembros")
	public String GestionMiembros(@RequestParam("classId") int classId, HttpSession session, Model model) {
	    List<GymUserModel> members = gymUserService.ListGymUsersByClassId(classId);

	    // Filtra los miembros activos e inactivos
	    List<GymUserModel> activeMembers = members.stream()
	            .filter(gymUser -> gymUser.isEnabled() && !gymUser.isDeleted()).collect(Collectors.toList());
	    List<GymUserModel> inactiveMembers = members.stream()
	            .filter(gymUser -> !gymUser.isEnabled() && !gymUser.isDeleted()).collect(Collectors.toList());

	    // Calcula la edad y el IMC para cada miembro
	    List<Integer> ages = members.stream().map(GymUserModel::getBirthDate).map(gymUserService::calculateAge)
	            .collect(Collectors.toList());
	    List<Float> bmis = members.stream().map(gymUserService::calculateBMI).collect(Collectors.toList());

	    // Predice el riesgo de abandono para cada miembro
	    for (GymUserModel gymUser : members) {
	        DataFrame data = gymUserToDataFrameConverter.convert(Collections.singletonList(gymUser));
	        boolean churnRisk = churnPredictionService.predict(data);
	        gymUser.setChurn(churnRisk);
	    }

	    List<LocalDateTime> createdDates = members.stream().map(GymUserModel::getCreatedDate).collect(Collectors.toList());
	    
	    // Agregar la lista de rutinas al modelo
	    model.addAttribute("routines", routineService.ListRoutine());
	    model.addAttribute("exercises", exerciseService.ListExercise());
	    model.addAttribute("planNutrition", nutritionPlanService.ListAllNutritionPlan());
	    model.addAttribute("achievements", achievementService.ListAchievement());
	    // Añade los miembros activos e inactivos, las edades, los IMCs y los días desde la creación al modelo
	    model.addAttribute("activeMembers", activeMembers);
	    model.addAttribute("inactiveMembers", inactiveMembers);
	    model.addAttribute("ages", ages);
	    model.addAttribute("bmis", bmis);
	    model.addAttribute("daysSinceCreation", createdDates);
	    LocalDate fechaActual = LocalDate.now();
	    model.addAttribute("fechaActual", fechaActual);

	    return GESTIONMIEMBROS_VIEW;
	}


	@GetMapping("/auth/gymInstructor/GestionEjercicios")
	public String GestionEjercicios(HttpSession session, Model model) {
		// Obtener el ID de la clase almacenado en la sesión del usuario
		model.addAttribute("exercises", exerciseService.ListExercise());
		model.addAttribute("gymUsers", gymUserService.ListAllGymUsers());
		return GESTIONEJERCICIOS_VIEW;
	}

	@PostMapping("/auth/gymInstructor/asignarExercise")
	public String asignarExercise(@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam("exerciseId") int exerciseId, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		ExerciseModel exercise = exerciseService.getExercise(exerciseId);
		GymUserModel gymUser = gymUserService.getGymUserById(userId);
		if ( exercise != null) {
			List<ExerciseModel> exercises = gymUser.getExercises();
			exercises.add(exercise);
			gymUser.setExercises(exercises);
			// Guardar los cambios en la base de datos
			gymUserService.updateUser(gymUser);
		}
		 redirectAttributes.addFlashAttribute("success", "Ejercicio asignado con éxito");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymInstructor/agregarExercise")
	public String addExercise(@ModelAttribute ExerciseModel exercise, HttpSession session, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// Obtener el ID de la clase almacenado en la sesión del usuario
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verificar si la autenticación es de tipo CustomUserDetails
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// Obtener el ID del usuario desde CustomUserDetails
			int userId = userDetails.getId();

			// Asignar el ID del usuario al plan de nutrición
			exercise.setGymUser(gymUserService.getGymUserById(userId));

		}
		exerciseService.create(exercise);
		 redirectAttributes.addFlashAttribute("success", "Ejercicio creado con éxito");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymInstructor/updateExercise")
	public String updateExercise(@ModelAttribute ExerciseModel exercise, HttpSession session, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// Obtener el ID de la clase almacenado en la sesión del usuario
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verificar si la autenticación es de tipo CustomUserDetails
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// Obtener el ID del usuario desde CustomUserDetails
			int userId = userDetails.getId();

			// Asignar el ID del usuario al plan de nutrición
			exercise.setGymUser(gymUserService.getGymUserById(userId));

		}
		exerciseService.update(exercise);
		 redirectAttributes.addFlashAttribute("success", "Ejercicio actualizado con éxito");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@GetMapping("/auth/gymInstructor/GestionRutinas")
	public String GestionRutinas(HttpSession session, Model model) {
		// Obtener el ID de la clase almacenado en la sesión del usuario
		model.addAttribute("routines", routineService.ListRoutine());
		model.addAttribute("gymUsers", gymUserService.ListAllGymUsers());
		model.addAttribute("exercises", exerciseService.ListExercise());
		return GESTIONRUTINAS_VIEW;
	}

	@PostMapping("/auth/gymInstructor/agregarRoutine")
	public String addRoutine(@ModelAttribute RoutineModel routine, HttpSession session, Model model,
			HttpServletRequest request,RedirectAttributes redirectAttributes) {
		// Obtener el ID de la clase almacenado en la sesión del usuario
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verificar si la autenticación es de tipo CustomUserDetails
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// Obtener el ID del usuario desde CustomUserDetails
			int userId = userDetails.getId();
			// Asignar el ID del usuario al plan de nutrición
			routine.setGymUser(gymUserService.getGymUserById(userId));

		}
		routineService.create(routine);
		 redirectAttributes.addFlashAttribute("success", "Rutina creada con éxito");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymInstructor/updateRoutine")
	public String updateRoutine(@ModelAttribute RoutineModel routine, HttpSession session, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// Obtener el ID de la clase almacenado en la sesión del usuario
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verificar si la autenticación es de tipo CustomUserDetails
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// Obtener el ID del usuario desde CustomUserDetails
			int userId = userDetails.getId();

			// Asignar el ID del usuario al plan de nutrición
			routine.setGymUser(gymUserService.getGymUserById(userId));

		}
		routineService.update(routine);
		 redirectAttributes.addFlashAttribute("success", "Rutina actualizada con éxito");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymInstructor/asignarRoutine")
    public String asignarRoutine(@RequestParam("userId") int userId, @RequestParam("routineId") int routineId,
                                 HttpServletRequest request, RedirectAttributes redirectAttributes) {
       
        GymUserModel gymUser = gymUserService.getGymUserById(userId);
        RoutineModel routine = routineService.getById(routineId);

        if (gymUser != null && routine != null) {
           gymUser.getRoutines().add(routine);
                
                gymUserService.updateUser(gymUser);
            
        }

   	 redirectAttributes.addFlashAttribute("success", "Rutina asignada con éxito");
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

	@GetMapping("/auth/gymInstructor/GestionNutricion")
	public String gestionPlanesNutricion(HttpSession session, Model model) {
		// Obtener el ID de la clase almacenado en la sesión del usuario
		model.addAttribute("nutritionPlans", nutritionPlanService.ListAllNutritionPlan());
		model.addAttribute("gymUsers", gymUserService.ListAllGymUsers());

		return GESTIONNUTRICION_VIEW;
	}

	@PostMapping("/auth/gymInstructor/asignarPlanNutricion")
	public String asignarPlanNutricion(@RequestParam("userId") int userId, @RequestParam("planId") int planId,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// Obtener el usuario de la base de datos
		GymUserModel gymUser = gymUserService.getGymUserById(userId);

		// Obtener el plan de nutrición de la base de datos
		NutritionPlanModel nutritionPlan = nutritionPlanService.getNutritionPlan(planId);

		// Verificar si el usuario y el plan existen
		if (gymUser != null && nutritionPlan != null) {
			// Agregar el plan de nutrición a la lista del usuario
			List<NutritionPlanModel> nutritionPlans = gymUser.getNutritionPlans();
			nutritionPlans.add(nutritionPlan);
			gymUser.setNutritionPlans(nutritionPlans);

			// Guardar los cambios en la base de datos
			gymUserService.updateUser(gymUser);
		}

		// Redirigir a la página anterior
		redirectAttributes.addFlashAttribute("success", "Plan Nutricion asignado exitosamente.");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymInstructor/agregarPlanNutricion")
	public String addNutritionPlan(@ModelAttribute NutritionPlanModel plan, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verificar si la autenticación es de tipo CustomUserDetails
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// Obtener el ID del usuario desde CustomUserDetails
			int userId = userDetails.getId();

			// Asignar el ID del usuario al plan de nutrición
			plan.setGymUser(gymUserService.getGymUserById(userId));

			// Asignar la fecha de creación
			plan.setCreatedAt(LocalDateTime.now());

			// Guardar el plan de nutrición
			nutritionPlanService.save(plan);
		}
		redirectAttributes.addFlashAttribute("success", "Plan Nutricion actualizado exitosamente.");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymInstructor/editarPlanNutricion")
	public String editNutritionPlan(@ModelAttribute NutritionPlanModel plan, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verificar si la autenticación es de tipo CustomUserDetails
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// Obtener el ID del usuario desde CustomUserDetails
			int userId = userDetails.getId();

			// Asignar el ID del usuario al plan de nutrición
			plan.setGymUser(gymUserService.getGymUserById(userId));

			// Asignar la fecha de actualización
			plan.setUpdatedAt(LocalDateTime.now());
		}

		nutritionPlanService.update(plan);
		redirectAttributes.addFlashAttribute("success", "Plan Nutricion actualizado exitosamente.");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymOwner/addInstructor")
	public String addInstructor(@ModelAttribute GymUser instructor, RedirectAttributes redirectAttributes) {
		gymUserService.registrar(gymUserConverter.transform(instructor));
		redirectAttributes.addFlashAttribute("success", "Instructor creado exitosamente.");
		return GESTIONINSTRUCTORES_VIEW;
	}

	@GetMapping("/auth/gymOwner/addInstructor")
	public String showAddInstructorForm(Model model) {
		model.addAttribute("instructor", new GymUser());
		model.addAttribute("allSpecialities", specialityService.getAllISpecialities());
		return ADDINSTRUCTOR_VIEW;
	}

	@GetMapping("/auth/gymOwner/edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
		GymUserModel instructor = instructorService.getInstructorById(id);
		model.addAttribute("instructor", instructor);
		redirectAttributes.addFlashAttribute("success", "Instructor actualizado exitosamente.");
		return ADDINSTRUCTOR_VIEW;
	}

	@GetMapping("/auth/gymInstructor/GestionAchievement")
	public String gestionLogros(HttpSession session, Model model) {
		// Obtener el ID de la clase almacenado en la sesión del usuario
		model.addAttribute("achievements", achievementService.ListAchievement());
		model.addAttribute("gymUsers", gymUserService.ListAllGymUsers());
		return GESTIONACHIEVEMENT_VIEW;
	}

	@PostMapping("/auth/gymInstructor/agregarAchievement")
	public String addAchievement(@ModelAttribute Achievement achievement, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verificar si la autenticación es de tipo CustomUserDetails
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// Obtener el ID del usuario desde CustomUserDetails
			int userId = userDetails.getId();

			// Asignar el ID del usuario al plan de nutrición
			achievement.setGymUser(gymUserConverter.transform(gymUserService.getGymUserById(userId)));

			// Asignar la fecha de creación
			achievement.setAchievedAt(LocalDateTime.now());

			// Guardar el logro
			
			achievementService.save(achievement);
		}
		redirectAttributes.addFlashAttribute("success", "Logro creado exitosamente.");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymInstructor/updateAchievement")
	public String editAchievement(@ModelAttribute Achievement achievement, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verificar si la autenticación es de tipo CustomUserDetails
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// Obtener el ID del usuario desde CustomUserDetails
			int userId = userDetails.getId();

			// Asignar el ID del usuario al plan de nutrición
			achievement.setGymUser(gymUserConverter.transform(gymUserService.getGymUserById(userId)));

			// Asignar la fecha de actualización
			achievement.setAchievedAt(LocalDateTime.now());
		}

		achievementService.update(achievement);
		redirectAttributes.addFlashAttribute("success", "Logro actualizado exitosamente.");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymInstructor/deleteAchievement")
	public String deleteAchievement(@RequestParam("id") int id, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verificar si la autenticación es de tipo CustomUserDetails
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		}

		// Actualizar el logro
		achievementService.deleteAchievement(achievementService.getById(id));

		// Redirigir con un mensaje de éxito
		redirectAttributes.addFlashAttribute("success", "Logro eliminado exitosamente.");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/gymOwner/update/{id}")
	public String updateInstructor(@PathVariable("id") int id, @ModelAttribute GymUserModel instructor, RedirectAttributes redirectAttributes) {
		instructorService.updateInstructor(instructor);
		redirectAttributes.addFlashAttribute("success", "Instructor actualizado exitosamente.");
		return "redirect:" + GESTIONINSTRUCTORES_VIEW;
	}

	@GetMapping("/auth/gymOwner/delete/{id}")
	public String deleteInstructor(@PathVariable("id") int id) {
		gymUserService.eliminarGymUser(id);
		return "redirect:" + GESTIONINSTRUCTORES_VIEW;
	}
	
	@PostMapping("/auth/gymInstructor/viewAchievements")
	public String viewAchievementsGymUser(@RequestParam("id") int id, Model model) {
	    GymUser gymUser = gymUserRepository.findById(id).get();
	    model.addAttribute("achievements", gymUser.getAchievements());
	    return VIEWACHIEVEMNET_VIEW;
	}

}

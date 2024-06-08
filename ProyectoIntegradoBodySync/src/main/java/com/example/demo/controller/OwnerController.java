package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.converter.GymUserToDataFrameConverter;
import com.example.demo.entity.GymClass;
import com.example.demo.entity.GymUser;
import com.example.demo.model.ClassFeedbackModel;
import com.example.demo.model.GymClassModel;
import com.example.demo.model.GymUserModel;
import com.example.demo.repository.GymUserRepository;
import com.example.demo.service.ClassFeedbackService;
import com.example.demo.service.GymClassService;
import com.example.demo.service.GymUserService;
import com.example.demo.service.InstructorService;
import com.example.demo.service.impl.ChurnPredictionServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import smile.data.DataFrame;

@Controller
public class OwnerController {

	private static final String OWNERPANEL_VIEW = "/auth/GymOwner/OwnerPanel";
	private static final String GESTIONMIEMBROS_VIEW = "/auth/GymOwner/GestionMiembros";
	private static final String ADDMIEMBROS_VIEW = "/auth/GymOwner/addMiembros";
	private static final String VIEWACHIEVEMNET_VIEW = "/auth/GymOwner/viewAchievement";
	private static final String GESTIONCLASES_VIEW = "/auth/GymOwner/GestionClases";
	private static final String GESTIONINSTRUCTORES_VIEW = "/auth/GymOwner/GestionInstructores";
	private static final String CONFIGURACIONGYM_VIEW = "/auth/GymOwner/ConfiguracionGym";

	@Autowired
	@Qualifier("gymUserService")
	private GymUserService gymUserService;

	@Autowired
	@Qualifier("gymUserRepository")
	private GymUserRepository gymUserRepository;

	@Autowired
	@Qualifier("gymClassService")
	private GymClassService gymClassService;

	@Autowired
	@Qualifier("classFeedbackService")
	private ClassFeedbackService classFeedbackService;

	@Autowired
	@Qualifier("instructorService")
	private InstructorService instructorService;

	@Autowired
	@Qualifier("churnPredictionService")
	private ChurnPredictionServiceImpl churnPredictionService;

	@Autowired
	@Qualifier("gymUserToDataFrameConverter")
	private GymUserToDataFrameConverter gymUserToDataFrameConverter;

	@GetMapping("/auth/gymOwner/ownerPanel")
	public String OwnerPanel() {
		return OWNERPANEL_VIEW;
	}

	@PostMapping("/auth/gymOwner/addClass")
	public String addClass(@ModelAttribute GymClassModel gymClass, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		gymClassService.addClass(gymClass);
	 gymUserService.getGymUserById(gymClass.getInstructor().getId()).getEnrolledClasses().add(gymClass);

		redirectAttributes.addFlashAttribute("message", "Clase creada con éxito");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
	

	@PostMapping("/auth/gymOwner/updateClass/{id}")
	public String editClass(@PathVariable int id, @ModelAttribute GymClass updatedClass, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
 
		GymClassModel existingClass = gymClassService.getClassById(id);
		if (existingClass != null) {
			existingClass.setName(updatedClass.getName());
			existingClass.setDescription(updatedClass.getDescription());
			existingClass.setStartDate(updatedClass.getStartDate());
			existingClass.setEndDate(updatedClass.getEndDate());
			existingClass.setDaysOfWeek(updatedClass.getDaysOfWeek());
			existingClass.setTime(updatedClass.getTime());
			existingClass.setDuration(updatedClass.getDuration());
			existingClass.setMaximumCapacity(updatedClass.getMaximumCapacity());
			existingClass.setInstructor(existingClass.getInstructor());

			gymClassService.updateClass(existingClass);
			gymUserService.getGymUserById(existingClass.getInstructor().getId()).getEnrolledClasses().add(existingClass);
			redirectAttributes.addFlashAttribute("success", "Clase actualizada con éxito");
		}
		String referer = request.getHeader("Referer");	
		return "redirect:" + referer;
	}
	
	@PostMapping("/auth/gymUser/activateDesactivate/{id}")
	public String activateDesativar(@PathVariable int id, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		 gymUserService.activarDesactivar(id);
		 redirectAttributes.addFlashAttribute("success", "Instructor desactivado con éxito");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@GetMapping("/auth/gymOwner/getClassDetails/{id}")
	@ResponseBody
	public GymClassModel getClassDetails(@PathVariable int id) {
		GymClassModel gymClass = gymClassService.getClassById(id);
		return gymClass;
	}

	@GetMapping("/auth/gymOwner/GestionMiembros")
	public String GestionMiembros(Model model) {
		List<GymUserModel> members = gymUserService.ListAllGymUsers();

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

		List<LocalDateTime> createdDates = members.stream().map(GymUserModel::getCreatedDate) // Asegúrate de que
																								// getCreatedDate es un
																								// método de
																								// GymUserModel
				.collect(Collectors.toList());

		// Añade los miembros activos e inactivos, las edades, los IMCs y los días desde
		// la creación al modelo
		model.addAttribute("activeMembers", activeMembers);
		model.addAttribute("inactiveMembers", inactiveMembers);
		model.addAttribute("ages", ages);
		model.addAttribute("bmis", bmis);
		model.addAttribute("daysSinceCreation", createdDates);
		LocalDate fechaActual = LocalDate.now();
		model.addAttribute("fechaActual", fechaActual);

		return GESTIONMIEMBROS_VIEW;
	}

	@GetMapping("/auth/gymUsers/membershipStats")
	public ResponseEntity<Map<String, Integer>> getMembershipStats() {
		Map<String, Integer> stats = new HashMap<>();
		// Aquí debes rellenar el mapa 'stats' con los datos que necesitas.
		// Por ejemplo, podrías contar el número de miembros activos e inactivos.
		stats.put("active", gymUserRepository.countByEnabledAndRole(true, "ROL_GYMUSER"));
		stats.put("inactive", gymUserRepository.countByEnabledAndRole(false, "ROL_GYMUSER"));
		return ResponseEntity.ok(stats);
	}

	@GetMapping("/auth/gymUsers/attendanceStats")
	public ResponseEntity<Map<String, Integer>> getAttendanceStats() {
	    Map<String, Integer> stats = new HashMap<>();
	    SimpleDateFormat sdf = new SimpleDateFormat("EEEE", new Locale("es", "ES"));

	    // Días de la semana.
	    for (String dia : new String[] { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" }) {
	        try {
	            Date fechaDia = sdf.parse(dia);
	            stats.put(dia, gymUserRepository.countByAttendanceDaysContains(fechaDia));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }
	    return ResponseEntity.ok(stats);
	}

	@PostMapping("/gymUser/delete/{id}")
	public String deleteGymUser(@PathVariable("id") int id, RedirectAttributes flash, HttpServletRequest request) {
		if (gymUserService.eliminarGymUser(id)) {
			flash.addFlashAttribute("message", "Usuario eliminado con éxito");
		} else {
			flash.addFlashAttribute("error", "No se pudo eliminar el usuario");
		}
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@GetMapping("/auth/gymOwner/viewAchievements/{id}")
	public String viewAchievementsGymUser(@PathVariable("id") int id, Model model) {
		GymUser gymUser = gymUserRepository.findById(id).get();
		model.addAttribute("achievements", gymUser.getAchievements());
		return VIEWACHIEVEMNET_VIEW;
	}

	@PostMapping("/auth/gymOwner/addFeedback")
	public String addFeedback(@ModelAttribute ClassFeedbackModel feedback) {
		classFeedbackService.addFeedback(feedback);
		return "redirect:" + GESTIONCLASES_VIEW;
	}

	@GetMapping("/auth/gymOwner/addMember")
	public String showAddGymUserForm(Model model) {
		model.addAttribute("gymUser", new GymUser());
		return ADDMIEMBROS_VIEW;
	}

	@PostMapping("/auth/gymOwner/addMember")
	public String addGymUser(@ModelAttribute GymUser gymUser) {
		gymUserRepository.save(gymUser);
		return ADDMIEMBROS_VIEW;
	}

	@GetMapping("/auth/gymOwner/GestionClases")
	public String GestionClases(Model model) {
		model.addAttribute("gymClasses", gymClassService.getAllClasses());
		model.addAttribute("instructors", gymUserService.ListAllGymUsersInstructores());
		return GESTIONCLASES_VIEW;
	}

	@GetMapping("/auth/gymOwner/GestionInstructores")
	public String GestionInstructores(Model model) {

	    model.addAttribute("ListInstructores", instructorService.getAllInstructors());
	    System.out.println(instructorService.getAllInstructors());
	    return GESTIONINSTRUCTORES_VIEW;
	}

	@GetMapping("/auth/gymOwner/ConfiguracionGym")
	public String ConfiguracionGym() {
	return CONFIGURACIONGYM_VIEW;
	}

	@GetMapping("/auth/gymOwner/instructors")
	 @ResponseBody
    public List<GymUserModel> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

	@GetMapping("/auth/gymOwner/asistencia")
	public ResponseEntity<List<GymClassModel>> obtenerDatosAsistencia() {
	    List<GymClassModel> asistenciaData = gymClassService.obtenerDatosAsistencia();
	    return ResponseEntity.ok(asistenciaData);
	}

	@GetMapping("/auth/gymOwner/popularidad")
	public ResponseEntity<List<GymClassModel>> obtenerDatosPopularidad() {
	    List<GymClassModel> popularidadData = gymClassService.obtenerDatosPopularidad();
	    return ResponseEntity.ok(popularidadData);
	}

	@PostMapping("/auth/gymOwner/instructors/edit")
	public String editInstructor(@ModelAttribute GymUserModel instructor, RedirectAttributes redirectAttributes, HttpServletRequest request) {
	    gymUserService.updateUser(instructor);
	    
	    // Agregar un mensaje de éxito
	    redirectAttributes.addFlashAttribute("success", "¡Instructor actualizado exitosamente!");

	    String referer = request.getHeader("Referer");
	    return "redirect:" + referer;
	}

	@PostMapping("/auth/gymOwner/register")
	public String registerSubmit(@ModelAttribute("gymUserModel") GymUserModel gymUserModel,
			@RequestParam("confirmPassword") String confirmPassword, BindingResult result, RedirectAttributes flash, HttpServletRequest request) {

		if (result.hasErrors()) {
			return GESTIONINSTRUCTORES_VIEW;
		}

		gymUserModel.setUsername(gymUserModel.getUsername().toLowerCase());

		if (gymUserModel.getFirstName().length() > 45) {
			flash.addFlashAttribute("error", "¡El nombre excede los 100 caracteres!");
			return GESTIONINSTRUCTORES_VIEW;
		} else if (gymUserService.existeUsername(gymUserModel.getUsername())) {
			flash.addFlashAttribute("error",
					"Este correo electrónico ya está registrado. ¿Has olvidado tu contraseña? Puedes restablecerla aquí. O intenta registrarte con una dirección de correo electrónico diferente.");
			return GESTIONINSTRUCTORES_VIEW;
		} else if (gymUserModel.getLastName().length() > 100) {
			flash.addFlashAttribute("error", "¡Los apellidos exceden los 100 caracteres!");
			return GESTIONINSTRUCTORES_VIEW;
		} else if (gymUserService.existeUsername(gymUserModel.getUsername())) {
			flash.addFlashAttribute("error", "¡El correo electrónico ya está registrado!");
			return GESTIONINSTRUCTORES_VIEW;
		} else if (!isValidEmailAddress(gymUserModel.getUsername())) {
			flash.addFlashAttribute("error", "¡El correo electrónico no tiene un formato válido!");
			return "redirect:/auth/register";
		} else if (!gymUserModel.getPassword().equals(confirmPassword)) {
			flash.addFlashAttribute("error", "¡Las contraseñas no coinciden!");
			return GESTIONINSTRUCTORES_VIEW;
		} else if (gymUserModel.getPassword().length() < 6 || gymUserModel.getPassword().length() > 18) {
			flash.addFlashAttribute("error", "¡La contraseña debe tener entre 6 y 18 caracteres!");
			return GESTIONINSTRUCTORES_VIEW;
		} else {
			gymUserService.registrar(gymUserModel);
			flash.addFlashAttribute("success", "¡Instructor registrado exitosamente!");
			String referer = request.getHeader("Referer");
			
			return "redirect:" + referer;
		}
	}
	
	private boolean isValidEmailAddress(String email) {
		// Expresión regular para verificar el formato de un correo electrónico
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
		return email.matches(regex);
	}
	
	@GetMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalida la sesión actual
        request.getSession().invalidate();
        // Redirige a la página de inicio de sesión
        return "redirect:/auth/login";
    }

}

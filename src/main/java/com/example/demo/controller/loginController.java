package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.converter.GymUserConverter;
import com.example.demo.entity.GymUser;
import com.example.demo.model.GymUserModel;
import com.example.demo.service.GymUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class loginController {

	private static final String LOGIN_VIEW = "/auth/login";
	private static final String REGISTER_VIEW = "/auth/register";

	@Autowired
	@Qualifier("gymUserService")
	private GymUserService gymUserService;

	@Autowired
	@Qualifier("gymUserConverter")
	private GymUserConverter gymUserConverter;

	@GetMapping("/auth/login")
	public String login(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout, Principal principal) {

		// Redirige a la página principal si el usuario ya inició sesión
		if (principal != null) {
			return "redirect:/" + LOGIN_VIEW;
		}

		// Solo agrega mensajes de error al modelo si los parámetros correspondientes no
		// están vacíos
		if (error != null) {
			model.addAttribute("loginError", "Error de inicio de sesión. Por favor, verifica tus credenciales.");
			// Agrega otros mensajes de error específicos aquí si es necesario
		} else {
			// Asegúrate de que los atributos del modelo estén limpios si no hay errores
			model.addAttribute("loginError", null);
			model.addAttribute("emailError", null);
			model.addAttribute("passwordError", null);
			model.addAttribute("accountLocked", null);
			model.addAttribute("accountNotActivated", null);
		}

		if (logout != null) {
			model.addAttribute("logoutMessage", "Has cerrado sesión con éxito. ¡Esperamos verte pronto!");
		} else {
			// Limpia el mensaje de cierre de sesión si no hay logout
			model.addAttribute("logoutMessage", null);
		}

		// Retorna la vista de login
		return LOGIN_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/register")
	public String registerForm(Model model) {
		model.addAttribute("gymUser", new GymUserModel());
		return REGISTER_VIEW;
	}

	@PostMapping("/auth/register")
	public String registerSubmit(@ModelAttribute("gymUserModel") GymUserModel gymUserModel,
			@RequestParam("confirmPassword") String confirmPassword, BindingResult result, RedirectAttributes flash) {

		if (result.hasErrors()) {
			return REGISTER_VIEW;
		}

		gymUserModel.setUsername(gymUserModel.getUsername().toLowerCase());

		if (gymUserModel.getFirstName().length() > 45) {
			flash.addFlashAttribute("error", "¡El nombre excede los 100 caracteres!");
			return "redirect:" + REGISTER_VIEW;
		} else if (gymUserService.existeUsername(gymUserModel.getUsername())) {
			flash.addFlashAttribute("error",
					"Este correo electrónico ya está registrado. ¿Has olvidado tu contraseña? Puedes restablecerla aquí. O intenta registrarte con una dirección de correo electrónico diferente.");
			return "redirect:" + REGISTER_VIEW;
		} else if (gymUserModel.getLastName().length() > 100) {
			flash.addFlashAttribute("error", "¡Los apellidos exceden los 100 caracteres!");
			return "redirect:" + REGISTER_VIEW;
		} else if (gymUserService.existeUsername(gymUserModel.getUsername())) {
			flash.addFlashAttribute("error", "¡El correo electrónico ya está registrado!");
			return "redirect:" + REGISTER_VIEW;
		} else if (!isValidEmailAddress(gymUserModel.getUsername())) {
			flash.addFlashAttribute("error", "¡El correo electrónico no tiene un formato válido!");
			return "redirect:" + REGISTER_VIEW;
		} else if (!gymUserModel.getPassword().equals(confirmPassword)) {
			flash.addFlashAttribute("error", "¡Las contraseñas no coinciden!");
			return "redirect:" + REGISTER_VIEW;
		} else if (gymUserModel.getPassword().length() < 6 || gymUserModel.getPassword().length() > 18) {
			flash.addFlashAttribute("error", "¡La contraseña debe tener entre 6 y 18 caracteres!");
			return "redirect:" + REGISTER_VIEW;
		} else {
			gymUserService.registrar(gymUserModel);
			flash.addFlashAttribute("success", "¡GymUser registrado exitosamente!");
			return "redirect:" + LOGIN_VIEW;
		}
	}

	private boolean isValidEmailAddress(String email) {
		// Expresión regular para verificar el formato de un correo electrónico
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
		return email.matches(regex);
	}

}
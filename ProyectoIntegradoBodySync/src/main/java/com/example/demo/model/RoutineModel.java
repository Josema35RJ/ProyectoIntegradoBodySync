package com.example.demo.model;

import java.util.List;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.GymUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RoutineModel {
	// Identificador único para cada rutina.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "The name is required")
	private String name;

	// Lista de ejercicios que componen la rutina.
	@NotEmpty(message = "Exercise list cannot be empty")
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Exercise> exerciseList;

	// Número de días a la semana que se debe realizar la rutina.
	@Min(value = 1, message = "There must be at least one day per week")
	private int daysPerWeek;

	// Referencia al usuario del gimnasio al que pertenece el ejercicio.
	@ManyToOne
	@JoinColumn(name = "gym_user_id")
	private GymUser gymUser;
}

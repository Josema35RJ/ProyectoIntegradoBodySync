package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.GymUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class WorkoutModel {
	// Identificador único para cada entrenamiento.
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int id;

		// Usuario que ha hecho el registro de entrenamiento.
		@ManyToOne
		@NotNull(message = "GymUser is required")
		private GymUser gymUser;

		// Fecha y hora del entrenamiento.
		@NotNull(message = "Date and time are required")
		private Date dateTime;

		// Duración del entrenamiento.
		@DecimalMin(value = "0.0", inclusive = false, message = "Duration must be greater than 0")
		private BigDecimal duration;

		// Lista de ejercicios realizados durante el entrenamiento.
		@NotEmpty(message = "Exercise list cannot be empty")
		@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		private List<Exercise> exerciseList;

		// Comentarios adicionales sobre el entrenamiento.
		private String comments;
}

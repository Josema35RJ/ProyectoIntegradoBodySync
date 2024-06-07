package com.example.demo.model;

import java.time.LocalDateTime;

import com.example.demo.entity.GymUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WorkoutLogModel {
	// Identificador único para cada registro de entrenamiento.
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;

		// Usuario que ha hecho el registro de entrenamiento.
		@ManyToOne
		@NotNull(message = "GymUser is required")
		@JsonBackReference
		private GymUserModel gymUser;

		// Fecha del entrenamiento.
		private LocalDateTime workoutDate;

		// Descripción del entrenamiento.
		@NotBlank(message = "Workout description is required")
		private String workoutDescription;

		// Calorías quemadas durante el entrenamiento.
		@Min(value = 0, message = "Calories burned must be a non-negative number")
		private Integer caloriesBurned;
}

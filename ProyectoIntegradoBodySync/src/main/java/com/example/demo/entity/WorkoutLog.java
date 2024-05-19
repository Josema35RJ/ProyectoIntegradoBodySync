package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//La clase WorkoutLog representa un registro de entrenamiento hecho por un usuario.
@Entity
@Table(name = "workout_log")
@Data
public class WorkoutLog {
	// Identificador único para cada registro de entrenamiento.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Usuario que ha hecho el registro de entrenamiento.
	@ManyToOne
	@NotNull(message = "GymUser is required")
	private GymUser gymUser;

	// Fecha del entrenamiento.
	private LocalDateTime workoutDate;

	// Descripción del entrenamiento.
	@NotBlank(message = "Workout description is required")
	private String workoutDescription;

	// Calorías quemadas durante el entrenamiento.
	@Min(value = 0, message = "Calories burned must be a non-negative number")
	private Integer caloriesBurned;
}
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

//La clase MealLog representa un registro de comida hecha por un usuario.
@Entity
@Table(name = "meal_log")
@Data
public class MealLog {
	// Identificador único para cada registro de comida.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Usuario que ha hecho el registro de comida.
	@ManyToOne
	@NotNull(message = "GymUser is required")
	private GymUser gymUser;

	// Fecha de la comida.
	private LocalDateTime mealDate;

	// Descripción de la comida.
	@NotBlank(message = "Meal description is required")
	private String mealDescription;

	// Calorías consumidas en la comida.
	@Min(value = 0, message = "Calories consumed must be a non-negative number")
	private Integer caloriesConsumed;
}
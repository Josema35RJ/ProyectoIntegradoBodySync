package com.example.demo.model;

import java.time.LocalDateTime;

import com.example.demo.entity.GymUser;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NutritionPlanModel {
		// Identificador único para cada plan de nutrición.
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;

		// Usuario al que pertenece el plan de nutrición.
		@ManyToOne
		@NotNull(message = "GymUser is required")
		private GymUser gymUser;

		// Nombre del plan de nutrición.
		@NotBlank(message = "Name is required")
		private String name;

		// Descripción del plan de nutrición.
		@NotBlank(message = "Description is required")
		private String description;

		// Fecha de creación del plan de nutrición.
		private LocalDateTime createdAt;

		// Fecha de última modificación del plan de nutrición.
		private LocalDateTime updatedAt;
		
}

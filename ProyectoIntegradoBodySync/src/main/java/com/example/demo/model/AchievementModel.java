package com.example.demo.model;

import java.time.LocalDateTime;

import com.example.demo.entity.GymUser;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AchievementModel {
	// Identificador único para cada logro.
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;

		// Usuario que ha conseguido el logro.
		@ManyToOne
		@NotNull(message = "GymUser is required")
		private GymUser gymUser;

		// Nombre del logro.
		@NotBlank(message = "Name is required")
		private String name;

		// Descripción del logro.
		@NotBlank(message = "Description is required")
		private String description;

		// Fecha en la que se consiguió el logro.
		private LocalDateTime achievedAt;
}

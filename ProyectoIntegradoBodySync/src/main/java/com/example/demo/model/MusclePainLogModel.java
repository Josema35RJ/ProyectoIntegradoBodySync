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

public class MusclePainLogModel {
	// Identificador único para cada registro de dolor muscular.
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;

		// Usuario que ha hecho el registro de dolor muscular.
		@ManyToOne
		@NotNull(message = "GymUser is required")
		@JsonBackReference
		private GymUserModel gymUser;

		// Fecha del dolor muscular.
		private LocalDateTime painDate;

		// Descripción del dolor muscular.
		@NotBlank(message = "Pain description is required")
		private String painDescription;

		// Grupo muscular que duele.
		@NotBlank(message = "Muscle group is required")
		private String muscleGroup;

		// Intensidad del dolor muscular.
		@Min(value = 0, message = "Pain intensity must be a non-negative number")
		private Integer painIntensity;
}

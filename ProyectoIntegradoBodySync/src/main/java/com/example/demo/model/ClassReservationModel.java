package com.example.demo.model;

import java.time.LocalDateTime;

import com.example.demo.entity.GymUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClassReservationModel {
	// Identificador único para cada reserva de clase.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Usuario que ha hecho la reserva.
	@ManyToOne
	@NotNull(message = "GymUser is required")
	@JsonBackReference
	private GymUserModel gymUser;

	// Fecha y hora de la reserva.
	private LocalDateTime reservedAt;

	// Fecha y hora de inicio de la clase.
	private LocalDateTime classStartTime;

	// Nombre de la clase reservada.
	@NotBlank(message = "Class name is required")
	private String className;
}

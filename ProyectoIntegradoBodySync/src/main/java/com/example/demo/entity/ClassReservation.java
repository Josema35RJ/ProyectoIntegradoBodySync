package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//La clase ClassReservation representa una reserva de clase hecha por un usuario.
@Entity
@Table(name = "class_reservation")
@Data
public class ClassReservation {
	// Identificador único para cada reserva de clase.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Usuario que ha hecho la reserva.
	@ManyToOne
	@NotNull(message = "GymUser is required")
	private GymUser gymUser;

	// Fecha y hora de la reserva.
	private LocalDateTime reservedAt;

	// Fecha y hora de inicio de la clase.
	private LocalDateTime classStartTime;

	// Nombre de la clase reservada.
	@NotBlank(message = "Class name is required")
	private String className;
}

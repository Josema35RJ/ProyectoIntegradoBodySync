package com.example.demo.entity;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//La clase Workout representa un entrenamiento realizado por un usuario.

@Entity
@Table(name = "workout")
@Data
public class Workout {
	// Identificador único para cada entrenamiento.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	// Identificador del usuario que ha realizado el entrenamiento.
	@NotNull(message = "User is required")
	private int user;

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

package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//La clase Speciality representa una especialidad que puede tener un instructor.
@Entity
@Table(name = "speciality")
@Data
public class Speciality {
	// Identificador único para cada especialidad.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	// Nombre de la especialidad.
	@Column(name = "Name", nullable = false)
	@NotBlank(message = "Name is required")
	private String name;

	// Descripción de la especialidad.
	@Column(name = "Description", nullable = false)
	@NotBlank(message = "Description is required")
	private String description;
}
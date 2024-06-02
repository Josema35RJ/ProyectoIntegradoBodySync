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

//La clase Achievement representa un logro alcanzado por un usuario.
@Entity
@Table(name = "achievement")
@Data
public class Achievement {
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

	public void setDeletedAt(LocalDateTime now) {
		// TODO Auto-generated method stub
		
	}
}
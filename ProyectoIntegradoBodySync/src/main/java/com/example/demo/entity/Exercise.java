package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//La clase Exercise representa un ejercicio que puede ser realizado en el gimnasio.
@Entity
@Table(name = "exercise")
@Data
public class Exercise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Description is required")
	private String description;

	@NotBlank(message = "Muscle group is required")
	private String muscleGroup;

	@NotBlank(message = "Necessary equipment is required")
	private String necessaryEquipment;

	// Referencia al usuario del gimnasio al que pertenece el ejercicio.
	@ManyToOne
	@JoinColumn(name = "gym_user_id")
	private GymUser gymUser;
}

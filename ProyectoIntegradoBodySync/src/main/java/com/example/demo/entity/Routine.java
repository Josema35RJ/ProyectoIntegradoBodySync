package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

//La clase Routine representa una rutina de ejercicios que puede ser realizada por un usuario.
@Entity
@Table(name = "routine")
@Data
public class Routine {
	// Identificador único para cada rutina.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "The name is required")
	private String name;

	// Lista de ejercicios que componen la rutina.
	@NotEmpty(message = "Exercise list cannot be empty")
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Exercise> exerciseList;

	// Número de días a la semana que se debe realizar la rutina.
	@Min(value = 1, message = "There must be at least one day per week")
	private int daysPerWeek;

	// Referencia al usuario del gimnasio al que pertenece el ejercicio.
	@ManyToOne
	@JoinColumn(name = "gym_user_id")
	private GymUser gymUser;
}

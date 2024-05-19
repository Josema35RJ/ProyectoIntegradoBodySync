package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entity.Achievement;
import com.example.demo.entity.ClassReservation;
import com.example.demo.entity.Exercise;
import com.example.demo.entity.GymClass;
import com.example.demo.entity.GymUser;
import com.example.demo.entity.MealLog;
import com.example.demo.entity.MusclePainLog;
import com.example.demo.entity.NutritionPlan;
import com.example.demo.entity.Routine;
import com.example.demo.entity.Speciality;
import com.example.demo.entity.WorkoutLog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class SpecialityModel {
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

	public SpecialityModel() {
		super();
	}

	public SpecialityModel(int id, @NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Description is required") String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "SpecialityModel [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
}
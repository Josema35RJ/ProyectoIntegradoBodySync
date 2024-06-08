package com.example.demo.model;

import com.example.demo.entity.GymUser;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

public class ExerciseModel {
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
	
	private GymUserModel gymUser;

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

	public String getMuscleGroup() {
		return muscleGroup;
	}

	public void setMuscleGroup(String muscleGroup) {
		this.muscleGroup = muscleGroup;
	}

	public String getNecessaryEquipment() {
		return necessaryEquipment;
	}

	public void setNecessaryEquipment(String necessaryEquipment) {
		this.necessaryEquipment = necessaryEquipment;
	}

	public GymUserModel getGymUser() {
		return gymUser;
	}

	public void setGymUser(GymUserModel gymUser) {
		this.gymUser = gymUser;
	}

	@Override
	public String toString() {
		return "ExerciseModel [id=" + id + ", name=" + name + ", description=" + description + ", muscleGroup="
				+ muscleGroup + ", necessaryEquipment=" + necessaryEquipment + ", gymUser=" + gymUser + "]";
	}
}

package com.example.demo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

public class MealLogModel {
	// Identificador único para cada registro de comida.
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;

		// Usuario que ha hecho el registro de comida.
		@JsonIgnore
		@ManyToOne
		@JoinColumn(name = "gym_user_id") 
		private GymUserModel gymUser;

		// Fecha de la comida.
		private LocalDateTime mealDate;

		// Descripción de la comida.
		@NotBlank(message = "Meal description is required")
		private String mealDescription;

		// Calorías consumidas en la comida.
		private Integer caloriesConsumed;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public GymUserModel getGymUser() {
			return gymUser;
		}

		public void setGymUser(GymUserModel gymUser) {
			this.gymUser = gymUser;
		}

		public LocalDateTime getMealDate() {
			return mealDate;
		}

		public void setMealDate(LocalDateTime mealDate) {
			this.mealDate = mealDate;
		}

		public String getMealDescription() {
			return mealDescription;
		}

		public void setMealDescription(String mealDescription) {
			this.mealDescription = mealDescription;
		}

		public Integer getCaloriesConsumed() {
			return caloriesConsumed;
		}

		public void setCaloriesConsumed(Integer caloriesConsumed) {
			this.caloriesConsumed = caloriesConsumed;
		}

		@Override
		public String toString() {
			return "MealLogModel [id=" + id + ", gymUser=" + gymUser + ", mealDate=" + mealDate + ", mealDescription="
					+ mealDescription + ", caloriesConsumed=" + caloriesConsumed + "]";
		}
}

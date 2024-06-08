package com.example.demo.model;

import java.time.LocalDateTime;

import com.example.demo.entity.GymUser;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NutritionPlanModel {
		// Identificador único para cada plan de nutrición.
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;

		// Usuario al que pertenece el plan de nutrición.
		@ManyToOne
		@NotNull(message = "GymUser is required")
		private GymUserModel gymUser;

		// Nombre del plan de nutrición.
		@NotBlank(message = "Name is required")
		private String name;

		// Descripción del plan de nutrición.
		@NotBlank(message = "Description is required")
		private String description;

		// Fecha de creación del plan de nutrición.
		private LocalDateTime createdAt;

		// Fecha de última modificación del plan de nutrición.
		private LocalDateTime updatedAt;

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

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

		@Override
		public String toString() {
			return "NutritionPlanModel [id=" + id + ", gymUser=" + gymUser + ", name=" + name + ", description="
					+ description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
		}
		
}

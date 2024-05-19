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

public class ClassFeedbackModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@NotNull(message = "GymUser is required")
	private GymUser gymUser;

	@ManyToOne
	@NotNull(message = "GymClass is required")
	private GymClass gymClass;

	private Integer rating; // e.g. on a scale of 1-5

	@NotBlank(message = "Comments are required")
	private String comments;

	public ClassFeedbackModel() {
		super();
	}

	public ClassFeedbackModel(Integer id, @NotNull(message = "GymUser is required") GymUser gymUser,
			@NotNull(message = "GymClass is required") GymClass gymClass, Integer rating,
			@NotBlank(message = "Comments are required") String comments) {
		super();
		this.id = id;
		this.gymUser = gymUser;
		this.gymClass = gymClass;
		this.rating = rating;
		this.comments = comments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GymUser getGymUser() {
		return gymUser;
	}

	public void setGymUser(GymUser gymUser) {
		this.gymUser = gymUser;
	}

	public GymClass getGymClass() {
		return gymClass;
	}

	public void setGymClass(GymClass gymClass) {
		this.gymClass = gymClass;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "ClassFeedbackModel [id=" + id + ", gymUser=" + gymUser + ", gymClass=" + gymClass + ", rating=" + rating
				+ ", comments=" + comments + "]";
	}

}
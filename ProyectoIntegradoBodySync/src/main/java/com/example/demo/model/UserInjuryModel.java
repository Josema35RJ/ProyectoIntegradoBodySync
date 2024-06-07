package com.example.demo.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entity.GymUser;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

public class UserInjuryModel {
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Integer id;

	 // Nombre de la lesión o músculo afectado.
	 @Column(name = "injury_name", nullable = false)
	 @NotBlank(message = "The injury name is required")
	 private String injuryName;

	 // Descripción de la lesión o dolor.
	 @Column(name = "description")
	 private String description;

	 // Severidad de la lesión o dolor.
	 @Column(name = "severity")
	 private String severity;

	 // Fecha de inicio de la lesión o dolor.
	 @Column(name = "start_date")
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	 private LocalDate startDate;

	 // Fecha estimada de recuperación.
	 @Column(name = "recovery_date")
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	 private LocalDate recoveryDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInjuryName() {
		return injuryName;
	}

	public void setInjuryName(String injuryName) {
		this.injuryName = injuryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getRecoveryDate() {
		return recoveryDate;
	}

	public void setRecoveryDate(LocalDate recoveryDate) {
		this.recoveryDate = recoveryDate;
	}

	@Override
	public String toString() {
		return "UserInjuryModel [id=" + id + ", injuryName=" + injuryName + ", description=" + description
				+ ", severity=" + severity + ", startDate=" + startDate + ", recoveryDate=" + recoveryDate + "]";
	}

}

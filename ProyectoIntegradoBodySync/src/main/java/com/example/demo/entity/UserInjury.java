package com.example.demo.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//Clase para representar lesiones o dolores específicos.
@Entity
@Data
@Table(name = "user_injury")
public class UserInjury {
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
	
	 @OneToMany(mappedBy = "userInjury", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	    private Set<UserInjuryStatus> injuryStatuses = new HashSet<>();
	 
}

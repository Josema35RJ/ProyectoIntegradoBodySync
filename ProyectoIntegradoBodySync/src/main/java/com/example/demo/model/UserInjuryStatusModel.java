package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entity.GymUser;
import com.example.demo.entity.UserInjury;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

public class UserInjuryStatusModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private GymUser gymUser;

    @ManyToOne
    @JoinColumn(name = "injury_id", nullable = false)
    private UserInjury userInjury;

    @ElementCollection
    @CollectionTable(name = "activation_dates", joinColumns = @JoinColumn(name = "user_injury_status_id"))
    @Column(name = "activation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private List<LocalDateTime> activationDates = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "deactivation_dates", joinColumns = @JoinColumn(name = "user_injury_status_id"))
    @Column(name = "deactivation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private List<LocalDateTime> deactivationDates = new ArrayList<>();

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    // Métodos para activar y desactivar la lesión
    public void activate() {
        this.isActive = true;
        this.activationDates.add(LocalDateTime.now());
    }

    public void deactivate() {
        this.isActive = false;
        this.deactivationDates.add(LocalDateTime.now());
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

	public UserInjury getUserInjury() {
		return userInjury;
	}

	public void setUserInjury(UserInjury userInjury) {
		this.userInjury = userInjury;
	}

	public List<LocalDateTime> getActivationDates() {
		return activationDates;
	}

	public void setActivationDates(List<LocalDateTime> activationDates) {
		this.activationDates = activationDates;
	}

	public List<LocalDateTime> getDeactivationDates() {
		return deactivationDates;
	}

	public void setDeactivationDates(List<LocalDateTime> deactivationDates) {
		this.deactivationDates = deactivationDates;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "UserInjuryStatusModel [id=" + id + ", gymUser=" + gymUser + ", userInjury=" + userInjury
				+ ", activationDates=" + activationDates + ", deactivationDates=" + deactivationDates + ", isActive="
				+ isActive + "]";
	}
    
    
}

package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
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

@Entity
@Data
@Table(name = "user_injury_status")
public class UserInjuryStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
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
}

package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "class_feedback")
@Data
public class ClassFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @NotNull(message = "GymUser is required")
    @ToString.Exclude
    @JsonBackReference
    private GymUser gymUser;

    @ManyToOne
    @NotNull(message = "GymClass is required")
    @ToString.Exclude
    @JsonBackReference
    private GymClass gymClass;

    private Integer rating; // e.g. on a scale of 1-5

    @NotBlank(message = "Comments are required")
    private String comments;
}

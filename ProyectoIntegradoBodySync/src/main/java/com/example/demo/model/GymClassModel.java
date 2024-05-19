package com.example.demo.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entity.GymUser;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GymClassModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Start date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "class_days", joinColumns = @JoinColumn(name = "class_id"))
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysOfWeek;

    @NotNull(message = "Time is required")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @Min(value = 1, message = "Duration must be greater than 0")
    private int duration;

    @Min(value = 1, message = "Maximum capacity must be greater than 0")
    private int maximumCapacity;

    @ManyToOne
    @JoinColumn(name="instructor_id", nullable=false)
    private GymUser instructor;
    
    private boolean active = true; // Nuevo campo

    @ElementCollection
    private Set<Integer> attendeeIds = new HashSet<>();

    @ElementCollection
    private Set<Integer> reservationIds = new HashSet<>();

    public boolean addReservation(int userId) {
        if (reservationIds.size() < maximumCapacity) {
            return reservationIds.add(userId);
        }
        return false;
    }

    public boolean addAttendee(int userId) {
        if (attendeeIds.size() < maximumCapacity) {
            return attendeeIds.add(userId);
        }
        return false;
    }

    public boolean isFull() {
        return reservationIds.size() >= maximumCapacity;
    }

    public int getAttendanceCount() {
        return attendeeIds.size();
    }

    public double getOccupancyRate() {
        return (double) attendeeIds.size() / maximumCapacity;
    }

    public String getStatus() {
        LocalDate now = LocalDate.now();
        if (now.isAfter(startDate) && now.isBefore(endDate.plusDays(1))) {
            LocalTime nowTime = LocalTime.now();
            if (nowTime.isAfter(time) && nowTime.isBefore(time.plusMinutes(duration))) {
                return "ongoing";
            }
        }
        if (now.isAfter(endDate)) {
            return "finished";
        }
        return "upcoming";
    }
}
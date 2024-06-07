package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

// La clase GymUser representa a un usuario del gimnasio.
@Data
@Entity
@Table(name = "gym_user")
public class GymUser {
    // Identificador único para cada usuario.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Nombre del usuario.
    @Column(name = "first_name", nullable = false)
    @Size(max = 100, message = "The first name cannot exceed 100 characters")
    @NotBlank(message = "The first name is required")
    private String firstName;

    // Apellido del usuario.
    @Column(name = "last_name", nullable = false)
    @Size(max = 100, message = "The last name cannot exceed 100 characters")
    @NotBlank(message = "The last name is required")
    private String lastName;

    // DNI del usuario.
    @Column(name = "dni", nullable = false, unique = true)
    @Size(max = 9, message = "The DNI cannot exceed 9 characters")
    @NotBlank(message = "The DNI is required")
    private String dni;

    // Código postal del usuario.
    @Column(name = "postal_code", nullable = false)
    @Size(max = 5, message = "The postal code cannot exceed 5 characters")
    @NotBlank(message = "The postal code is required")
    private String postalCode;

    // Provincia del usuario.
    @Column(name = "province", nullable = false)
    @Size(max = 100, message = "The province cannot exceed 100 characters")
    @NotBlank(message = "The province is required")
    private String province;

    // Ciudad del usuario.
    @Column(name = "city", nullable = false)
    @Size(max = 100, message = "The city cannot exceed 100 characters")
    @NotBlank(message = "The city is required")
    private String city;

    // Email del usuario.
    @Column(name = "email", nullable = false)
    @Email
    @Size(max = 100, message = "The email cannot exceed 100 characters")
    @NotBlank(message = "The email is required")
    private String username;

    // Contraseña del usuario.
    @Column(name = "password", nullable = false)
    @NotBlank(message = "The password is required")
    private String password;

    // Rol del usuario (puede ser "user", "instructor" o "gymOwner").
    @Column(name = "role", nullable = false)
    @NotBlank(message = "The role is required")
    private String role;

    // Fecha de nacimiento del usuario.
    @Past(message = "The birth date must be a past date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    // Peso del usuario.
    @Positive(message = "The weight must be a positive number")
    private Float weight;

    // Altura del usuario.
    @Positive(message = "The height must be a positive number")
    private Float height;

    // Nivel de actividad del usuario.
    private String activityLevel;

    // Objetivo de fitness del usuario.
    private String goal;

    // Indica si el usuario ha sido eliminado.
    @NotNull
    private boolean deleted;

    // Indica si el usuario está activo.
    @NotNull
    private boolean enabled;

    // Biografía del instructor.
    @Size(max = 500)
    private String biography;

    // Lista de especialidades del instructor.
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Speciality> specialtyList = new ArrayList<>();

    // Nombre del gimnasio del propietario.
    @Size(max = 500)
    private String gymName;

    // Ubicación del gimnasio del propietario.
    @Size(max = 500)
    private String gymLocation;

    // Lista de clases a las que está inscrito el miembro.
    // Si es Instructor, clases donde es instructor
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(name = "enrolledClasses")
    @ToString.Exclude
    private Set<GymClass> enrolledClasses = new HashSet<>();

    // Estado de los pagos del miembro.
    @Column(name = "payment_status")
    private Boolean paymentStatus = true;

    // Cantidad que el miembro debe, si es que debe algo.
    @Column(name = "debt")
    private Double debt;

    // Días de la semana que el miembro asiste al gimnasio.
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "attendance_days", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "day")
    @ToString.Exclude
    private Set<String> attendanceDays = new HashSet<>();

    // Fecha de creación del registro del miembro.
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    // Fecha de última modificación del usuario.
    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // Lista de rutinas del usuario.
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Routine> routines = new ArrayList<>();

    // Lista de ejercicios del usuario.
    @OneToMany()
    @ToString.Exclude
    private List<Exercise> exercises = new ArrayList<>();

    // Lista de planes de nutrición del usuario.
    @OneToMany()
    @ToString.Exclude
    private List<NutritionPlan> nutritionPlans = new ArrayList<>();

    // Lista de logros del usuario.
    @OneToMany()
    @ToString.Exclude
    private List<Achievement> achievements = new ArrayList<>();

    // Lista de reservas de clases del usuario.
    @OneToMany()
    @ToString.Exclude
    private List<ClassReservation> classReservations = new ArrayList<>();

    // Lista de registros de entrenamiento del usuario.
    @OneToMany()
    @ToString.Exclude
    private List<WorkoutLog> workoutLogs = new ArrayList<>();

    // Lista de registros de comidas del usuario.
    @OneToMany()
    @ToString.Exclude
    private List<MealLog> mealLogs = new ArrayList<>();

    // Lista de registros de dolor muscular del usuario.
    @OneToMany()
    @ToString.Exclude
    private List<MusclePainLog> musclePainLogs = new ArrayList<>();

    // Asistencia del usuario.
    private Integer attendance;

    // Lista de lesiones o músculos lesionados del usuario.
    @OneToMany(mappedBy = "gymUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<UserInjuryStatus> injuryStatuses = new HashSet<>();
    
    private Boolean churn;

    // Lista de gymBros.
    @ManyToMany
    @JoinTable(
        name = "gym_bros",
        joinColumns = @JoinColumn(name = "gym_user_id"),
        inverseJoinColumns = @JoinColumn(name = "gym_bro_id")
    )
    @ToString.Exclude
    private List<GymUser> gymBros = new ArrayList<>();

    @OneToMany(mappedBy = "gymUser")
    @JsonManagedReference
    @ToString.Exclude
    private Set<ClassFeedback> feedbacks = new HashSet<>();

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    // Método para activar o desactivar un usuario.
    public void toggleActivation() {
        this.enabled = !this.enabled;
    }

    public Boolean getChurn() {
        return churn;
    }

    public void setChurn(Boolean churn) {
        this.churn = churn;
    }
}

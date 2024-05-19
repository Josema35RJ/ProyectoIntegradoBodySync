package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entity.Achievement;
import com.example.demo.entity.ClassReservation;
import com.example.demo.entity.Exercise;
import com.example.demo.entity.GymClass;
import com.example.demo.entity.MealLog;
import com.example.demo.entity.MusclePainLog;
import com.example.demo.entity.NutritionPlan;
import com.example.demo.entity.Routine;
import com.example.demo.entity.Speciality;
import com.example.demo.entity.WorkoutLog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class GymUserModel {

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
	@Column(name = "dni", nullable = false)
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
	@NotBlank(message = "The activity level is required")
	private String activityLevel;

	// Objetivo de fitness del usuario.
	@NotBlank(message = "The fitness goal is required")
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
	private List<Speciality> specialtyList;

	// Nombre del gimnasio del propietario.
	@Size(max = 500)
	private String gymName;

	// Ubicación del gimnasio del propietario.
	@Size(max = 500)
	private String gymLocation;

	// Fecha de creación del registro del miembro.
	@CreatedDate
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;

	// Fecha de última modificación del usuario.
	@LastModifiedDate
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	// Lista de rutinas del usuario.
	@OneToMany(mappedBy = "gymUser")
	private List<Routine> routines;

	// Lista de ejercicios del usuario.
	@OneToMany(mappedBy = "gymUser")
	private List<Exercise> exercises;

	// Lista de planes de nutrición del usuario.
	@OneToMany(mappedBy = "gymUser")
	private List<NutritionPlan> nutritionPlans;

	// Lista de logros del usuario.
	@OneToMany(mappedBy = "gymUser")
	private List<Achievement> achievements;

	// Lista de reservas de clases del usuario.
	@OneToMany(mappedBy = "gymUser")
	private List<ClassReservation> classReservations;

	// Lista de registros de entrenamiento del usuario.
	@OneToMany(mappedBy = "gymUser")
	private List<WorkoutLog> workoutLogs;

	// Lista de registros de comidas del usuario.
	@OneToMany(mappedBy = "gymUser")
	private List<MealLog> mealLogs;

	// Lista de registros de dolor muscular del usuario.
	@OneToMany(mappedBy = "gymUser")
	private List<MusclePainLog> musclePainLogs;

	// Lista de clases a las que está inscrito el miembro.
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<GymClass> enrolledClasses;

	// Estado de los pagos del miembro.
	@Column(name = "payment_status")
	private Boolean paymentStatus = true;

	// Cantidad que el miembro debe, si es que debe algo.
	@Column(name = "debt")
	private Double debt;
	// Asistencia del usuario.
	private Integer attendance;

	// Lista de lesiones o músculos lesionados del usuario.
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "user_injuries", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "injury")
	private Set<String> injuriesList;

	private Boolean churn;

	// Días de la semana que el miembro asiste al gimnasio.
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "attendance_days", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "day")
	private Set<String> attendanceDays;

	public GymUserModel() {
		super();
	}

	public GymUserModel(Integer id,
			@Size(max = 100, message = "The first name cannot exceed 100 characters") @NotBlank(message = "The first name is required") String firstName,
			@Size(max = 100, message = "The last name cannot exceed 100 characters") @NotBlank(message = "The last name is required") String lastName,
			@Size(max = 9, message = "The DNI cannot exceed 9 characters") @NotBlank(message = "The DNI is required") String dni,
			@Size(max = 5, message = "The postal code cannot exceed 5 characters") @NotBlank(message = "The postal code is required") String postalCode,
			@Size(max = 100, message = "The province cannot exceed 100 characters") @NotBlank(message = "The province is required") String province,
			@Size(max = 100, message = "The city cannot exceed 100 characters") @NotBlank(message = "The city is required") String city,
			@Email @Size(max = 100, message = "The email cannot exceed 100 characters") @NotBlank(message = "The email is required") String username,
			@NotBlank(message = "The password is required") String password,
			@NotBlank(message = "The role is required") String role, boolean deleted, boolean enabled,
			@Past(message = "The birth date must be a past date") Date birthDate,
			@Positive(message = "The weight must be a positive number") Float weight,
			@Positive(message = "The height must be a positive number") Float height,
			@NotBlank(message = "The activity level is required") String activityLevel,
			@NotBlank(message = "The fitness goal is required") String goal) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.postalCode = postalCode;
		this.province = province;
		this.city = city;
		this.username = username;
		this.password = password;
		this.role = role;
		this.deleted = false;
		this.enabled = false;
		this.birthDate = birthDate;
		this.weight = weight;
		this.height = height;
		this.activityLevel = activityLevel;
		this.goal = goal;
	}

	public GymUserModel(Integer id,
			@Size(max = 100, message = "The first name cannot exceed 100 characters") @NotBlank(message = "The first name is required") String firstName,
			@Size(max = 100, message = "The last name cannot exceed 100 characters") @NotBlank(message = "The last name is required") String lastName,
			@Size(max = 9, message = "The DNI cannot exceed 9 characters") @NotBlank(message = "The DNI is required") String dni,
			@Size(max = 5, message = "The postal code cannot exceed 5 characters") @NotBlank(message = "The postal code is required") String postalCode,
			@Size(max = 100, message = "The province cannot exceed 100 characters") @NotBlank(message = "The province is required") String province,
			@Size(max = 100, message = "The city cannot exceed 100 characters") @NotBlank(message = "The city is required") String city,
			@Email @Size(max = 100, message = "The email cannot exceed 100 characters") @NotBlank(message = "The email is required") String username,
			@NotBlank(message = "The password is required") String password,
			@NotBlank(message = "The role is required") String role, boolean deleted, boolean enabled,
			@Past(message = "The birth date must be a past date") Date birthDate,
			@Positive(message = "The weight must be a positive number") Float weight,
			@Positive(message = "The height must be a positive number") Float height,
			@NotBlank(message = "The activity level is required") String activityLevel,
			@NotBlank(message = "The fitness goal is required") String goal, @Size(max = 500) String biography,
			@NotEmpty(message = "Specialties list cannot be empty") List<Speciality> specialtyList,
			@Size(max = 500) String gymName, @Size(max = 500) String gymLocation) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.postalCode = postalCode;
		this.province = province;
		this.city = city;
		this.username = username;
		this.password = password;
		this.role = role;
		this.deleted = deleted;
		this.enabled = enabled;
		this.birthDate = birthDate;
		this.weight = weight;
		this.height = height;
		this.activityLevel = activityLevel;
		this.goal = goal;
		this.biography = biography;
		this.specialtyList = specialtyList;
		this.gymName = gymName;
		this.gymLocation = gymLocation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public String getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public List<Speciality> getSpecialtyList() {
		return specialtyList;
	}

	public void setSpecialtyList(List<Speciality> specialtyList) {
		this.specialtyList = specialtyList;
	}

	public String getGymName() {
		return gymName;
	}

	public void setGymName(String gymName) {
		this.gymName = gymName;
	}

	public String getGymLocation() {
		return gymLocation;
	}

	public void setGymLocation(String gymLocation) {
		this.gymLocation = gymLocation;
	}

	public List<Routine> getRoutines() {
		return routines;
	}

	public void setRoutines(List<Routine> routines) {
		this.routines = routines;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public List<NutritionPlan> getNutritionPlans() {
		return nutritionPlans;
	}

	public void setNutritionPlans(List<NutritionPlan> nutritionPlans) {
		this.nutritionPlans = nutritionPlans;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

	public List<ClassReservation> getClassReservations() {
		return classReservations;
	}

	public void setClassReservations(List<ClassReservation> classReservations) {
		this.classReservations = classReservations;
	}

	public List<WorkoutLog> getWorkoutLogs() {
		return workoutLogs;
	}

	public void setWorkoutLogs(List<WorkoutLog> workoutLogs) {
		this.workoutLogs = workoutLogs;
	}

	public List<MealLog> getMealLogs() {
		return mealLogs;
	}

	public void setMealLogs(List<MealLog> mealLogs) {
		this.mealLogs = mealLogs;
	}

	public List<MusclePainLog> getMusclePainLogs() {
		return musclePainLogs;
	}

	public void setMusclePainLogs(List<MusclePainLog> musclePainLogs) {
		this.musclePainLogs = musclePainLogs;
	}

	public Set<GymClass> getEnrolledClasses() {
		return enrolledClasses;
	}

	public void setEnrolledClasses(Set<GymClass> enrolledClasses) {
		this.enrolledClasses = enrolledClasses;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getDebt() {
		return debt;
	}

	public void setDebt(Double debt) {
		this.debt = debt;
	}

	public Set<String> getAttendanceDays() {
		return attendanceDays;
	}

	public void setAttendanceDays(Set<String> attendanceDays) {
		this.attendanceDays = attendanceDays;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getAttendance() {
		return attendance;
	}

	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}

	

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Boolean getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Set<String> getInjuriesList() {
		return injuriesList;
	}

	public void setInjuriesList(Set<String> injuriesList) {
		this.injuriesList = injuriesList;
	}

	public Boolean getChurn() {
		return churn;
	}

	public void setChurn(Boolean churn) {
		this.churn = churn;
	}

	@Override
	public String toString() {
		return "GymUserModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dni=" + dni
				+ ", postalCode=" + postalCode + ", province=" + province + ", city=" + city + ", username=" + username
				+ ", password=" + password + ", role=" + role + ", birthDate=" + birthDate + ", weight=" + weight
				+ ", height=" + height + ", activityLevel=" + activityLevel + ", goal=" + goal + ", deleted=" + deleted
				+ ", enabled=" + enabled + ", biography=" + biography + ", specialtyList=" + specialtyList
				+ ", gymName=" + gymName + ", gymLocation=" + gymLocation + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", routines=" + routines + ", exercises=" + exercises
				+ ", nutritionPlans=" + nutritionPlans + ", achievements=" + achievements + ", classReservations="
				+ classReservations + ", workoutLogs=" + workoutLogs + ", mealLogs=" + mealLogs + ", musclePainLogs="
				+ musclePainLogs + ", enrolledClasses=" + enrolledClasses + ", paymentStatus=" + paymentStatus
				+ ", debt=" + debt + ", attendance=" + attendance + ", injuriesList=" + injuriesList + ", churn="
				+ churn + ", attendanceDays=" + attendanceDays + "]";
	}

	
}
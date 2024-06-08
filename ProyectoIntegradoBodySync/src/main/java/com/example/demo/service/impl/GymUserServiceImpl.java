package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.converter.ExerciseConverter;
import com.example.demo.converter.GymUserConverter;
import com.example.demo.entity.Exercise;
import com.example.demo.entity.GymUser;
import com.example.demo.entity.Routine;
import com.example.demo.model.ExerciseModel;
import com.example.demo.model.GymUserModel;
import com.example.demo.repository.GymUserRepository;
import com.example.demo.repository.NutritionPlanRepository;
import com.example.demo.repository.RoutineRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.GymUserService;

import jakarta.transaction.Transactional;

@Service("gymUserService")
public class GymUserServiceImpl implements UserDetailsService, GymUserService {

	@Autowired
	@Qualifier("gymUserRepository")
	private GymUserRepository gymUserRepository;
	
	@Autowired
	@Qualifier("routineRepository")
	private RoutineRepository routineRepository;
	
	@Autowired
	@Qualifier("nutritionPlanRepository")
	private NutritionPlanRepository nutritionPlanRepository;
	
	@Autowired
	@Qualifier("gymUserConverter")
	private GymUserConverter gymUserConverter;
	
	@Autowired
	@Qualifier("exerciseConverter")
	private ExerciseConverter exerciseConverter;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		CustomUserDetails builder = null;
		GymUser user = gymUserRepository.findByUsername(email);

		if (user != null) {
			if (user.isEnabled()) {
				builder = new CustomUserDetails(user.getFirstName(), user.getPassword(), 
						Collections.singletonList(new SimpleGrantedAuthority(user.getRole())), user.getId());
			} else {
				throw new DisabledException("El usuario no está activado");
			}
		} else {
			throw new UsernameNotFoundException("Alumno no encontrado con el email: " + email);
		}

		return builder;
	}
	
	public float calculateBMI(GymUserModel user) {
	    if (user.getHeight() != null && user.getWeight() != null) {
	        float heightInMeters = user.getHeight() / 100;
	        return user.getWeight() / (heightInMeters * heightInMeters);
	    } else {
	        return 0;
	    }
	}


	public int calculateAge(Date birthDate) {
	    if (birthDate != null) {
	        LocalDate localBirthDate = convertToLocalDateViaInstant(birthDate);
	        return Period.between(localBirthDate, LocalDate.now()).getYears();
	    } else {
	        return 0;
	    }
	}


	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}

	
	@Override
	public List<GymUserModel> ListAllGymUsers() {
	    List<GymUserModel> users = new ArrayList<>();
	    for (GymUser gymUser : gymUserRepository.findByDeletedAndRole(false,"ROL_GYMUSER")) {
	    	users.add(gymUserConverter.transform(gymUser));
	    }
	    return users;
	}
	
	@Override
	public List<GymUserModel> ListAllGymUsersInstructores() {
	    List<GymUserModel> users = new ArrayList<>();
	    for (GymUser gymUser : gymUserRepository.findByDeletedAndRole(false,"ROL_GYMINSTRUCTOR")) {
	    	users.add(gymUserConverter.transform(gymUser));
	    }
	    return users;
	}

		
	@Override
	public boolean existeUsername(String email) {
		// TODO Auto-generated method stub
		return gymUserRepository.existsByUsername(email);
	}
	
	@Override
	public boolean activarDesactivar(int id) {
		GymUser gymUser = gymUserRepository.findById(id).get();
		if (gymUser != null) {
			if(!gymUser.isEnabled()) 
				gymUser.setEnabled(true);
			else 
				gymUser.setEnabled(false);
			gymUserRepository.save(gymUser);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean eliminarGymUser(int id) {
	    GymUser gymUser = gymUserRepository.findById(id).get();
	    if (gymUser != null) {
	        if(!gymUser.isDeleted()) {
	            gymUser.setDeleted(true);
	            gymUserRepository.save(gymUser);
	            return true;
	        }
	    }
	    return false;
	}

	
	@Override
	public GymUserModel getGymUserById(int id) {
		// TODO Auto-generated method stub
		GymUser gymUser = gymUserRepository.findById(id).orElse(null);

		return gymUserConverter.transform(gymUser);
	}
	@Override
	public GymUser updateUser(GymUserModel gymUserModel) {
		// TODO Auto-generated method stub
		GymUser gymUser = gymUserConverter.transform(gymUserModel);
		gymUserRepository.save(gymUser);
		return gymUser;
	}
	
	@Override
	public List<String> getEmails() {
		List<String> emails = new ArrayList<>();
		for (GymUser gymUser : gymUserRepository.findAll()) {
			if (gymUser.isDeleted() && gymUser.getRole().equals("ROL_GYMUSER"))
				emails.add(gymUser.getUsername());
		}
		return emails;
	}
	
	@Override
	public GymUser registrar(GymUserModel gymUserModel) {
		// TODO Auto-generated method stub
		GymUser gymUser = gymUserConverter.transform(gymUserModel);
		gymUser.setPassword(passwordEncoder().encode(gymUser.getPassword()));
		gymUser.setEnabled(true);
		gymUser.setDeleted(false);
		if(gymUser.getRole().equalsIgnoreCase("GymUser")){
			gymUser.setRole("ROL_GYMUSER");
		}else if (gymUser.getRole().equalsIgnoreCase("GymInstructor")){
			gymUser.setRole("ROL_GYMINSTRUCTOR");
		}else if (gymUser.getRole().equalsIgnoreCase("GymOwner")){
			gymUser.setRole("ROL_GYMOWNER");
		}
			
		return gymUserRepository.save(gymUser);
	}

	public List<GymUserModel> ListGymUsersByClassId(int classId) {
		List<GymUserModel> l = new ArrayList<>();
		for(GymUser g : gymUserRepository.findByEnrolledClasses_Id(classId)) {
			l.add(gymUserConverter.transform(g));
		}
        return l;
    }

	@Override
	public List<ExerciseModel> ListExercisesModelByGymUser(int id) {
		// TODO Auto-generated method stub
		List<ExerciseModel> l = new ArrayList<>();
		for(Exercise exe: gymUserRepository.findById(id).get().getExercises()) {
			l.add(exerciseConverter.transform(exe));
			
		}
		return l;
	}

	@Override
	public void setListExercisesModelByGymUser(List<ExerciseModel> exercises, int id) {
		// TODO Auto-generated method stub
		List<Exercise> l = new ArrayList<>();
		for(ExerciseModel exe: exercises) {
			l.add(exerciseConverter.transform(exe));
			
		}
		gymUserRepository.findById(id).get().setExercises(l);
	}

	@Override
	public void addRoutineToUser(Integer userId, Routine routine) {
		// TODO Auto-generated method stub
		 GymUser gymUser = gymUserRepository.findById(userId).get();
	        
	        // Establecer la relación bidireccional
	  
	        gymUser.getRoutines().add(routine);

	        gymUserRepository.save(gymUser);
	}
	
	 
	  
}

package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.GymUser;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.service.InstructorService;

@Service("instructorService")
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	@Qualifier("instructorRepository")
	private InstructorRepository instructorRepository;

	@Override
	public List<GymUser> getAllInstructors() {
		return instructorRepository.findByDeletedAndRole(false, "ROL_GYMINSTRUCTOR");
	}
	
	

	@Override
	public GymUser getInstructorById(int id) {
		return instructorRepository.findById(id).orElse(null);
	}
	
	

	@Override
	public void updateInstructor( GymUser instructorDetails) {
		GymUser instructor = instructorRepository.findById(instructorDetails.getId()).orElse(null);
		if (instructor != null) {
			instructor.setFirstName(instructorDetails.getFirstName());
			instructor.setUsername(instructorDetails.getUsername());
			instructor.setSpecialtyList(instructorDetails.getSpecialtyList());
			instructorRepository.save(instructor);
		}
	}

	@Override
	public void deleteInstructor(int id) {
		instructorRepository.deleteById(id);
	}



	@Override
	public void createInstructor(GymUser instructor) {
		instructorRepository.save(instructor);
		
	}
}
package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.GymUserConverter;
import com.example.demo.entity.GymUser;
import com.example.demo.model.GymUserModel;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.service.InstructorService;

@Service("instructorService")
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	@Qualifier("instructorRepository")
	private InstructorRepository instructorRepository;
	
	@Autowired
	@Qualifier("gymUserConverter")
	private GymUserConverter gymUserConverter;

	@Override
	public List<GymUserModel> getAllInstructors() {
		 List<GymUserModel> l = new ArrayList<>();
		for(GymUser g: instructorRepository.findByDeletedAndRole(false, "ROL_GYMINSTRUCTOR")) {
			l.add(gymUserConverter.transform(g));
		}
		return l;
	}
	
	

	@Override
	public GymUserModel getInstructorById(int id) {
		return gymUserConverter.transform(instructorRepository.findById(id).get());
	}
	
	

	@Override
	public void updateInstructor(GymUserModel instructorDetails) {
		GymUserModel instructor = gymUserConverter.transform(instructorRepository.findById(instructorDetails.getId()).get());
		if (instructor != null) {
			instructor.setFirstName(instructorDetails.getFirstName());
			instructor.setUsername(instructorDetails.getUsername());
			instructor.setSpecialtyList(instructorDetails.getSpecialtyList());
			instructorRepository.save(gymUserConverter.transform(instructor));
		}
	}

	@Override
	public void deleteInstructor(int id) {
		instructorRepository.deleteById(id);
	}



	@Override
	public void createInstructor(GymUserModel instructor) {
		instructorRepository.save(gymUserConverter.transform(instructor));
		
	}
}
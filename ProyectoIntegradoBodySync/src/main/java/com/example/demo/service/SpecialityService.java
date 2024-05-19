package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Speciality;

public interface SpecialityService {

	List<Speciality> getAllISpecialities();

	Speciality getSpecialityById(int id);

	void saveSpeciality(SpecialityService speciality);

	void updateSpeciality(int id, SpecialityService speciality);

	void deleteSpeciality(int id);

}

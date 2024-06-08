package com.example.demo.service;

import java.util.List;

import com.example.demo.model.SpecialityModel;

public interface SpecialityService {

	List<SpecialityModel> getAllISpecialities();

	SpecialityModel getSpecialityById(int id);

	void saveSpeciality(SpecialityService speciality);

	void updateSpeciality(int id, SpecialityService speciality);

	void deleteSpeciality(int id);

}

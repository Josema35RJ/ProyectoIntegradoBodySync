package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.SpecialityConverter;
import com.example.demo.entity.Speciality;
import com.example.demo.model.SpecialityModel;
import com.example.demo.repository.SpecialityRepository;
import com.example.demo.service.SpecialityService;

@Service("specialityService")
public class SpecialityServiceImpl implements SpecialityService {
 
	@Autowired
	@Qualifier("specialityRepository")
    private SpecialityRepository specialityRepository;
	
	@Autowired
	@Qualifier("specialityConverter")
	private SpecialityConverter specialityConverter;

	@Override
	public List<SpecialityModel> getAllISpecialities() {
		// TODO Auto-generated method stub
		List<SpecialityModel> l = new ArrayList<>();
		for(Speciality s : specialityRepository.findAll()) {
			l.add(specialityConverter.transform(s));
		}
		return l;
	}

	@Override
	public SpecialityModel getSpecialityById(int id) {
		// TODO Auto-generated method stub
		return specialityConverter.transform(specialityRepository.findById(id).get());
	}

	@Override
	public void saveSpeciality(SpecialityService speciality) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSpeciality(int id, SpecialityService speciality) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSpeciality(int id) {
		// TODO Auto-generated method stub
		
	}
}
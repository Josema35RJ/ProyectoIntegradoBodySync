package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.GymUserConverter;
import com.example.demo.converter.RoutineConverter;
import com.example.demo.entity.Routine;
import com.example.demo.model.GymUserModel;
import com.example.demo.model.RoutineModel;
import com.example.demo.repository.RoutineRepository;
import com.example.demo.service.RoutineService;

@Service("routineService")
public class RoutineServiceImpl implements RoutineService{

	@Autowired
	@Qualifier("routineRepository")
    private RoutineRepository routineRepository;
	
	@Autowired
	@Qualifier("routineConverter")
    private RoutineConverter routineConverter;
	
	@Autowired
	@Qualifier("gymUserConverter")
	private GymUserConverter gymUserConverter;

	@Override
	public RoutineModel findByGymUser(GymUserModel gymUserModel) {
		// TODO Auto-generated method stub
		return routineConverter.transform(routineRepository.findByGymUser(gymUserConverter.transform(gymUserModel)));
	}

	@Override
	public List<RoutineModel> ListRoutine() {
		// TODO Auto-generated method stub
		List<RoutineModel> l = new ArrayList<>();
		for(Routine r : routineRepository.findAll()) {
			l.add(routineConverter.transform(r));
		}
		return l;
	}

	@Override
	public void update(RoutineModel routine) {
		// TODO Auto-generated method stub
		routineRepository.save(routineConverter.transform(routine));
	}

	@Override
	public void create(RoutineModel routine) {
		// TODO Auto-generated method stub
		routineRepository.save(routineConverter.transform(routine));
	}

	@Override
	public RoutineModel getById(int id) {
		// TODO Auto-generated method stub
		return routineConverter.transform(routineRepository.findById(id).get());
	}


}

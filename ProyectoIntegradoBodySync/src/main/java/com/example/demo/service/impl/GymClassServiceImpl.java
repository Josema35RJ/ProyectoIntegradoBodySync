package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.GymClassConverter;
import com.example.demo.entity.GymClass;
import com.example.demo.entity.GymUser;
import com.example.demo.model.GymClassModel;
import com.example.demo.model.GymUserModel;
import com.example.demo.repository.GymClassRepository;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.service.GymClassService;

@Service("gymClassService")
public class GymClassServiceImpl implements GymClassService {

	@Autowired
	@Qualifier("gymClassRepository")
	private GymClassRepository gymClassRepository;

	@Autowired
	@Qualifier("instructorRepository")
	private InstructorRepository instructorRepository;

	@Autowired
	@Qualifier("gymClassConverter")
	private GymClassConverter gymClassConverter;

	@Override
	public List<GymClassModel> getAllClasses() {
		List<GymClassModel> l = new ArrayList<>();
		for (GymClass g : gymClassRepository.findAll()) {
			l.add(gymClassConverter.transform(g));
		}
		return l;
	}

	@Override
	public GymClassModel getClassById(int id) {
		return gymClassConverter.transform(gymClassRepository.findById(id).get());
	}

	@Override
	public void addClass(GymClassModel gymClass) {
		gymClassRepository.save(gymClassConverter.transform(gymClass));
	}

	public void updateClass(GymClassModel gymClass) {
		// Obtén la clase existente de la base de datos
		GymClass existingClass = gymClassRepository.findById(gymClass.getId())
				.orElseThrow(() -> new IllegalArgumentException("Clase no encontrada con ID: " + gymClass.getId()));

		// Actualiza la clase existente con los nuevos valores
		existingClass.setName(gymClass.getName());
		existingClass.setDescription(gymClass.getDescription());
		existingClass.setInstructor(gymClass.getInstructor());
		existingClass.setTime(gymClass.getTime());
		existingClass.setMaximumCapacity(gymClass.getMaximumCapacity());

		// Guarda la clase actualizada en la base de datos
		gymClassRepository.save(existingClass);
	}

	public void assignInstructor(int classId, int instructorId) {
		// Encuentra la clase por ID
		GymClass gymClass = gymClassRepository.findById(classId)
				.orElseThrow(() -> new IllegalArgumentException("Clase no encontrada con ID: " + classId));

		// Encuentra el instructor por ID
		GymUser instructor = instructorRepository.findById(instructorId)
				.orElseThrow(() -> new IllegalArgumentException("Instructor no encontrado con ID: " + instructorId));

		// Asigna el instructor a la clase
		gymClass.setInstructor(instructor);

		// Guarda los cambios en la base de datos
		gymClassRepository.save(gymClass);
	}

	public List<GymClassModel> obtenerDatosAsistencia() {
		// Obtener todas las clases y filtrar por las que están en curso
		List<GymClassModel> l = new ArrayList<>();
		for (GymClass g : gymClassRepository.findAll().stream()
				.filter(gymClass -> "ongoing".equals(gymClass.getStatus())).collect(Collectors.toList())) {
			l.add(gymClassConverter.transform(g));
		}
		return l;
	}

	public List<GymClassModel> obtenerDatosPopularidad() {
		// Obtener todas las clases y ordenarlas por la tasa de ocupación
		List<GymClassModel> l = new ArrayList<>();
		for (GymClass g : gymClassRepository.findAll().stream()
				.sorted((c1, c2) -> Double.compare(c2.getOccupancyRate(), c1.getOccupancyRate()))
				.collect(Collectors.toList())) {
		}
		return l;
	}

	@Override
	public void deleteClass(int id) {
		gymClassRepository.deleteById(id);
	}

	@Override
	public List<GymClassModel> getAllClassesFinByInstructorId(GymUserModel instructor) {
		// TODO Auto-generated method stub
		 List<GymClassModel> l = new ArrayList<>();
		for(GymClass g: gymClassRepository.findByInstructorId(instructor.getId())) {
			l.add(gymClassConverter.transform(g));
		}
		return l;
	}
}

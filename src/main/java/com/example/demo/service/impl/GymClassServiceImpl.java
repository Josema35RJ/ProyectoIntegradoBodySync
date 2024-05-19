package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.GymClass;
import com.example.demo.entity.GymUser;
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
    
 

    @Override
    public List<GymClass> getAllClasses() {
        return gymClassRepository.findAll();
    }

    @Override
    public GymClass getClassById(int id) {
        return gymClassRepository.findById(id).orElse(null);
    }

    @Override
    public void addClass(GymClass gymClass) {
        gymClassRepository.save(gymClass);
    }

    public void updateClass(GymClass gymClass) {
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
    
    public List<GymClass> obtenerDatosAsistencia() {
        // Obtener todas las clases y filtrar por las que están en curso
        return gymClassRepository.findAll().stream()
                .filter(gymClass -> "ongoing".equals(gymClass.getStatus()))
                .collect(Collectors.toList());
    }

    public List<GymClass> obtenerDatosPopularidad() {
        // Obtener todas las clases y ordenarlas por la tasa de ocupación
        return gymClassRepository.findAll().stream()
                .sorted((c1, c2) -> Double.compare(c2.getOccupancyRate(), c1.getOccupancyRate()))
                .collect(Collectors.toList());
    }
  
    @Override
    public void deleteClass(int id) {
        gymClassRepository.deleteById(id);
    }

	@Override
	public List<GymClass> getAllClassesFinByInstructorId(GymUser instructor) {
		// TODO Auto-generated method stub
		return gymClassRepository.findByInstructorId(instructor.getId());
	}
}

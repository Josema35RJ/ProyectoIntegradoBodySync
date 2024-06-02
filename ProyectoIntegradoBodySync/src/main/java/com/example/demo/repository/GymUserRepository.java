package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GymUser;
import com.example.demo.model.GymUserModel;

@Repository("gymUserRepository")
public interface GymUserRepository extends JpaRepository<GymUser, Integer> {

    boolean existsByUsername(String email);
	
    List<GymUser> findByDeletedAndRole(boolean x,String role);
	GymUser findByUsername(String email);
	List<GymUser> findByEnrolledClasses_Id(int classId);
	

	 List<GymUser> findByRole( String role);

	Integer countByAttendanceDaysContains(String day);

	Integer countByEnabledAndRole(boolean b, String role);


}

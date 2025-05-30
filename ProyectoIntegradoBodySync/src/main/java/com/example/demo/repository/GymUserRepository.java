package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GymUser;

@Repository("gymUserRepository")
public interface GymUserRepository extends JpaRepository<GymUser, Integer> {

    boolean existsByUsername(String email);
	
    List<GymUser> findByDeletedAndRole(boolean x,String role);
	GymUser findByUsername(String email);
	
	 List<GymUser> findByRole( String role);

	Integer countByAttendanceDaysContains(Date day);

	Integer countByEnabledAndRole(boolean b, String role);

	@Query("SELECT gu FROM GymUser gu JOIN gu.enrolledClasses ec WHERE ec.id = :classId AND gu.deleted = false")
    List<GymUser> findByClassIdAndDeletedFalse(int classId);


}

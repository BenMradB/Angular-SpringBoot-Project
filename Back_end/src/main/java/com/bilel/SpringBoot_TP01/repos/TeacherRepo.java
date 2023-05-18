package com.bilel.SpringBoot_TP01.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bilel.SpringBoot_TP01.entities.Teacher;

@RepositoryRestResource(path = "teachers_rest")
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
	List<Teacher> findByUserName(String userName);
	
	List<Teacher> findByUserNameContains(String name);
	
	@Query("SELECT t FROM Teacher t where t.userName LIKE :userName% AND t.email = :email")
	List<Teacher> findByUserNameEmail(@Param("userName") String userName, @Param("email") String email);
	
	@Query("SELECT t FROM Teacher t ORDER BY t.lastName ASC, t.email DESC")
	List<Teacher> sortTeacherByLastNameEmail();
}

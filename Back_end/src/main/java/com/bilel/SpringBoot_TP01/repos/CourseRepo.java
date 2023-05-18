package com.bilel.SpringBoot_TP01.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bilel.SpringBoot_TP01.entities.Course;
import com.bilel.SpringBoot_TP01.entities.Speciality;
import com.bilel.SpringBoot_TP01.entities.Teacher;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = "courses_rest")
public interface CourseRepo extends JpaRepository<Course, Long> {
	@Query("SELECT c FROM Course c WHERE c.teacher = :teacher")
	List<Course> findByTeacher(@Param("teacher") Teacher teacher);

	List<Course> findByTeacherTeacherId(Long teacherId);

	@Query("SELECT c FROM Course c WHERE c.speciality = :speciality")
	List<Course> findBySpeciality(@Param("speciality") Speciality speciality);

	List<Course> findBySpecialitySpecialityId(Long specialityId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Course c WHERE c.teacher.teacherId = ?1")
	void deleteTeacherCoursesTeacherId(Long teacherId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Course c WHERE c.speciality.specialityId = ?1")
	void deleteSpecialityCoursesSpecialityId(Long specialityId);
}

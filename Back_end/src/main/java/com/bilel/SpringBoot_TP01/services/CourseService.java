package com.bilel.SpringBoot_TP01.services;

import java.util.List;
import org.springframework.data.domain.Page;

import com.bilel.SpringBoot_TP01.entities.Course;
import com.bilel.SpringBoot_TP01.entities.Speciality;
import com.bilel.SpringBoot_TP01.entities.Teacher;

public interface CourseService {
	Course addCourse(Course course);
	Course updateCourse(Course course);
	Course getCourse(Long courseId);
	List<Course> getAllCourses();
	Page<Course> getAllCourseByPage(int page, int size);
	void deleteCourseById(Long courseId);
	void deleteCourse(Course course);
	List<Course> findByTeacher(Teacher teacher);
	List<Course> findByTeacherTeacherId(Long teacherId);
	List<Course> findBySpeciality(Speciality speciality);
	List<Course> findCoursesBySpecialityId(Long specialityId);
	void deleteTeacherCourses(Long teacherId);
	void deleteSpecialityCourses(Long specialityId);

}

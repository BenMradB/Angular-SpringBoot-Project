package com.bilel.SpringBoot_TP01.services.implementations;

import java.util.List;

import com.bilel.SpringBoot_TP01.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bilel.SpringBoot_TP01.entities.Course;
import com.bilel.SpringBoot_TP01.entities.Speciality;
import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.repos.CourseRepo;

@Service
public class CourseServiceI implements CourseService {
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Override
	public Course addCourse(Course course) {
		// TODO Auto-generated method stub
		return this.courseRepo.save(course);
	}

	@Override
	public Course updateCourse(Course course) {
		// TODO Auto-generated method stub
		return this.courseRepo.save(course);
	}

	@Override
	public Course getCourse(Long courseId) {
		// TODO Auto-generated method stub
		return this.courseRepo.findById(courseId).get();
	}

	@Override
	public List<Course> getAllCourses() {
		// TODO Auto-generated method stub
		return this.courseRepo.findAll();
	}

	@Override
	public Page<Course> getAllCourseByPage(int page, int size) {
		// TODO Auto-generated method stub
		return this.courseRepo.findAll(PageRequest.of(page, size));
	}

	@Override
	public void deleteCourseById(Long courseId) {
		// TODO Auto-generated method stub
		this.courseRepo.deleteById(courseId);
	}

	@Override
	public void deleteCourse(Course course) {
		// TODO Auto-generated method stub
		this.courseRepo.delete(course);
	}

	@Override
	public List<Course> findByTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		return this.courseRepo.findByTeacher(teacher);
	}

	@Override
	public List<Course> findByTeacherTeacherId(Long teacherId) {
		// TODO Auto-generated method stub
		return this.courseRepo.findByTeacherTeacherId(teacherId);
	}

	@Override
	public List<Course> findBySpeciality(Speciality speciality) {
		// TODO Auto-generated method stub
		return this.courseRepo.findBySpeciality(speciality);
	}

	@Override
	public List<Course> findCoursesBySpecialityId(Long specialityId) {
		// TODO Auto-generated method stub
		return this.courseRepo.findBySpecialitySpecialityId(specialityId);
	}

	@Override
	public void deleteTeacherCourses(Long teacherId) {
		this.courseRepo.deleteTeacherCoursesTeacherId(teacherId);
	}

	@Override
	public void deleteSpecialityCourses(Long specialityId) {
		this.courseRepo.deleteSpecialityCoursesSpecialityId(specialityId);
	}

}

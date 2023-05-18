package com.bilel.SpringBoot_TP01;

import java.util.List;

import com.bilel.SpringBoot_TP01.entities.Image;
import com.bilel.SpringBoot_TP01.services.ImageService;
import com.bilel.SpringBoot_TP01.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bilel.SpringBoot_TP01.entities.Course;
import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.repos.CourseRepo;
import com.bilel.SpringBoot_TP01.repos.TeacherRepo;
import com.bilel.SpringBoot_TP01.services.TeacherService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
class SpringBootTp01ApplicationTests {
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private ImageService imageService;
	@Autowired UserService userService;
	
	@Autowired
	private TeacherRepo teacherRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Test
	public void testGetTeacher() {
		this.userService.deleteUser(3L);
	}

	@Test
	public void testFindByUserName() {
		List<Teacher> teachers = this.teacherRepo.findByUserName("BenMrad Bilel");
		
		for (Teacher teacher : teachers) {
			System.out.println(teacher.getUserName());
		}
	}
	
	@Test
	public void testFindByUserNameContains() {
		List<Teacher> teachers = this.teacherRepo.findByUserNameContains("BenMrad");
		
		for (Teacher teacher : teachers) {
			System.out.println(teacher.getUserName());
		}
	}
	
	@Test
	public void testFindByUserNameEmail() {
		List<Teacher> teachers = this.teacherRepo.findByUserNameEmail("BenMrad", "bilel@gmail.com");
		
		for (Teacher teacher : teachers) {
			System.out.println(teacher.getUserName() + " " + teacher.getEmail());
		}
	}
	
	@Test
	public void testFindByTeacher() {
		Teacher teacher = this.teacherService.getTeacher(1L);
		List<Course> courses = this.courseRepo.findByTeacher(teacher);
		
		for (Course course : courses) {
			System.out.println(course.getCourseName());
		}
	}
	
	@Test
	public void testFindByTeacherTeacherId() {
		List<Course> courses = this.courseRepo.findByTeacherTeacherId(1L);
		
		for (Course course : courses) {
			System.out.println(course.getCourseName());
		}
	}
	
	@Test
	public void testSortTeachersByLastNameEmail() {
		List<Teacher> teachers = this.teacherRepo.sortTeacherByLastNameEmail();
		
		for (Teacher teacher : teachers) {
			System.out.println(teacher.getLastName() + " " + teacher.getEmail());
		}
	}
}

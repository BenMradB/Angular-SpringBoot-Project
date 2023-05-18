package com.bilel.SpringBoot_TP01.controllers;

import com.bilel.SpringBoot_TP01.entities.Course;
import com.bilel.SpringBoot_TP01.entities.Speciality;
import com.bilel.SpringBoot_TP01.entities.Teacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bilel.SpringBoot_TP01.services.CourseService;
import com.bilel.SpringBoot_TP01.services.SpecialityService;
import com.bilel.SpringBoot_TP01.services.TeacherService;

import jakarta.validation.Valid;

@Controller
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private SpecialityService specialityService;

	public void listOfTeachers(ModelMap modelMap) {
		List<Teacher> teachers = this.teacherService.getAllTeachers();

		modelMap.addAttribute("teachers", teachers);
	}

	public void listOfSpecialities(ModelMap modelMap) {
		List<Speciality> specialities = this.specialityService.getSpecialities();

		modelMap.addAttribute("specialities", specialities);
	}

	public void paginate(ModelMap modelMap, int page, int size) {
		Page<Course> courses = this.courseService.getAllCourseByPage(page, size);
		modelMap.addAttribute("courses", courses);
		modelMap.addAttribute("pages", new int[courses.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	}

	@GetMapping("/newCourse")
	public String getCreateCoursePage(ModelMap modelMap) {
		this.listOfTeachers(modelMap);
		this.listOfSpecialities(modelMap);
		modelMap.addAttribute("course", new Course());
		modelMap.addAttribute("courseInfo", new Course());
		modelMap.addAttribute("mode", "new");
		return "/course/form_course";
	}

	@PostMapping("/manageCourse")
	public String manageCourse(ModelMap modelMap, @Valid @ModelAttribute("course") Course course,
			BindingResult bindingResult, @RequestParam(name = "page", defaultValue = "0") int page) {

		if (course.getCourseId() == null) {
			modelMap.addAttribute("mode", "new");
			Page<Course> courses = this.courseService.getAllCourseByPage(page, 2);
			page = courses.getTotalPages() - 1;
		} else {
			Teacher currentTeacher = course.getTeacher();
			Speciality currentSpeciality = course.getSpeciality();
			modelMap.addAttribute("currentTeacher", currentTeacher);
			modelMap.addAttribute("currentSpeciality", currentSpeciality);
			modelMap.addAttribute("mode", "edit");
		}

		if (bindingResult.hasErrors()) {
			this.listOfTeachers(modelMap);
			this.listOfSpecialities(modelMap);
			modelMap.addAttribute("courseInfo", course);
			modelMap.addAttribute("currentPage", page);
			return "/course/form_course";
		}

		this.courseService.addCourse(course);

		this.paginate(modelMap, page, 2);

		return "/course/list_courses";
	}

	@GetMapping("/courses")
	public String coursesPage(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {

		this.paginate(modelMap, page, size);
		return "/course/list_courses";
	}

	@GetMapping("/deleteCourse")
	public String deleteCourse(ModelMap modelMap, @RequestParam("id") Long courseId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {
		this.courseService.deleteCourseById(courseId);
		this.paginate(modelMap, page, size);

		return "/course/list_courses";
	}

	@GetMapping("/editCourse")
	public String getUpdateCoursePage(ModelMap modelMap, @RequestParam("id") Long courseId,
			@RequestParam("page") int page) {
		Course course = this.courseService.getCourse(courseId);
		Teacher currentTeacher = course.getTeacher();
		Speciality currentSpeciality = course.getSpeciality();

		this.listOfTeachers(modelMap);
		this.listOfSpecialities(modelMap);
		modelMap.addAttribute("course", new Course());
		modelMap.addAttribute("courseInfo", course);
		modelMap.addAttribute("currentTeacher", currentTeacher);
		modelMap.addAttribute("currentSpeciality", currentSpeciality);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("mode", "edit");

		return "/course/form_course";
	}
}

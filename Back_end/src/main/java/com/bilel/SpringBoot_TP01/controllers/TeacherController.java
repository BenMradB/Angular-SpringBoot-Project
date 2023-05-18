package com.bilel.SpringBoot_TP01.controllers;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.services.TeacherService;

import jakarta.validation.Valid;

@Controller
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	public void paginate(ModelMap modelMap, int page, int size) {
		Page<Teacher> teachers = this.teacherService.getTeachersByPage(page, size);

		modelMap.addAttribute("teachers", teachers);
		modelMap.addAttribute("pages", new int[teachers.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	}

	public boolean contains(Teacher teacher) {

		List<Teacher> teachers = this.teacherService.getAllTeachers().stream()
				.filter(t -> teacher.getEmail().equals(t.getEmail())).collect(Collectors.toList());
		return teachers.size() > 0;
	}

	@GetMapping("/new")
	public String getCreateTeacherPage(ModelMap modelMap) {

		modelMap.addAttribute("teacher", new Teacher());
		modelMap.addAttribute("teacherInfo", new Teacher());

		return "/teacher/create_teacher";
	}

	@PostMapping("/createTeacher")
	public String createTeacher(@Valid @ModelAttribute("teacher") Teacher teacher, BindingResult bindingResult,
			@RequestParam("passwordConfirm") String passwordConfirm, @RequestParam("password") String password,
			ModelMap modelMap) throws ParseException {

		if (bindingResult.hasErrors()) {

			System.out.println(teacher.getTeacherId());
			modelMap.addAttribute("teacherInfo", teacher);
			return "/teacher/create_teacher";
		}

		if (passwordConfirm.equals("")) {
			String msg = "Password Confirm Field Must Have A Value .";
			modelMap.addAttribute("msg", msg);
			modelMap.addAttribute("teacherInfo", teacher);
			return "/teacher/create_teacher";
		}

		if (!password.equals(passwordConfirm)) {
			String msg = "Passwords Don't Match !!!";
			modelMap.addAttribute("msg", msg);
			modelMap.addAttribute("teacherInfo", teacher);
			return "/teacher/create_teacher";
		}

		if (contains(teacher)) {
			String error = "There is an account related to this email adress !!! Try onother one .";
			modelMap.addAttribute("error", error);
			modelMap.addAttribute("teacherInfo", teacher);
			return "/teacher/create_teacher";
		}

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String hashedPassword = bcrypt.encode(password);

		teacher.setPassword(hashedPassword);
		teacher.setRole("teacher");

		this.teacherService.createTeacher(teacher);

		int page = 0;

		Page<Teacher> teachers = this.teacherService.getTeachersByPage(page, 2);

		page = teachers.getTotalPages() - 1;

		this.paginate(modelMap, page, 2);

		return "/teacher/list_teachers";
	}

	@GetMapping("/teachers")
	public String getTeachersPage(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {

		this.paginate(modelMap, page, size);

		return "/teacher/list_teachers";
	}

	@GetMapping("/deleteTeacher")
	public String deleteTeacher(ModelMap modelMap, @RequestParam("id") Long teacherId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {

		this.teacherService.deleteTeacherById(teacherId);

		this.paginate(modelMap, page, size);

		return "/teacher/list_teachers";
	}

	@GetMapping("/editTeacher")
	public String getEditTeacherPage(ModelMap modelMap, @RequestParam("id") Long teacherId,
			@RequestParam("page") int page) {

		Teacher teacher = this.teacherService.getTeacher(teacherId);
		modelMap.addAttribute("teacher", teacher);
		modelMap.addAttribute("currentPage", page);

		return "/teacher/update_teacher";
	}

	@PostMapping("/editTeacher")
	public String editTeacher(ModelMap modelMap, @ModelAttribute("teacher") Teacher teacher,
			@RequestParam(name = "page", defaultValue = "0") int page) throws ParseException {

		Teacher t = this.teacherService.getTeacher(teacher.getTeacherId());

		if (teacher.getFirstName().equals("") || teacher.getLastName().equals("") || teacher.getUserName().equals("")
				|| teacher.getEmail().equals("")) {
			String error = "All The Fields Are Required .";
			modelMap.addAttribute("error", error);
			modelMap.addAttribute("teacher", t);
			return "/teacher/update_teacher";
		}

		if (contains(teacher)) {
			if (!t.getEmail().equals(teacher.getEmail())) {
				String error = "Email Already In Use .";
				modelMap.addAttribute("error", error);
				return "/teacher/update_teacher";
			}
		}

		if (!teacher.getPassword().equals("")) {
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			String hashedPassword = bcrypt.encode(teacher.getPassword());
			System.out.println(hashedPassword);

			teacher.setPassword(hashedPassword);
		} else {
			teacher.setPassword(t.getPassword());
		}

		teacher.setRole(t.getRole());

		this.teacherService.createTeacher(teacher);

		return "/teacher/list_teachers";
	}
}

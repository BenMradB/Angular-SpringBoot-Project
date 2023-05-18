package com.bilel.SpringBoot_TP01.services.implementations;

import java.util.List;

import com.bilel.SpringBoot_TP01.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.repos.TeacherRepo;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired
	private TeacherRepo teacherRepo;

	@Override
	public Teacher createTeacher(Teacher teacher) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String hashedPassword = bcrypt.encode(teacher.getPassword());
		teacher.setPassword(hashedPassword);
		
		return this.teacherRepo.save(teacher);
	}

	@Override
	public Teacher updateTeacher(Teacher teacher) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String hashedPassword = bcrypt.encode(teacher.getPassword());
		teacher.setPassword(hashedPassword);
		return this.teacherRepo.save(teacher);
	}

	@Override
	public void deleteTeacher(Teacher teacher) {
		this.teacherRepo.delete(teacher);
	}

	@Override
	public void deleteTeacherById(Long id) {
		this.teacherRepo.deleteById(id);

	}

	@Override
	public List<Teacher> getAllTeachers() {
		return this.teacherRepo.findAll();
	}

	@Override
	public Page<Teacher> getTeachersByPage(int page, int size) {
		return this.teacherRepo.findAll(PageRequest.of(page, size));
	}

	@Override
	public Teacher getTeacher(Long teacherId) {
		// TODO Auto-generated method stub
		return this.teacherRepo.findById(teacherId).orElseThrow();
	}

	@Override
	public List<Teacher> findByUserName(String userName) {
		// TODO Auto-generated method stub
		return this.teacherRepo.findByUserName(userName);
	}

	@Override
	public List<Teacher> findByUserNameContains(String name) {
		// TODO Auto-generated method stub
		return this.findByUserNameContains(name);
	}

	@Override
	public List<Teacher> findByUserNameEmail(String userName, String email) {
		// TODO Auto-generated method stub
		return this.findByUserNameEmail(userName, email);
	}

	@Override
	public List<Teacher> sortTeacherByLastNameEmail() {
		// TODO Auto-generated method stub
		return this.sortTeacherByLastNameEmail();
	}



}

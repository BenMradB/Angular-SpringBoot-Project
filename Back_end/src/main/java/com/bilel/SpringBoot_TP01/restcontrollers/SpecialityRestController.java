package com.bilel.SpringBoot_TP01.restcontrollers;

import java.util.List;

import com.bilel.SpringBoot_TP01.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.bilel.SpringBoot_TP01.entities.Speciality;
import com.bilel.SpringBoot_TP01.services.SpecialityService;

@RequestMapping("/api/specialities")
@RestController
@CrossOrigin("*")
public class SpecialityRestController {
	@Autowired
	private SpecialityService specialityService;
	@Autowired
	private CourseService courseService;

	@GetMapping("/all")
	List<Speciality> getAllSpecialities() {
		return this.specialityService.getSpecialities();
	}

	@GetMapping
	Page<Speciality> getSpecialitiesPageByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
		return this.specialityService.getSpecialitiesByPage(page, size);
	}

	@GetMapping("/get/{specialityId}")
	Speciality getSpecialityById(@PathVariable("specialityId") Long specialityId) {
		return this.specialityService.getSpecialityById(specialityId);
	}

	@PostMapping("/create")
	Speciality createNewSpeciality(@RequestBody Speciality speciality) {
		return this.specialityService.createSpeciality(speciality);
	}

	@PatchMapping("/update/{specialityId}")
	Speciality updateSpeciality(@PathVariable("specialityId") Long specialityId, @RequestBody Speciality speciality) {
		speciality.setSpecialityId(specialityId);
		return this.specialityService.updateSpeciality(speciality);
	}

	@DeleteMapping("/delete/{specialityId}")
	void deleteSpeciality(@PathVariable("specialityId") Long specialityId) {
		this.courseService.deleteSpecialityCourses(specialityId);
		this.specialityService.deleteSpecialityById(specialityId);
	}
}

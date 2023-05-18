package com.bilel.SpringBoot_TP01.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bilel.SpringBoot_TP01.entities.Speciality;
import com.bilel.SpringBoot_TP01.services.SpecialityService;

import jakarta.validation.Valid;

@Controller
public class SpecialityController {
	@Autowired
	private SpecialityService specialityService;

	public void paginate(ModelMap modelMap, int page, int size) {
		Page<Speciality> specialities = this.specialityService.getSpecialitiesByPage(page, size);
		modelMap.addAttribute("specialities", specialities);
		modelMap.addAttribute("pages", new int[specialities.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	}

	@GetMapping("/newSpeciality")
	public String getCreateSpecialityPage(ModelMap modelMap) {

		modelMap.addAttribute("speciality", new Speciality());
		modelMap.addAttribute("specialityInfo", new Speciality());
		modelMap.addAttribute("mode", "new");

		return "Speciality/form_speciality";
	}

	@PostMapping("/manageSpeciality")
	public String manageSpeciality(ModelMap modelMap, @Valid @ModelAttribute("speciality") Speciality speciality,
			BindingResult bindingResult, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {

		if (speciality.getSpecialityId() == null) {
			modelMap.addAttribute("mode", "new");
			Page<Speciality> specialities = this.specialityService.getSpecialitiesByPage(page, size);
			page = specialities.getTotalPages() - 1;
		} else {
			modelMap.addAttribute("mode", "edit");
		}

		if (bindingResult.hasErrors()) {
			modelMap.addAttribute("specialityInfo", speciality);
			modelMap.addAttribute("currentPage", page);

			return "Speciality/form_speciality";
		}

		this.specialityService.createSpeciality(speciality);
		this.paginate(modelMap, page, size);

		return "Speciality/list_specialities";
	}

	@GetMapping("/specialities")
	public String getSpecialitiesPage(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {
		this.paginate(modelMap, page, size);
		return "Speciality/list_specialities";
	}

	@GetMapping("deleteSpeciality")
	public String deleteSpeciality(ModelMap modelMap, @RequestParam("id") Long specialityId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {
		this.specialityService.deleteSpecialityById(specialityId);
		this.paginate(modelMap, page, size);
		return "Speciality/list_specialities";
	}

	@GetMapping("/editSpeciality")
	public String getEditSpecialityPage(ModelMap modelMap, @RequestParam("id") Long specialityId,
			@RequestParam("page") int page) {
		Speciality speciality = this.specialityService.getSpecialityById(specialityId);
		modelMap.addAttribute("specialityInfo", speciality);
		modelMap.addAttribute("speciality", new Speciality());
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("mode", "edit");

		return "Speciality/form_speciality";
	}

}

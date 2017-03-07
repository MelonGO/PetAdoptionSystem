package com.pet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.model.Pet;
import com.pet.service.PetService;

@Controller
public class AdoptionController {

	@Autowired
	PetService petService;

	@RequestMapping(path = { "/adoption" })
	public String adoption(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {

		List<Pet> petList = petService.selectByPage((page - 1) * 5);
		model.addAttribute("petList", petList);

		int num = petService.allPetsNumber();
		List<Integer> pages = new ArrayList<Integer>();

		int pageAmount = num / 5;
		if (num % 5 != 0) {
			pageAmount++;
		}
		for (int i = 0; i < pageAmount; i++) {
			pages.add(i + 1);
		}
		model.addAttribute("pages", pages);

		return "adoption";
	}

}

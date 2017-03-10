package com.pet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.model.Pet;
import com.pet.model.User;
import com.pet.service.PetService;

@Controller
public class AdoptionController {

	@Autowired
	PetService petService;

	@RequestMapping(path = { "/adoption" })
	public String adoption(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		int num = petService.allPetsNumber();
		List<Integer> pages = new ArrayList<Integer>();

		int pageAmount = num / 5;
		if (num % 5 != 0) {
			pageAmount++;
		}

		int tmp = page;
		if (tmp % 5 == 0) {
			tmp = tmp - 4;
		} else {
			tmp = tmp - tmp % 5 + 1;
		}
		
		for (int i = 0; i < 5; i++) {
			if (tmp <= pageAmount) {
				pages.add(tmp);
				tmp++;
			}
		}
		
		model.addAttribute("pages", pages);

		model.addAttribute("previous", page - 1);
		model.addAttribute("next", page + 1);
		model.addAttribute("pageAmount", pageAmount);

		List<Pet> petList = petService.selectByPage((page - 1) * 5);
		model.addAttribute("petList", petList);

		return "adoption";
	}

	@RequestMapping(path = { "/wantAdopt" })
	public String wantAdopt(Model model, @RequestParam(value = "petId") int petId, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "redirect:login";

		} else {
			User user = (User) session.getAttribute("user");
			Pet pet = petService.selectById(petId);

			model.addAttribute("pet", pet);

			return "AdopterInfo";
		}

	}

}

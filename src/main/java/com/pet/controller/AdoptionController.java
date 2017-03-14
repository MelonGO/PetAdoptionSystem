package com.pet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.model.Pet;
import com.pet.model.User;
import com.pet.service.AdoptInfoService;
import com.pet.service.PetService;

@Controller
public class AdoptionController {

	@Autowired
	PetService petService;

	@Autowired
	AdoptInfoService adoptInfoService;

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
			model.addAttribute("user", (User) session.getAttribute("user"));

			return "AdopterInfo";
		}

	}

	@RequestMapping(path = { "/submitAdopt" })
	public String submitAdopt(Model model, @RequestParam("petId") int petId,
											@RequestParam("userId") int userId,
											@RequestParam("userName") String userName,
											@RequestParam("realName") String realName,
											@RequestParam("address") String address,
											@RequestParam("sex") String sex,
											HttpServletResponse response,
											HttpSession session) throws IOException {
		
		
		Map<String, Object> map = adoptInfoService.addAdoptInfo(petId, userId, userName, realName, address, sex);
		String msg = (String) map.get("msg");
		if (msg.equals("exist")) {
			model.addAttribute("error", "您已申请领养该宠物，请耐心等待申请结果!");
			return "error";
		}
		
		return "redirect:adoption";

	}

}

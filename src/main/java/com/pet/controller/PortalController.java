package com.pet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Pet;
import com.pet.service.PetService;

import tools.RequestUtil;

@Controller
public class PortalController {

	@Autowired
	PetService petService;
	
	@RequestMapping(path = { "/", "/portal" })
	public String portal(Model model){
		model.addAttribute("currentHtml", "portal");
		
		List<Pet> petList = petService.getLatesPets(0);
		
		model.addAttribute("petList", petList);
		
		return "portal";
	}
	
	@RequestMapping(value = { "/getMore" })
	@ResponseBody
	public List<Pet> getMore(HttpServletRequest request){
		Integer start = RequestUtil.getPositiveInteger(request, "start", null);

		List<Pet> petList = petService.getLatesPets((start - 1) * 3);
		return petList;
	}
	
	
}

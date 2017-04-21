package com.pet.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pet.model.Adoption;
import com.pet.model.Pet;
import com.pet.model.User;
import com.pet.service.AdoptionService;
import com.pet.service.PetService;
import com.pet.service.UserService;

@Controller
public class RecycleController {
	
	@Autowired
	PetService petService;

	@Autowired
	AdoptionService adoptInfoService;
	
	@Autowired
	UserService userService;
	
	
	//查看可送回的宠物
	@RequestMapping(path = { "/myRecycle" })
	public String recycle(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "login";
		}
		
		Map<Adoption, Pet> myAdoptionMap = new LinkedHashMap<>();
		
		List<Adoption> adoptInfoList = adoptInfoService.findUserAdoption(user.getId());
		for (int i = 0; i < adoptInfoList.size(); i++) {
			Adoption adoptInfo= adoptInfoList.get(i);
			if(adoptInfo.getState()==3){
				Pet pet = petService.selectById(adoptInfo.getPetId());
				myAdoptionMap.put(adoptInfo, pet);
			}
		}
		
		model.addAttribute("myAdoptionMap", myAdoptionMap);
		return "recycle";
	}
}

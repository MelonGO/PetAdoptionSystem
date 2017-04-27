package com.pet.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pet.service.UserService;


import com.pet.model.Pet;
import com.pet.model.User;
import com.pet.service.PetService;

@Controller
public class PetManageController {
	@Autowired
	UserService userService;
	
	@Autowired
	PetService petService;

	@RequestMapping(path = { "/petManage" })
	public String petManage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "login";
		}
		if(!user.getRole().equals("admin")){
			return "login";
		}
		List<Pet> petList=petService.getAll();
		model.addAttribute("petList", petList);
		return "petManage";
	}
	
	
	@RequestMapping(path = { "/updatePet.do" })
	public String petUpdate(String petID,String name,String type,String age,String sex,String price,String profile,Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		if(!user.getRole().equals("admin")){
			return "0";
		}
		int result=petService.update(petID, name,type,age,sex,price,profile);
		return String.valueOf(result+1);
	}
}

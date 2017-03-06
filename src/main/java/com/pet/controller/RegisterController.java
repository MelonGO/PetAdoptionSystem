package com.pet.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = { "/register" })
	public String registerShow(){
		return "register";
	}
	
	@RequestMapping(value = { "/registerUser" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String register(Model model, 
			@RequestParam("username") String username,
			@RequestParam("password") String password, 
			@RequestParam("role") String role) {
		
		Map<String, Object> map = userService.register(username, password, role);
		String msg = (String)map.get("msg");
		if(map.get("user") == null){
			model.addAttribute("error", msg);
			return "error";
		}
		
		return "redirect:login";
		
	}
	
}

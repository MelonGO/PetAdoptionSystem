package com.pet.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.User;
import com.pet.service.UserService;

import tools.CreateMD5;

@Controller
public class RegisterController {

	@Autowired
	UserService userService;

	@RequestMapping(path = { "/register" })
	public String registerShow() {
		return "register";
	}

	@RequestMapping(value = { "/registerUser" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String register(Model model, @RequestParam("username") String username,
										@RequestParam("password") String password, 
										@RequestParam("role") String role) {
		
		Map<String, Object> map = userService.register(username, CreateMD5.getMd5(password), role);
		String msg = (String) map.get("msg");
		if (map.get("user") == null) {
			model.addAttribute("error", msg);
			return "error";
		}
		
		return "redirect:login";

	}
	
	@RequestMapping(value = { "/checkName" })
	@ResponseBody
	public String checkName(Model model, @RequestParam("username") String username){
		if(username.equals("")){
			return "用户名不能为空!";
		}
		User user = userService.checkName(username);
		
		if(user == null){
			return "ok";
		}else{
			return "该用户名已被占用!";
		}
		
	}
}
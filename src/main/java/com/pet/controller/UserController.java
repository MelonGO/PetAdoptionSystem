package com.pet.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.model.User;
import com.pet.service.UserService;

import tools.CreateMD5;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = { "/userInfo" })
	public String personalInfo(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		model.addAttribute("user", user);
		
		return "userInfo";
	}
	
	@RequestMapping(path = { "/updateUserInfo" })
	public String updateUserInfo(Model model, 
								@RequestParam("userId") int userId,
								@RequestParam("uame") String uame,
								@RequestParam("oldPassword") String oldPassword,
								@RequestParam("newPassword") String newPassword,
								@RequestParam("newPasswordAgain") String newPasswordAgain,
								@RequestParam("sex") String sex, HttpSession session) {
		User user = userService.getUser(userId);
		if (!user.getPassword().equals(CreateMD5.getMd5(oldPassword))) {
			return "redirect:userInfo?msg=passwordWrong";
			
		} else {
			user.setPassword(CreateMD5.getMd5(newPassword));
			user.setSex(sex);
			userService.updateUserInfo(user);
			
			model.addAttribute("user", user);
			session.setAttribute("user", user);
			
			return "redirect:userInfo?msg=updateSuccess";
			
		}
		
	}

}

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
	
	@RequestMapping(path = { "/updateUser" })
	public String updateUserInfo(@RequestParam("userId") int userId,
								@RequestParam("username") String username,
								@RequestParam("oldPassword") String oldPassword,
								@RequestParam("newPassword") String newPassword,
								@RequestParam("newPasswordAgain") String newPasswordAgain,
								@RequestParam("sex") String sex) {
		User user = userService.getUser(userId);
		if (user.getPassword() != CreateMD5.getMd5(oldPassword)) {
			return "userInfo?msg=passwordWrong";
			
		} else {
			user.setName(username);
			user.setSex(sex);
			user.setPassword(CreateMD5.getMd5(newPassword));
			userService.updateUserInfo(user);
			
			return "userInfo?msg=updateSuccess";
			
		}
		
	}

}

package com.pet.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.service.UserService;

import tools.CreateMD5;

import com.pet.model.User;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@RequestMapping(path = { "/login" })
	public String login() {
		return "login";
	}

	@RequestMapping(path = { "/loginUser" })
	public String login(Model model, @RequestParam("username") String username,
									@RequestParam("password") String password, HttpSession session) {
		Map<String, Object> map = userService.login(username, CreateMD5.getMd5(password));
		String msg = (String) map.get("msg");
		if (map.get("user") == null) {

			model.addAttribute("error", msg);
			return "Error";
		}

		model.addAttribute("user", (User) map.get("user"));
		session.setAttribute("user", (User) map.get("user"));

		return "redirect:portal";
	}

	@RequestMapping(value = { "/logout" })
	public String logout(HttpSession session) {
		session.invalidate();
		return "";
	}

}

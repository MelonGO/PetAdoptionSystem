package com.pet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalController {
	
	@RequestMapping(path = { "/", "/portal" })
	public String portal(){
		return "portal";
	}

}

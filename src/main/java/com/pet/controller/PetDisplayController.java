package com.pet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Comment;
import com.pet.service.CommentService;

import tools.RequestUtil;

@Controller
public class PetDisplayController {
	@Autowired
	CommentService commentService;
	
	@RequestMapping(path = {"/item"})
	public String comemnt(Model model){
		model.addAttribute("currentHtml", "commentBlock");
		
		List<Comment> commentList = commentService.getAll();
		
		model.addAttribute("commentList", commentList);
		
		return "commentBlock";
	}
}

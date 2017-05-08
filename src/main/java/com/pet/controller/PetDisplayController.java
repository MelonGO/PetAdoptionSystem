package com.pet.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Comment;
import com.pet.service.CommentService;

import tools.RequestUtil;

@Controller
public class PetDisplayController {
	@Autowired
	CommentService commentService;
	
	@RequestMapping(path = {"/item"})
	public String loadCom(Model model){
		model.addAttribute("currentHtml", "commentBlock");
		
		List<Comment> commentList = commentService.getAll();
		
		model.addAttribute("commentList", commentList);
		
		return "commentBlock";
	}
	
	@RequestMapping(path = {"/pushcomment"})
	public String pushComment(Model model,HttpServletRequest request){
		String content = request.getParameter("content");
		System.out.println(content);
		Map<String, Object> map = commentService.addComment(1, "Kitty", content, 0, 0, 0);
		String msg = (String) map.get("msg");
		if(!msg.equals("success")){
			model.addAttribute("error", "评论失败!");
			return "error";
		}
		
		return "redirect:item?msg=success";
	}
}

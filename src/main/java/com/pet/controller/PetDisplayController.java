package com.pet.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Comment;
import com.pet.model.Pet;
import com.pet.model.ReceivingInfo;
import com.pet.model.User;
import com.pet.service.CommentService;
import com.pet.service.PetService;

import tools.RequestUtil;

@Controller
public class PetDisplayController {
	@Autowired
	CommentService commentService;
	
	@Autowired
	PetService petService;
	
	@RequestMapping(path = {"/item"})
	public String loadCom(Model model, /*@RequestParam(value = "petId") int petId,*/ HttpSession session){
		
		model.addAttribute("currentHtml", "commentBlock");
		Pet pet = petService.selectById(1);
		List<Comment> commentList = commentService.selectById(1);
		model.addAttribute("pet", pet);
		model.addAttribute("commentList", commentList);
		
		return "commentBlock";
	}
	
	@RequestMapping(path = {"/pushcomment"})
	public String pushComment(Model model, /*@RequestParam(value = "petId") int petId,*/HttpServletRequest request, HttpSession session){
		/*if (session.getAttribute("user") == null) {
			return "redirect:petList?msg=notLogin";
		} else {
			User user = (User) session.getAttribute("user");
			String content = request.getParameter("content");
			int fatherid = Integer.parseInt(request.getParameter("fatherid"));
			Map<String, Object> map = commentService.addComment(petId, user.getName(), content, fatherid, -1, 0);
			String msg = (String) map.get("msg");
			if(!msg.equals("success")){
				model.addAttribute("error", "评论失败!");
				return "error";
			}
			
			return "redirect:item?msg=success";
		}*/
		String content = request.getParameter("content");
		int fatherid = Integer.parseInt(request.getParameter("fatherid"));
		Map<String, Object> map = commentService.addComment(1, "Cruze", content, fatherid, -1, 0);
		String msg = (String) map.get("msg");
		if(!msg.equals("success")){
			model.addAttribute("error", "评论失败!");
			return "error";
		}
		return "redirect:item?msg=success";
	}
}

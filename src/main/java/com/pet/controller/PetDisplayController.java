package com.pet.controller;

import java.util.ArrayList;
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
	public String loadCom(Model model, /*@RequestParam(value = "petId") int petId,*/ 
			@RequestParam(value = "page", defaultValue = "1") int page,
			HttpSession session){
		int rootCommentCount = commentService.getRootCommentsCountByPetId(1);
		System.out.println(rootCommentCount);
		List<Integer> pages = new ArrayList<Integer>();

		int pageAmount = rootCommentCount / 5;
		if (rootCommentCount % 5 != 0) {
			pageAmount++;
		}

		int tmp = page;
		if (tmp % 5 == 0) {
			tmp = tmp - 4;
		} else {
			tmp = tmp - tmp % 5 + 1;
		}

		for (int i = 0; i < 5; i++) {
			if (tmp <= pageAmount) {
				pages.add(tmp);
				tmp++;
			}
		}

		model.addAttribute("pages", pages);
		model.addAttribute("current", page);
		model.addAttribute("pageAmount", pageAmount);
		
		model.addAttribute("currentHtml", "commentBlock");
		Pet pet = petService.selectById(1);
		List<Integer> rootCommentIdList = commentService.selectRootIdByPage(1, page);
		
		List<Comment> commentList = commentService.selectByPage(1,page);
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

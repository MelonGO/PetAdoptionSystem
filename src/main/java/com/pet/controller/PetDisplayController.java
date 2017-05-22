package com.pet.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.pet.model.Support;
import com.pet.model.User;
import com.pet.service.CommentService;
import com.pet.service.PetService;

import tools.HavePages;
import tools.RequestUtil;

@Controller
public class PetDisplayController {
	@Autowired
	CommentService commentService;
	
	@Autowired
	PetService petService;
	
	@RequestMapping(path = {"/item"})
	public String loadCom(Model model, @RequestParam(value = "petId") int petId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			HttpSession session){
		/*
		 * 
		 * 测试用 visualUser 2017/5/22
		 * 
		 * */
		User user = new User();
		user.setId(1);
		user.setName("visualPaul");
		if(session.getAttribute("user")==null){
			session.setAttribute("user", user);
		}
		else{
			user = (User)session.getAttribute("user");
		}
		/*
		 * 
		 * */
		
		Pet pet = petService.selectById(petId);//petId
		int rootCommentCount = commentService.getRootCommentsCountByPetId(petId);//petId
		
		
		
		
		model.addAttribute("petId", petId);
		model.addAttribute("pet", pet);
		model.addAttribute("currentHtml", "commentBlock");
		
		if(rootCommentCount==0){
			model.addAttribute("commentCount",0);
			model.addAttribute("commentList", new ArrayList<List<Comment>>());
			model.addAttribute("supportList", new ArrayList<List<Support>>());
			model.addAttribute("pages", new ArrayList<List<Integer>>());
		}
		else{
			List<List<Integer>> pages = new ArrayList<List<Integer>>();
			pages.add(HavePages.getMyPages(page, rootCommentCount));
			int commentCount = commentService.getCommentsCountByPetId(petId);//petId
			List<Comment> rootCommentList = commentService.selectRootCommentByPage(petId, (page - 1) * 5);//petId
			List<Integer> commentIdListSup = new ArrayList<Integer>();
			List<List<Comment>> commentList = new ArrayList<List<Comment>>();
			List<List<Support>> supportList = new ArrayList<List<Support>>();
			
			for(Comment c: rootCommentList){
				List<Comment> commentListItem = new ArrayList<Comment>();/*用以存放一条根评论及其一页的评论*/
				commentListItem.add(c);
				commentIdListSup.add(c.getId());
				pages.add(HavePages.getMyPages(1, commentService.getLeafCommentsCountByFatherId(c.getId())));
				for(Comment cs: commentService.selectLeafCommentByPage(c.getId(), 0)){
					commentListItem.add(cs);
					commentIdListSup.add(cs.getId());
				}
				commentList.add(commentListItem);
				if(session.getAttribute("user")==null){
					for(int i=0;i<5;i++){
						supportList.add(new ArrayList<Support>());
					}
				}
				else{
					supportList.add(commentService.selectCommentSupportByUserId(user.getId(), commentIdListSup));//userId
				}
			}
			model.addAttribute("commentCount",commentCount);
			model.addAttribute("commentList", commentList);
			model.addAttribute("supportList",  supportList);
			model.addAttribute("pages", pages);
		}
		return "commentBlock";
	}
	
	@RequestMapping(path = {"/pushcomment"})
	public String pushComment(Model model, @RequestParam(value = "petId") int petId, HttpServletRequest request, HttpSession session){
		if (session.getAttribute("user") == null) {
			return "redirect:item?msg=notLogin";
		} else {
			User user = (User)session.getAttribute("user");
			String content = request.getParameter("content");
			int fatherid = Integer.parseInt(request.getParameter("fatherid"));
			Map<String, Object> map = commentService.addComment(petId, user.getName(), content, fatherid);
			String msg = (String) map.get("msg");
			if(!msg.equals("success")){
				model.addAttribute("error", "评论失败!");
				return "error";
			}
			return "redirect:item?" + "petId=" + petId + "&msg=success";
		}
	}
	
	@RequestMapping(path = {"/like"})
	public String pushDislike(HttpSession session, @RequestParam(value = "like") int like, @RequestParam(value = "commentId") int commentId){
		if(session.getAttribute("user")==null){
			return "redirect:item?msg=notLogin";
		}
		else{
			User user = (User)session.getAttribute("user");
			commentService.updateCommentSupport(like, commentId);
			commentService.updateCommentSupportTable(user.getId(),commentId,like);
			return "";
		}
	}
	
	@RequestMapping(path = {"moreLeaves"})
	@ResponseBody
	public List<Comment> getMoreLeafComments(HttpSession session, @RequestParam(value = "commentId") int commentId, @RequestParam(value = "page") int page){
		List<Comment> leafCommentList = commentService.selectLeafCommentByPage(commentId, (page-1)*5);
		for(Comment c : leafCommentList){
			if(session.getAttribute("user")==null){
				c.setReplyCommentID(0);
			}
			else{
				User user = (User)session.getAttribute("user");
				if(commentService.isSupportted(user.getId(), c.getId()) == 1){
					c.setReplyCommentID(1);
				}
				else{
					c.setReplyCommentID(0);
				}
			}
		}
		return leafCommentList;
	}
}

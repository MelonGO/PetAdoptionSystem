package com.pet.controller;


import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Message;
import com.pet.model.User;
import com.pet.service.MessageService;
import com.pet.service.UserService;

import tools.MsgSend;



@Controller
public class MsgController {
	
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	
	@RequestMapping(path = { "/message" })
	public String message(Model model,HttpSession session) {
		User user=(User) session.getAttribute("user");
		if(!(user==null)){
			List<Message> messageList = messageService.getPrivateByTargetUserName(user.getName());
			model.addAttribute("myMsgList", messageList);
		}		
		return "message";
	}
	
	
	@RequestMapping("/msg_private.do")
	public @ResponseBody String msg_private(Model model,HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		List<Message> messageList = messageService.getPrivateByTargetUserName(user.getName());
		model.addAttribute("myMsgList", messageList);
		return "1";
		
	}
	
	@RequestMapping("/msg_comment.do")
	public @ResponseBody String msg_comment(Model model,HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		List<Message> messageList = messageService.getCommentByTargetUserName(user.getName());
		model.addAttribute("myMsgList", messageList);
		return "1";
	}	
	
	@RequestMapping("/msg_notify.do")
	public @ResponseBody String msg_notify(Model model,HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		List<Message> messageList = messageService.getNotifyByTargetUserName(user.getName());
		model.addAttribute("myMsgList", messageList);
		return "1";
	}	
	
	@RequestMapping("/hasMsg.do")
	public @ResponseBody int hasMsg(HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return 0;
		}
		String username=user.getName();
		MsgSend ms=new MsgSend();
		return ms.hasMsg(username);
		
	}
	@RequestMapping("/nav_msg.do")
	public @ResponseBody int dump(HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return 0;
		}
		String targetUsername=user.getName();
		MsgSend ms=new MsgSend();		
		Set<String>  s=ms.getMsgByTargeUsername(targetUsername);
		for (String str : s) {  
		      String[] splits=str.split("_");
		      String sendTime=splits[0];
		      String content=splits[1];
		      String username=splits[2];
		      String readed=splits[3];
		      String type=splits[4];
		      messageService.addMessage(username, targetUsername, content, readed, sendTime, type);
		}
		ms.delMsgByTargeUsername(targetUsername);
		return 1;
		
	}
	
	
	@RequestMapping("/getUsn.do")
	public  @ResponseBody String getUsn(HttpSession session)
	{
		
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "";
		}
		
		return user.getName();
	}
	
	@RequestMapping("/sendmsg.do")
	public  @ResponseBody String send(String userName,String msgConent, HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		if(user.getRole().equals("admin")||user.getRole().equals("user"))
		{
			
			MsgSend ms=new MsgSend();			
			ms.sendMsg(user.getName(), msgConent,userName);
			
			return "1";
		}
		return "0";
		
	}

}

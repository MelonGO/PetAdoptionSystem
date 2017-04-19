package com.pet.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.User;
import com.pet.service.UserService;

import tools.MsgSend;



@Controller
public class MsgController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/hasMsg.do")
	public @ResponseBody Boolean hasMsg(HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return false;
		}
		String username=user.getName();
		MsgSend ms=new MsgSend();
		return ms.hasMsg(username);
		
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
		
		if(user.getRole().equals("admin"))
		{
			
			MsgSend ms=new MsgSend();
			
			ms.sendMsg(userName, msgConent);
			
			return "1";
		}
		else
		{
			return "0";
		}
	}

}

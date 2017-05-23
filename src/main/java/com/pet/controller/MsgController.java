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
			List<Message> messageList = messageService.getPrivateByTargetUserName(user.getName());
			List<Message> messageList2 = messageService.getCommentByTargetUserName(user.getName());
			List<Message> messageList3 = messageService.getNotifyByTargetUserName(user.getName());
			model.addAttribute("myMsgList", messageList);
			for(int i=0;i<messageList.size();i++){
				messageService.updateMessage(messageList.get(i));

			}
			model.addAttribute("myMsgList2", messageList2);
			model.addAttribute("myMsgList3", messageList3);		
		}else{
			return "login";
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
		for(int i=0;i<messageList.size();i++){
			messageService.updateMessage(messageList.get(i));
		}
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
		for(int i=0;i<messageList.size();i++){
			messageService.updateMessage(messageList.get(i));
		}
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
		for(int i=0;i<messageList.size();i++){
			messageService.updateMessage(messageList.get(i));
		}
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
	
	@RequestMapping("/hasPrivateMsg.do")
	public @ResponseBody int hasPrivateMsg(HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return -1;
		}
		List<Message> messageList = messageService.getPrivateByTargetUserName(user.getName());
		int result=0;
		for(int i=0;i<messageList.size();i++){
			if(messageList.get(i).getReaded().equals("0")){
				result++;
			}
		}
		return result;
		
	}
	
	@RequestMapping("/hasCommentMsg.do")
	public @ResponseBody int hasCommentMsg(HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return -1;
		}
		List<Message> messageList = messageService.getCommentByTargetUserName(user.getName());
		int result=0;
		for(int i=0;i<messageList.size();i++){
			if(messageList.get(i).getReaded().equals("0")){
				result++;
			}
		}
		return result;
		
	}
	
	@RequestMapping("/hasNotifyMsg.do")
	public @ResponseBody int hasNotifyMsg(HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return -1;
		}
		List<Message> messageList = messageService.getNotifyByTargetUserName(user.getName());
		int result=0;
		for(int i=0;i<messageList.size();i++){
			if(messageList.get(i).getReaded().equals("0")){
				result++;
			}
		}
		return result;
		
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
	public  @ResponseBody String send(String userName,String msgContent, HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		if(user.getRole().equals("admin")||user.getRole().equals("user"))
		{
			messageService.sendMessage(user.getName(), msgContent, userName,"私信");			
			return "1";
		}
		return "0";
		
	}
	
	@RequestMapping("/sendNotifyMsg.do")
	public  @ResponseBody String sendNotify(String userName,String msgContent, HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		if(user.getRole().equals("admin")){	
			messageService.sendMessage(user.getName(), msgContent, userName,"系统通知");	
			return "1";
		}
		return "0";
		
	}
	@RequestMapping("/del_msg.do")
	public  @ResponseBody String delMsg(String mid, HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		int result=messageService.delMsg(mid)+1;

		return Integer.toString(result);
		
	}
	

}

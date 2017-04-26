package com.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.MessageDAO;
import com.pet.model.Message;

@Service
public class MessageService {
	
	@Autowired
	private MessageDAO messageDao;
	
	public Map<String, Object> addMessage(String username,String targetUsername,String content,String readed,String sendTime,String type) {
		Map<String, Object> msgMap = new HashMap<>();
		Message message=new Message();
		message.setUsername(username);
		message.setTargetUsername(targetUsername);
		message.setContent(content);
		message.setReaded(readed);
		message.setSendTime(sendTime);
		message.setType(type);
		
		messageDao.addMessage(message);
		msgMap.put("msg", "success");
		return msgMap;
			
	}
	
	public void updateMessage(Message message){	
		messageDao.updateMessage(message);
	}
	
	public List<Message> getPrivateByTargetUserName(String targetUsername){
		return messageDao.selectPrivateByTargetUserName(targetUsername);
	}

	public List<Message> getNotifyByTargetUserName(String targetUsername) {
		// TODO Auto-generated method stub
		return messageDao.selectNotifyByTargetUserName(targetUsername);
	}
	
	public List<Message> getCommentByTargetUserName(String targetUsername) {
		// TODO Auto-generated method stub
		return messageDao.selectCommentByTargetUserName(targetUsername);
	}
}

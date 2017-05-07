package com.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.CommentDAO;
import com.pet.model.Comment;

@Service
public class CommentService {
	
	@Autowired
	private CommentDAO commentDao;
	
	public List<Comment> selectById(int petId){
    	return commentDao.selectById(petId);
    }
	
	public List<Comment> getAll(){
		return commentDao.getAll();
	}
	
	public Map<String, Object> addComment(int petID, String username, String content, 
			int fatherCommentID, int replyCommentID, int support){
		Map<String, Object> msgMap = new HashMap<>();
		Comment myCom = new Comment();
		myCom.setPetID(petID);
		myCom.setUsername(username);
		myCom.setContent(content);
		myCom.setFatherCommentID(fatherCommentID);
		myCom.setReplyCommentID(replyCommentID);
		myCom.setSupport(support);
		commentDao.addComment(myCom);
		msgMap.put("msg", "success");
		return msgMap;
	}
}

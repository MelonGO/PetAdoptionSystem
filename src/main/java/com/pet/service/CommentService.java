package com.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.CommentDAO;
import com.pet.model.Comment;
import com.pet.model.Support;

@Service
public class CommentService {
	
	@Autowired
	private CommentDAO commentDao;
	
	public List<Comment> selectById(int petId){
    	return commentDao.selectById(petId);
    }
	
	public int getCommentsCountByPetId(int petID){
		return commentDao.getCommentsCountByPetId(petID);
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
	
	public int getRootCommentsCountByPetId(int petID){
		return commentDao.getRootCommentsCountByPetId(petID);
	}
	
	public List<Comment> selectByPage(int petID, int page){
    	return commentDao.selectByPage(petID, page);
    }
	
	public List<Comment> selectRootCommentByPage(int petID, int page){
		List<Comment> result = commentDao.selectRootCommentByPage(petID, page);
		for(Comment c: result){
			c.setCreateTime(c.getCreateTime().substring(0, 19));
		}
		return result;
	}
	
	public List<Comment> selectLeafCommentByFatherCommentId(List<Integer> fatherIdMap){
		List<Comment> result = commentDao.selectLeafCommentByFatherCommentId(fatherIdMap);
		for(Comment c: result){
			c.setCreateTime(c.getCreateTime().substring(0, 19));
		}
		return result;
	}
	
	public List<Support> selectCommentSupportByUserId(int userId, List<Integer> commentIdList){
		return commentDao.selectCommentSupportByUserId(userId,commentIdList);
	}
	
	public int updateCommentSupport(Support s){
		return commentDao.updateCommentSupport(s);
	}
}

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
			int fatherCommentID){
		Map<String, Object> msgMap = new HashMap<>();
		Comment myCom = new Comment();
		myCom.setPetID(petID);
		myCom.setUsername(username);
		myCom.setContent(content);
		myCom.setFatherCommentID(fatherCommentID);
		myCom.setReplyCommentID(-1);
		myCom.setSupport(0);
		commentDao.addComment(myCom);
		msgMap.put("msg", "success");
		return msgMap;
	}
	
	public int getRootCommentsCountByPetId(int petID){
		return commentDao.getRootCommentsCountByPetId(petID);
	}
	
	public int getLeafCommentsCountByFatherId(int fatherId){
		return commentDao.getLeafCommentsCountByFatherId(fatherId);
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
	
	public List<Comment> selectLeafCommentByFatherCommentId(List<Integer> fatherIdList){
		List<Comment> result = commentDao.selectLeafCommentByFatherCommentId(fatherIdList);
		for(Comment c: result){
			c.setCreateTime(c.getCreateTime().substring(0, 19));
		}
		return result;
	}
	
	public List<Support> selectCommentSupportByUserId(int userId, List<Integer> commentIdList){
		return commentDao.selectCommentSupportByUserId(userId,commentIdList);
	}
	
	public int updateCommentSupportTable(int userId, int commentId, int status){
		return commentDao.updateCommentSupportTable(userId,commentId,status,commentDao.doExistSupport(userId, commentId));
	}
	
	public int updateCommentSupport(int fix,int commentId){
		return commentDao.updateCommentSupport(fix, commentId);
	}
	
	public List<Comment> selectLeafCommentByPage(int fatherId, int page){
		return commentDao.selectLeafCommentByPage(fatherId, page);
	}
	
	public int doExistSupport(int userId, int commentId){
		return commentDao.doExistSupport(userId, commentId);
	}
	
	public int isSupportted(int userId, int commentId){
		return commentDao.isSupportted(userId, commentId);
	}
}

package com.pet.service;

import java.util.List;

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
}

package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.pet.dao.constants.CommentDaoConstants;
import com.pet.model.Comment;

@Mapper
public interface CommentDAO {
	@Select({"select ", CommentDaoConstants.SELECT_FIELDS, " from ", CommentDaoConstants.TABLE_NAME, " where petID=#{petId}"})
	List<Comment> selectById(int petId);
	
	@Select({"select ", CommentDaoConstants.SELECT_FIELDS, " from ", CommentDaoConstants.TABLE_NAME})
    List<Comment> getAll();
}

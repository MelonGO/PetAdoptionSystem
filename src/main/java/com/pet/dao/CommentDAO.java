package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.CommentDaoConstants;
import com.pet.model.Comment;

@Mapper
public interface CommentDAO {
	@Select({"select ", CommentDaoConstants.SELECT_FIELDS, " from ", CommentDaoConstants.TABLE_NAME, " where petID=#{petId}"})
	List<Comment> selectById(int petId);
	
	@Select({"select ", CommentDaoConstants.SELECT_FIELDS, " from ", CommentDaoConstants.TABLE_NAME})
    List<Comment> getAll();
	
	@Insert({ "insert into ", CommentDaoConstants.TABLE_NAME, "(", CommentDaoConstants.INSERT_FIELDS,
	") values (#{petID},#{username},#{content},#{fatherCommentID},#{replyCommentID},#{support})" })
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", before = false, resultType = Integer.class, 
	statementType = StatementType.PREPARED)
    int addComment(Comment com);
}
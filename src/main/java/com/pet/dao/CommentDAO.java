package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.CommentDaoConstants;
import com.pet.model.Comment;
import com.pet.model.Support;

@Mapper
public interface CommentDAO {
	@Select({"select ", CommentDaoConstants.SELECT_FIELDS, " from ", CommentDaoConstants.TABLE_NAME, " where petID=#{petId}"})
	List<Comment> selectById(int petId);
	
	@Select({"select count(*) from", CommentDaoConstants.TABLE_NAME, " where petID=#{petID} "})
    int getCommentsCountByPetId(int petID);
	
	@Select({"select ", CommentDaoConstants.SELECT_FIELDS, " from ", CommentDaoConstants.TABLE_NAME})
    List<Comment> getAll();
	
	@Insert({ "insert into ", CommentDaoConstants.TABLE_NAME, "(", CommentDaoConstants.INSERT_FIELDS,
	") values (#{petID},#{username},#{content},#{fatherCommentID},#{replyCommentID},#{support})" })
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", before = false, resultType = Integer.class, 
	statementType = StatementType.PREPARED)
    int addComment(Comment com);
	
	@Select({"select count(*) from", CommentDaoConstants.TABLE_NAME, " where petID=#{petID} and fatherCommentID=0"})
    int getRootCommentsCountByPetId(int petID);
	
	@Select({"select count(*) from", CommentDaoConstants.TABLE_NAME, "where fatherCommentId=#{fatherId}"})
	int getLeafCommentsCountByFatherId(int fatherId);
	
	@Select({"select ", CommentDaoConstants.SELECT_FIELDS, " from ", CommentDaoConstants.TABLE_NAME, "where petID=#{petID}", "limit #{page}, 5"})
    List<Comment> selectByPage(@Param("petID") int petID, @Param("page") int page);
	
	@Select({"select ", CommentDaoConstants.SELECT_FIELDS, " from ", CommentDaoConstants.TABLE_NAME, "where petID=#{petID} and fatherCommentID=0", "limit #{page}, 5"})
    List<Comment> selectRootCommentByPage(@Param("petID") int petID, @Param("page") int page);
	
	/*
	 * 
	 * 以下sql如无注释，参考mybatis-mapper.xml
	 * 
	 * */
	
    List<Comment> selectLeafCommentByFatherCommentId(@Param("fatherIdList") List<Integer> fatherIdList);
	
    List<Support> selectCommentSupportByUserId(@Param("userId") int userId, @Param("commentIdList") List<Integer> commentIdList);
	
	int updateCommentSupportTable(@Param("userId") int userId, @Param("commentId") int commentId, @Param("status") int status, @Param("exist") int exist);
	
	@Update({"update ", CommentDaoConstants.TABLE_NAME, " set support=support+#{fix} where id=#{commentId}"})
	int updateCommentSupport(@Param("fix") int fix, @Param("commentId") int commentId);
	
	@Select({"select count(*) from", CommentDaoConstants.TABLE_NAME_SUPPORT, "where userId=#{userId} and commentId=#{commentId}"})
	int doExistSupport(@Param("userId") int userId, @Param("commentId") int commentId);
	
	@Select({"select count(*) from", CommentDaoConstants.TABLE_NAME_SUPPORT, "where userId=#{userId} and commentId=#{commentId} and status=1"})
	int isSupportted(@Param("userId") int userId, @Param("commentId") int commentId);
	
	@Select({"select ", CommentDaoConstants.SELECT_FIELDS, " from ", CommentDaoConstants.TABLE_NAME, "where fatherCommentID=#{fatherId}",  "limit #{page}, 5"})
	List<Comment> selectLeafCommentByPage(@Param("fatherId") int fatherId, @Param("page") int page);
	
}

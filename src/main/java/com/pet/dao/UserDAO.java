package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.UserDaoConstants;
import com.pet.model.User;

@Mapper
public interface UserDAO {
   

    @Insert({"insert into ", UserDaoConstants.TABLE_NAME, "(", UserDaoConstants.INSERT_FIELDS,
            ") values (#{name},#{password},#{sex},#{role})"})
    @SelectKey(statement="select last_insert_id() as id", keyProperty="id", before=false, resultType=Integer.class,
            statementType = StatementType.PREPARED)
    int addUser(User user);

    @Select({"select ", UserDaoConstants.SELECT_FIELDS, " from ", UserDaoConstants.TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", UserDaoConstants.SELECT_FIELDS, " from ", UserDaoConstants.TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Update({"update ", UserDaoConstants.TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", UserDaoConstants.TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
    
    @Select({"select ", UserDaoConstants.SELECT_FIELDS, " from ", UserDaoConstants.TABLE_NAME})
    List<User> getAll();
}

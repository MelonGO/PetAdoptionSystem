package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.MyOrderDaoConstants;
import com.pet.model.MyOrder;

@Mapper
public interface MyOrderDAO {
	
	@Insert({ "insert into ", MyOrderDaoConstants.TABLE_NAME, "(", MyOrderDaoConstants.INSERT_FIELDS, 
		") values (#{userId},#{pets},#{props},#{total},#{state})" })
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", before = false, resultType = Integer.class, 
		statementType = StatementType.PREPARED)
	int addOrder(MyOrder myorder);
	
	@Select({ "select ", MyOrderDaoConstants.SELECT_FIELDS, " from ", MyOrderDaoConstants.TABLE_NAME, " where user_id=#{userId}" })
	List<MyOrder> selectByUser(int userId);
	
	@Delete({"delete from ", MyOrderDaoConstants.TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
	
}

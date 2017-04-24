package com.pet.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.RecycleDaoConstants;
import com.pet.model.Recycle;

@Mapper
public interface  RecycleDAO {	
	@Insert({ "insert into ", RecycleDaoConstants.TABLE_NAME, "(", RecycleDaoConstants.INSERT_FIELDS,
	") values (#{petId},#{userId},#{state},#{sponsorship},#{money},#{reason},#{address},#{phone})" })
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", before = false, resultType = Integer.class, 
	statementType = StatementType.PREPARED)
	int addRecycle(Recycle recycle);
}

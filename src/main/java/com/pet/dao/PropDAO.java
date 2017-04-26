package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.pet.dao.constants.PropDaoConstants;
import com.pet.model.Prop;

@Mapper
public interface PropDAO {

	@Select({ "select ", PropDaoConstants.SELECT_FIELDS, " from ", PropDaoConstants.TABLE_NAME })
	List<Prop> getAll();
	
	
	@Select({ "select ", PropDaoConstants.SELECT_FIELDS, " from ", PropDaoConstants.TABLE_NAME, " where id=#{propId} " })
	Prop getByPropId(int propId);
	
}

package com.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.PropDAO;
import com.pet.model.Prop;

@Service
public class PropService {

	@Autowired
	private PropDAO propDao;
	
	public List<Prop> getAllProps(){
		return propDao.getAll();
	}
	
}

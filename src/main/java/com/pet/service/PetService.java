package com.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.PetDAO;
import com.pet.model.Pet;

@Service
public class PetService {
	
	@Autowired
	private PetDAO petDao;
	
	public Pet selectById(int petId){
    	return petDao.selectById(petId);
    }
	
	public List<Pet> selectByPage(int page){
    	return petDao.selectByPage(page);
    }
	
	public int allPetsNumber(){
		return petDao.allPetsNumber();
	}

}

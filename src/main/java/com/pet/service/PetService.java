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
	
	public List<Pet> getAll(){
		return petDao.getAll();
	}
	
	public List<Pet> getLatesPets(int start){
		return petDao.getLatesPets(start);
	}

	public int update(String petID, String name, String type, String age, String sex, String price, String profile) {
		// TODO Auto-generated method stub
		Pet pet=new Pet();
		pet.setId(Integer.parseInt(petID));
		pet.setAge(Integer.parseInt(age));
		pet.setName(name);
		pet.setType(type);
		pet.setPrice(Double.parseDouble(price));
		pet.setSex(sex);
		pet.setProfile(profile);
		return petDao.update(pet);
	}

	public int add(String name, String type, String age, String sex, String price, String profile) {
		// TODO Auto-generated method stub
		Pet pet=new Pet();
		pet.setAge(Integer.parseInt(age));
		pet.setName(name);
		pet.setType(type);
		pet.setPrice(Double.parseDouble(price));
		pet.setSex(sex);
		pet.setProfile(profile);
		return petDao.add(pet);
	}

	public int del(String id) {
		// TODO Auto-generated method stub
		return petDao.delPet(Integer.parseInt(id));
	}

	public int getID() {
		// TODO Auto-generated method stub
		return petDao.getID();
	}

}

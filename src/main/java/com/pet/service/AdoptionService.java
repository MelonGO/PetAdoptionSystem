package com.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.AdoptionDAO;
import com.pet.dao.ReceivingInfoDAO;
import com.pet.model.Adoption;
import com.pet.model.ReceivingInfo;

@Service
public class AdoptionService {

	@Autowired
	private AdoptionDAO adoptionDao;
	
	@Autowired
	private ReceivingInfoDAO recInfoDao;

	public Map<String, Object> addAdoption(int petId, int userId, String realName, 
											String address, String phone) {
		Map<String, Object> msgMap = new HashMap<>();
		
		ReceivingInfo recInfo = recInfoDao.selectByUserId(userId);
		if(recInfo != null){
			recInfo.setRealName(realName);
			recInfo.setAddress(address);
			recInfo.setPhone(phone);
			recInfoDao.updateReceivingInfo(recInfo);
		}else{
			ReceivingInfo newRecInfo = new ReceivingInfo();
			newRecInfo.setUserId(userId);
			newRecInfo.setRealName(realName);
			newRecInfo.setAddress(address);
			newRecInfo.setPhone(phone);

			recInfoDao.addReceivingInfo(newRecInfo);
		}

		Adoption newAdopt = new Adoption();
		newAdopt.setReceiving_info_id(recInfoDao.selectByUserId(userId).getId());
		newAdopt.setPetId(petId);
		newAdopt.setUserId(userId);
		newAdopt.setTransport_way("邮递");

		adoptionDao.addAdoption(newAdopt);
		msgMap.put("msg", "success");
		return msgMap;
	}

	public List<Adoption> getAll() {
		return adoptionDao.getAll();
	}
	
	public String findAlreadyExist(int petId, int userId) {
		Adoption adoptInfo = adoptionDao.selectByPetAndUser(petId, userId);
		if (adoptInfo != null) {
			return "exist";
		}
		return "none";
	}
	
	public List<Adoption> findUserAdoption(int userId) {
		return adoptionDao.selectByUser(userId);
	}
	
	public Adoption findAdoptionById(int id){
		return adoptionDao.selectById(id);
	}
	
	public Map<String, Object> updateAdoption(Adoption adoptInfo){
		Map<String, Object> msgMap = new HashMap<>();
		
		adoptionDao.updateAdoption(adoptInfo);
		msgMap.put("msg", "审核成功!");
		return msgMap;
	}
	
	public Adoption findByPetId(int petId){
		return adoptionDao.selectByPetId(petId);
	}

}

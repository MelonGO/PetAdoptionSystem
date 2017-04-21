package com.pet.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.AdoptionDAO;
import com.pet.dao.RecycleDAO;
import com.pet.model.Adoption;
import com.pet.model.ReceivingInfo;
import com.pet.model.Recycle;

@Service
public class RecyleService {
	
	@Autowired
	private AdoptionDAO adoptionDao;
	
	@Autowired
	private RecycleDAO recycleDao;
	
	public Map<String, Object> addAdoption(int petId, int userId, int isSponsorship, double money, String reason, String address,String phone) {
		Map<String, Object> msgMap = new HashMap<>();
		Recycle recycle=new Recycle();
		
		recycle.setPetId(petId);
		recycle.setUserId(userId);
		recycle.setMoney(money);
		recycle.setSponsorship(isSponsorship);
		recycle.setReason(reason);
		recycle.setAddress(address);
		recycle.setPhone(phone);	
		
		recycleDao.addRecycle(recycle);
		msgMap.put("msg", "success");
		return msgMap;
			
	}

}
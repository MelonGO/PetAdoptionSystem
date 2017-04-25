package com.pet.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pet.dao.RecycleDAO;
import com.pet.model.Recycle;

@Service
public class RecycleService {
	
	@Autowired
	private RecycleDAO recycleDao;
	
	public Map<String, Object> addRecycle(int petId, int userId, int isSponsorship, double money, String reason, String address,String phone) {
		Map<String, Object> msgMap = new HashMap<>();
		Recycle recycle=new Recycle();
		recycle.setState(0);
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
	
	public List<Recycle> findUserRecycle(int userId) {
		return recycleDao.selectByUser(userId);
	}
	
	public Recycle findByPetAndUser(int petId, int userId) {
		return recycleDao.selectByPetAndUser(petId, userId);
	}
	
	public void updateRecycle(Recycle recycle){	
		recycleDao.updateRecycle(recycle);
	}

}

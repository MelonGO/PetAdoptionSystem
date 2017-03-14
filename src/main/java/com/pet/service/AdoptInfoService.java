package com.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.AdoptInfoDAO;
import com.pet.model.AdoptInfo;

@Service
public class AdoptInfoService {

	@Autowired
	private AdoptInfoDAO adoptInfoDao;

	public Map<String, Object> addAdoptInfo(int petId, int userId, String userName, 
											String realName, String address, String sex) {
		Map<String, Object> msgMap = new HashMap<>();

		AdoptInfo adoptInfo = adoptInfoDao.selectByPetAndUser(petId, userId);
		if (adoptInfo != null) {
			msgMap.put("msg", "exist");
			return msgMap;
		}

		AdoptInfo newAdopt = new AdoptInfo();
		newAdopt.setPetId(petId);
		newAdopt.setUserId(userId);
		newAdopt.setUserName(userName);
		newAdopt.setRealName(realName);
		newAdopt.setAddress(address);
		newAdopt.setSex(sex);

		adoptInfoDao.addAdoptInfo(newAdopt);
		msgMap.put("msg", "成功提交申请!");
		return msgMap;
	}

	public List<AdoptInfo> getAll() {
		return adoptInfoDao.getAll();
	}

}

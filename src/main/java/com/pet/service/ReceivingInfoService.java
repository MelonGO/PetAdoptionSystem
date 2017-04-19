package com.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.ReceivingInfoDAO;
import com.pet.model.ReceivingInfo;

@Service
public class ReceivingInfoService {
	
	@Autowired
	private ReceivingInfoDAO recInfoDao;
	
	public ReceivingInfo findByUserId(int userId) {
		return recInfoDao.selectByUserId(userId);
	}
	
	
	
}

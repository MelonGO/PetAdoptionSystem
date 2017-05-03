package com.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.MyOrderDAO;
import com.pet.model.MyOrder;

@Service
public class MyOrderService {
	@Autowired
	private MyOrderDAO orderDao;
	
	public List<MyOrder> getOrdersByUserId(int userId) {
		return orderDao.selectByUser(userId);
	}
	
	public int addOrder(MyOrder order) {
		return orderDao.addOrder(order);
	}
	
	
	public void deleteById(int id) {
		orderDao.deleteById(id);
	}
	
	public List<MyOrder> getAll() {
		return orderDao.getAll();
	}
	
	public void updateOrderState(MyOrder order){
		orderDao.updateOrderState(order);
	}
	
	public MyOrder findById(int id) {
		return orderDao.selectById(id);
	}
	
	
}

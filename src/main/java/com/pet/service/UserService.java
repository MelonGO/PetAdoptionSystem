package com.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dao.UserDAO;
import com.pet.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
	@Autowired
	private UserDAO userDao;
	
	public User getUser(int id)  {
		return userDao.selectById(id);
	}

	public int addUser(User user) {
		return userDao.addUser(user);
	}
    public  List<User> getAll(){
    	return userDao.getAll();
    }
    

	public Map<String, Object> login(String username, String password) {
		Map<String, Object> msgMap = new HashMap<>();

		User user = userDao.selectByName(username);
		if (user == null) {
			msgMap.put("msg", "该用户不存在!");
		} else {
			if (!password.equals(user.getPassword())) {
				msgMap.put("msg", "密码错误!");
			} else {
				msgMap.put("msg", "登陆成功!");
				msgMap.put("user", user);
				if(user.getRole().equals("admin")){
					msgMap.put("role", "admin");
				}else{
					msgMap.put("role", "user");
				}
			}
		}

		return msgMap;
	}
	
	public Map<String, Object> register(String username, String password, String role) {
		Map<String, Object> msgMap = new HashMap<>();

		User user = userDao.selectByName(username);
		if (user != null) {
			msgMap.put("msg", "该用户已经被注册!");
			return msgMap;
		}
		User userNew = new User();
		userNew.setName(username);
		userNew.setPassword(password);
		userNew.setRole(role);
		userDao.addUser(userNew);
		msgMap.put("msg", "注册成功!");
		msgMap.put("user", userNew);
		return msgMap;
	}
	
	public User checkName(String username){
		User user = userDao.selectByName(username);
		
		return user;
		
	}
	
	public boolean checkRole(String username){
		User user = userDao.selectByName(username);
		if(user.getRole().equals("admin")){
			return true;
		}
		return false;
		
	}
	
}

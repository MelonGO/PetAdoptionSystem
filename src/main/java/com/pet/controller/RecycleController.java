package com.pet.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Adoption;
import com.pet.model.Pet;
import com.pet.model.User;
import com.pet.model.Recycle;
import com.pet.service.AdoptionService;
import com.pet.service.RecycleService;
import com.pet.service.PetService;
import com.pet.service.UserService;

@Controller
public class RecycleController {
	
	@Autowired
	PetService petService;

	@Autowired
	RecycleService recycleService;
	
	@Autowired
	AdoptionService adoptInfoService;
	
	@Autowired
	UserService userService;
	
	
	//查看可送回的宠物
	@RequestMapping(path = { "/myRecycle" })
	public String Recycle(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "login";
		}
		
		Map<Adoption, Pet> myAdoptionMap = new LinkedHashMap<>();
		Map<Recycle, Pet> myRecycleMap = new LinkedHashMap<>();
		
		
		List<Adoption> adoptInfoList = adoptInfoService.findUserAdoption(user.getId());
		List<Recycle> recycleList = recycleService.findUserRecycle(user.getId());
		
		for (int i = 0; i < adoptInfoList.size(); i++) {
			Adoption adoptInfo= adoptInfoList.get(i);
			if(adoptInfo.getState()==3){
				Pet pet = petService.selectById(adoptInfo.getPetId());
				myAdoptionMap.put(adoptInfo, pet);
			}
		}
		
		for (int i = 0; i < recycleList.size(); i++) {
			Recycle recycle= recycleList.get(i);

			Pet pet = petService.selectById(recycle.getPetId());
			myRecycleMap.put(recycle, pet);
			
		}
		
		model.addAttribute("myAdoptionMap", myAdoptionMap);
		model.addAttribute("myRecycleMap", myRecycleMap);
		return "recycle";
	}
	
	@RequestMapping(path = { "/recycle.do" })
	public  @ResponseBody String ApplyRecycle(String reason,String pid,String address,String phone,String money,String sponsorship,Model model, HttpSession session) {

		if (session.getAttribute("user") == null) {
			return "0";

		} else {
			User user = (User) session.getAttribute("user");
			int petid=Integer.parseInt(pid);
			Adoption adoption = adoptInfoService.findByPetAndUser(petid, user.getId());		
			if(adoption.getState()!=3){
				return "1";
			}
			
			int isSponsorship=0;
			if(sponsorship.equals("进行助养")){
				isSponsorship=1;
			}
			recycleService.addRecycle(petid,user.getId(), isSponsorship,Double.parseDouble(money), reason, address, phone);
			adoption.setState(4);
			adoptInfoService.updateAdoption(adoption);
	
		}
		return "2";
	}
	
	
	@RequestMapping(path = { "/recycleCancel.do" })
	public  @ResponseBody String CancelRecycle(String pid,Model model, HttpSession session) {

		if (session.getAttribute("user") == null) {
			return "0";
		} else {
			User user = (User) session.getAttribute("user");
			int petid=Integer.parseInt(pid);
			Adoption adoption = adoptInfoService.findByPetAndUser(petid, user.getId());	
			Recycle recycle = recycleService.findByPetAndUser(petid, user.getId());		
			if(recycle.getState()!=0){
				return "1";
			}
			recycle.setState(-2);
			recycleService.updateRecycle(recycle);
			adoption.setState(3);
			adoptInfoService.updateAdoption(adoption);
	
		}
		return "2";
	}
}

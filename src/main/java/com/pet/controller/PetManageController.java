package com.pet.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pet.service.UserService;


import com.pet.model.Pet;
import com.pet.model.User;
import com.pet.service.PetService;

@Controller
public class PetManageController {
	@Autowired
	UserService userService;
	
	@Autowired
	PetService petService;

	@RequestMapping(path = { "/petManage" })
	public String petManage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "login";
		}
		if(!user.getRole().equals("admin")){
			return "login";
		}
		List<Pet> petList=petService.getAll();
		model.addAttribute("petList", petList);
		return "petManage";
	}
	@RequestMapping(path = { "/petPicture" })
	public  @ResponseBody String petPicture(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		if(!user.getRole().equals("admin")){
			return "0";
		}
		
		File targetFile = new File("");
		List<Pet> petList=petService.getAll();
		model.addAttribute("petList", petList);
		return "1";
	}
	
	
	@RequestMapping(path = { "/updatePet.do" })
	public @ResponseBody String petUpdate(String petID,String name,String type,String age,String sex,String price,String profile,Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		if(!user.getRole().equals("admin")){
			return "0";
		}
		int result=petService.update(petID, name,type,age,sex,price,profile);
		return String.valueOf(result+1);
	}
	
	
	@RequestMapping(path = { "/addPet" })
	public String petAdd(@RequestParam("addPetName")String name,@RequestParam("addPetType")String type,@RequestParam("addPetAge")String age,@RequestParam("addPetSex")String sex,@RequestParam("addPetPrice")String price,@RequestParam("addPetProfile")String profile,@RequestParam("file") MultipartFile picture,HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "redirect:login";
		}
		if(!user.getRole().equals("admin")){
			return "redirect:login";
		}
		int result=petService.add(name,type,age,sex,price,profile);
		int id=petService.getID();
		
		if (!picture.isEmpty()) {  
	        String fileName = picture.getOriginalFilename();  
	  
	        try {  
	            String tomcatPath = request.getServletContext().getRealPath("/"); //得到保存的路径  
	            FileCopyUtils.copy(picture.getBytes(), new File(tomcatPath +"/" +  id));//FileCopyUtils来自org.springframework.util.FileCopyUtils  
	  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	  }
		return "redirect:petManage";
	}
	
	
	@RequestMapping(path = { "/delPet.do" })
	public @ResponseBody String petDel(String id,Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "0";
		}
		if(!user.getRole().equals("admin")){
			return "0";
		}
		int result=petService.del(id);
		return String.valueOf(result+1);
	}
}

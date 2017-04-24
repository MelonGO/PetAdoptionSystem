package com.pet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Adoption;
import com.pet.model.Pet;
import com.pet.model.Prop;
import com.pet.model.ReceivingInfo;
import com.pet.model.User;
import com.pet.service.AdoptionService;
import com.pet.service.PetService;
import com.pet.service.PropService;
import com.pet.service.ReceivingInfoService;
import com.pet.service.UserService;

import tools.RequestUtil;

@Controller
public class AdoptionController {

	@Autowired
	PetService petService;

	@Autowired
	AdoptionService adoptInfoService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ReceivingInfoService recInfoService;
	
	@Autowired
	PropService propService;

	@RequestMapping(path = { "/petList" })
	public String adoption(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		model.addAttribute("currentHtml", "adoption");
		
		int num = petService.allPetsNumber();
		List<Integer> pages = new ArrayList<Integer>();

		int pageAmount = num / 5;
		if (num % 5 != 0) {
			pageAmount++;
		}

		int tmp = page;
		if (tmp % 5 == 0) {
			tmp = tmp - 4;
		} else {
			tmp = tmp - tmp % 5 + 1;
		}

		for (int i = 0; i < 5; i++) {
			if (tmp <= pageAmount) {
				pages.add(tmp);
				tmp++;
			}
		}

		model.addAttribute("pages", pages);

		model.addAttribute("current", page);
		model.addAttribute("previous", page - 1);
		model.addAttribute("next", page + 1);
		model.addAttribute("pageAmount", pageAmount);

		List<Pet> petList = petService.selectByPage((page - 1) * 5);
		
		Map<Pet, String> petMap = new LinkedHashMap<>();
		for (int i = 0; i < petList.size(); i++) {
			Pet pet = petList.get(i);
			Adoption adoptInfo = adoptInfoService.findByPetId(pet.getId());
			if (adoptInfo != null) {
				petMap.put(pet, "hasAdopt");
			} else {
				petMap.put(pet, "notAdopt");
			}
		}
		
		model.addAttribute("petMap", petMap);

		return "petList";
	}

	@RequestMapping(path = { "/wantAdopt" })
	public String wantAdopt(Model model, @RequestParam(value = "petId") int petId, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "redirect:petList?msg=notLogin";

		} else {
			User user = (User) session.getAttribute("user");
			String msg = adoptInfoService.findAlreadyExist(petId, user.getId());
			if (msg.equals("exist")) {
				model.addAttribute("error", "您已申请领养该宠物，请耐心等待申请结果!");
				return "error";
			}
			
			Pet pet = petService.selectById(petId);
			model.addAttribute("pet", pet);
			model.addAttribute("user", (User) session.getAttribute("user"));
			
			ReceivingInfo recInfo = recInfoService.findByUserId(user.getId());
			if(recInfo != null){
				model.addAttribute("recInfo", recInfo);
			}

			return "adopterInfo";
		}

	}

	@RequestMapping(path = { "/submitAdopt" })
	public String submitAdopt(Model model, @RequestParam("petId") int petId,
											@RequestParam("userId") int userId,
											@RequestParam("realName") String realName,
											@RequestParam("address") String address,
											@RequestParam("phone") String phone) throws IOException {
		Map<String, Object> map = adoptInfoService.addAdoption(petId, userId, realName, address, phone);
		String msg = (String) map.get("msg");
		if(!msg.equals("success")){
			model.addAttribute("error", "申请提交失败!");
			return "error";
		}
		
		return "redirect:petList?msg=success";

	}
	
	@RequestMapping(path = { "/myAdoption" })
	public String myAdoption(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		Map<Adoption, Pet> myAdoptionMap = new LinkedHashMap<>();
		
		List<Adoption> adoptInfoList = adoptInfoService.findUserAdoption(user.getId());
		for (int i = 0; i < adoptInfoList.size(); i++) {
			Adoption adoptInfo= adoptInfoList.get(i);
			Pet pet = petService.selectById(adoptInfo.getPetId());
			myAdoptionMap.put(adoptInfo, pet);
		}
		
		model.addAttribute("myAdoptionMap", myAdoptionMap);
		
		return "adoption";
		
	}
	
	@RequestMapping(path = { "/adoptionManage" })
	public String adoptionManage(Model model) {

		List<Adoption> allAdoptionList = adoptInfoService.getAll();
		
		List<Pet> petList = new ArrayList<>();
		List<User> userList = new ArrayList<>();
		List<ReceivingInfo> recInfoList = new ArrayList<>();
		

		for (int i = 0; i < allAdoptionList.size(); i++) {
			Adoption adoptInfo = allAdoptionList.get(i);
			Pet pet = petService.selectById(adoptInfo.getPetId());
			petList.add(pet);
			userList.add(userService.getUser(adoptInfo.getUserId()));
			recInfoList.add(recInfoService.findByUserId(adoptInfo.getUserId()));
		}

		model.addAttribute("allAdoptionList", allAdoptionList);
		model.addAttribute("petList", petList);
		model.addAttribute("userList", userList);
		model.addAttribute("recInfoList", recInfoList);
		
		return "adoptionManage";
	}
	
	@RequestMapping(path = { "/audit" })
	@ResponseBody
	public String audit(Model model,
						HttpServletRequest request) {
		Integer adoptionId = RequestUtil.getPositiveInteger(request, "adoptionId", null);
		String result = RequestUtil.getString(request, "result", null);

		Adoption adoption = adoptInfoService.findAdoptionById(adoptionId);

		if (result.equals("yes")) {
			adoption.setState(1);
		} else {
			adoption.setState(-1);
		}

		Map<String, Object> msg = adoptInfoService.updateAdoption(adoption);

		return (String) msg.get("msg");
	}
	
	@RequestMapping(path = { "/procedure" })
	public String procedure(Model model, 
							@RequestParam("adoptionId") int adoptionId) {
		Adoption adoption = adoptInfoService.findAdoptionById(adoptionId);
		
		Pet pet = petService.selectById(adoption.getPetId());
		
		ReceivingInfo recInfo = recInfoService.findByUserId(adoption.getUserId());
		
		model.addAttribute("adoption", adoption);
		model.addAttribute("pet", pet);
		model.addAttribute("recInfo", recInfo);
		
		List<Prop> propList = propService.getAllProps();
		model.addAttribute("propList", propList);
		
		return "procedure";
	}
	
	
	@RequestMapping(path = { "/shoppingCart" })
	@ResponseBody
	public String shoppingCart(Model model,
							@RequestParam("adoptionId") String adoptionId,
							@RequestParam("transport") String transport,
							@RequestParam(value = "prop[]", required = false) int[] prop) {
		String props = "";
		for (int n : prop) {
			props = props + " " + n;
		}
		return "add to shoppingCart success! <hr>" + "adoptionId:" + adoptionId + 
				"    transport:" + transport + "    propsId:" + props;
	}
	
	
	
	
}

package com.pet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.pet.model.AdoptInfo;
import com.pet.model.Pet;
import com.pet.model.User;
import com.pet.service.AdoptInfoService;
import com.pet.service.PetService;

import tools.RequestUtil;

@Controller
public class AdoptionController {

	@Autowired
	PetService petService;

	@Autowired
	AdoptInfoService adoptInfoService;

	@RequestMapping(path = { "/petList" })
	public String adoption(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
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

		model.addAttribute("previous", page - 1);
		model.addAttribute("next", page + 1);
		model.addAttribute("pageAmount", pageAmount);

		List<Pet> petList = petService.selectByPage((page - 1) * 5);
		model.addAttribute("petList", petList);

		return "petList";
	}

	@RequestMapping(path = { "/wantAdopt" })
	public String wantAdopt(Model model, @RequestParam(value = "petId") int petId, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "redirect:login";

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

			return "adopterInfo";
		}

	}

	@RequestMapping(path = { "/submitAdopt" })
	public String submitAdopt(Model model, @RequestParam("petId") int petId,
											@RequestParam("userId") int userId,
											@RequestParam("userName") String userName,
											@RequestParam("realName") String realName,
											@RequestParam("address") String address,
											@RequestParam("sex") String sex) throws IOException {
		Map<String, Object> map = adoptInfoService.addAdoptInfo(petId, userId, userName, realName, address, sex);
		String msg = (String) map.get("msg");
		if(!msg.equals("success")){
			model.addAttribute("error", "申请提交失败!");
			return "error";
		}
		
		return "redirect:petList";

	}
	
	@RequestMapping(path = { "/myAdoption" })
	public String myAdoption(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		Map<AdoptInfo, Pet> myAdoptionMap = new HashMap<>();
		
		List<AdoptInfo> adoptInfoList = adoptInfoService.findUserAdoptInfo(user.getId());
		for (int i = 0; i < adoptInfoList.size(); i++) {
			AdoptInfo adoptInfo= adoptInfoList.get(i);
			Pet pet = petService.selectById(adoptInfo.getPetId());
			myAdoptionMap.put(adoptInfo, pet);
		}
		
		model.addAttribute("myAdoptionMap", myAdoptionMap);
		
		return "adoption";
		
	}
	
	@RequestMapping(path = { "/adoptionManage" })
	public String adoptionManage(Model model) {
		Map<AdoptInfo, Pet> allAdoptionMap = new HashMap<>();
		
		List<AdoptInfo> allAdoptInfoList = adoptInfoService.getAll();
		
		for (int i = 0; i < allAdoptInfoList.size(); i++) {
			AdoptInfo adoptInfo= allAdoptInfoList.get(i);
			Pet pet = petService.selectById(adoptInfo.getPetId());
			allAdoptionMap.put(adoptInfo, pet);
		}
		
		model.addAttribute("allAdoptionMap", allAdoptionMap);
		return "adoptionManage";
	}
	
	@RequestMapping(path = { "/audit" })
	@ResponseBody
	public String audit(Model model,
						HttpServletRequest request) {
		Integer adoptInfoId = RequestUtil.getPositiveInteger(request, "adoptInfoId", null);
		String result = RequestUtil.getString(request, "result", null);
		
		return result;
	}

}

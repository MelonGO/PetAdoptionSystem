package com.pet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.MyOrder;
import com.pet.model.Pet;
import com.pet.model.Prop;
import com.pet.model.User;
import com.pet.service.MyOrderService;
import com.pet.service.PetService;
import com.pet.service.PropService;
import com.pet.service.UserService;

import tools.RequestUtil;

@Controller
public class OrderController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MyOrderService orderService;
	
	@Autowired
	PetService petService;
	
	@Autowired
	PropService propService;
	
	@RequestMapping(path = { "/myOrder" })
	public String myOrder(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");

		List<MyOrder> orderList =  orderService.getOrdersByUserId(user.getId());
		
		for (int i = 0; i < orderList.size(); i++) {
			MyOrder order = orderList.get(i);

			String pets = order.getPets();
			String props = order.getProps();

			String[] petsList = pets.split(",");
			String[] propsList = props.split(",");

			String newPets = "";
			String newProps = "";

			if (petsList.length > 0) {
				for (String pet : petsList) {
					if (!pet.equals("")) {
						int petId = Integer.parseInt(pet);
						Pet p = petService.selectById(petId);
						newPets += "宠物 名字:" + p.getName() + "," + "种类:" + p.getType() + "," + "性别:" + p.getSex() + "年龄:"
								+ p.getAge() + "     ";
					}
				}
			}
			
			if (propsList.length > 0) {
				for (String prop : propsList) {
					if (!prop.equals("")) {
						int propId = Integer.parseInt(prop);
						Prop p = propService.getByPropId(propId);
						newProps += p.getName() + "    ";
					}
				}
			}
			
			
			order.setPets(newPets);
			order.setProps(newProps);
			orderList.set(i, order);

		}
		
		model.addAttribute("orderList", orderList);
		
		return "order";
	}
	
	@RequestMapping(path = { "/removeOrder" })
	@ResponseBody
	public String removeOrder(HttpServletRequest request) {
		int orderId = RequestUtil.getInteger(request, "orderId", null);
		
		orderService.deleteById(orderId);
		
		return "订单删除成功!";
	}
	
	
	
	
}

package com.pet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Adoption;
import com.pet.model.MyOrder;
import com.pet.model.Pet;
import com.pet.model.Prop;
import com.pet.model.ReceivingInfo;
import com.pet.model.User;
import com.pet.service.AdoptionService;
import com.pet.service.MyOrderService;
import com.pet.service.PetService;
import com.pet.service.PropService;
import com.pet.service.ReceivingInfoService;
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
	
	@Autowired
	AdoptionService adoptionService;
	
	@Autowired
	ReceivingInfoService receivingInfoService;
	
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
						newPets += "宠物信息( 名字:" + p.getName() + "," + "种类:" + p.getType() + "," + "性别:" + p.getSex() + "年龄:"
								+ p.getAge() + " )";
					}
				}
			}
			
			if (propsList.length > 0) {
				for (String prop : propsList) {
					if (!prop.equals("")) {
						int propId = Integer.parseInt(prop);
						Prop p = propService.getByPropId(propId);
						newProps += p.getName() + "  ";
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
	
	@RequestMapping(path = { "/orderManage" })
	public String orderManage(Model model) {
		List<MyOrder> orderList = orderService.getAll();
		for (int i = 0; i < orderList.size(); i++) {
			MyOrder order = orderList.get(i);
			String pets = order.getPets();
			String props = order.getProps();
			
			int userId = order.getUserId();

			String[] petsList = pets.split(",");
			String[] propsList = props.split(",");

			String newPets = "";
			String newProps = "";
			
			if (propsList.length > 0) {
				for (String prop : propsList) {
					if (!prop.equals("")) {
						int propId = Integer.parseInt(prop);
						Prop p = propService.getByPropId(propId);
						newProps += "附加道具:" + p.getName() + "<br>";
									
					}
				}
				ReceivingInfo receivingInfo = receivingInfoService.findByUserId(userId);
				newProps += "接收人姓名:" + receivingInfo.getRealName() + "  地址:" + receivingInfo.getAddress() + "  <br>"
							+ "联系方式:" + receivingInfo.getPhone() + "  <br>";
			}
			
			if (petsList.length > 0) {
				for (String pet : petsList) {
					if (!pet.equals("")) {
						int petId = Integer.parseInt(pet);
						Pet p = petService.selectById(petId);
						Adoption adoption = adoptionService.findByPetId(petId);
						
						ReceivingInfo receivingInfo = receivingInfoService.findById(adoption.getReceivingInfoId());
						if(newProps.equals("")) {
							newPets += "宠物信息<br>"
										+ "名字:" + p.getName() + "  种类:" + p.getType() + "  <br>" 
										+ "性别:" + p.getSex() + "  年龄:" + p.getAge() + "  <br>" 
										+ "接收人姓名:" + receivingInfo.getRealName() + "  地址:" + receivingInfo.getAddress() + "  <br>"
										+ "联系方式:" + receivingInfo.getPhone() + "  运输方式:" + adoption.getTransportWay() + "  <br>";
						} else {
							newPets += "宠物信息<br>"
										+ "名字:" + p.getName() + "  种类:" + p.getType() + "  <br>" 
										+ "性别:" + p.getSex() + "  年龄:" + p.getAge() + "  <br>"
										+ "运输方式:" + adoption.getTransportWay() + "  <br>";
						}
						
					}
				}
			}
			
			order.setPets(newPets);
			order.setProps(newProps);
			orderList.set(i, order);
			
		}
		
		model.addAttribute("orderList", orderList);
		
		return "orderManage";
	}
	
	@RequestMapping(path = { "/deliver" })
	@ResponseBody
	public String deliver(HttpServletRequest request) {
		int orderId = RequestUtil.getInteger(request, "orderId", null);
		MyOrder order = orderService.findById(orderId);
		order.setState(1);
		
		orderService.updateOrderState(order);
		
		return "发送成功!";
	}
	
	@RequestMapping(path = { "/confirmReceipt" })
	@ResponseBody
	public String confirmReceipt(HttpServletRequest request) {
		int orderId = RequestUtil.getInteger(request, "orderId", null);
		MyOrder order = orderService.findById(orderId);
		order.setState(2);
		orderService.updateOrderState(order);
		
		String pets = order.getPets();
		String[] petsList = pets.split(",");
		if (petsList.length > 0) {
			for (String pet : petsList) {
				if (!pet.equals("")) {
					int petId = Integer.parseInt(pet);
					Adoption adoption = adoptionService.findByPetId(petId);
					adoption.setState(3);
					adoptionService.updateAdoption("state", adoption);
				}
			}
		}
		
		return "确认收货成功!";
	}
	
	
}

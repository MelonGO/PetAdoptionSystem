package com.pet.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.model.Adoption;
import com.pet.model.MyOrder;
import com.pet.model.Recycle;
import com.pet.model.ShoppingCart;
import com.pet.model.User;
import com.pet.service.AdoptionService;
import com.pet.service.MyOrderService;
import com.pet.service.RecycleService;

import net.sf.json.JSONObject;

@Controller
public class PaymentController {
	
	@Autowired
	AdoptionService adoptionService;
	
	@Autowired
	MyOrderService orderService;
	
	@Autowired
	RecycleService recycleService;
	
	@RequestMapping(path = { "/payment" })
	public String pay(Model model, @RequestParam("pets") String pets,
									@RequestParam("props") String props,
									@RequestParam("total") Double total) {
		model.addAttribute("total", total);
		model.addAttribute("pets", pets);
		model.addAttribute("props", props);
		
		return "payment";
	}
	
	@RequestMapping(path = { "/submitPay" })
	public String payment(HttpSession session, HttpServletResponse response, 
						@CookieValue(value = "cartCookie", required  = false) String cartCookieStr, 
						@RequestParam(value = "pets", required  = false) String pets,
						@RequestParam(value = "props", required  = false) String props,
						@RequestParam("total") Double total,
						ShoppingCart shoppingCart) throws Exception {
		cartCookieStr = URLDecoder.decode(cartCookieStr, "utf-8");
		JSONObject jsonCart = JSONObject.fromObject(cartCookieStr);
		shoppingCart = (ShoppingCart) JSONObject.toBean(jsonCart, ShoppingCart.class);//Json转换成对象Cart
		
		List<Integer> cartAdoptionList = shoppingCart.getAdoptionList();
		List<Integer> cartPropList = shoppingCart.getPropList();
		
		User user = (User) session.getAttribute("user");
		
		String[] petsList = pets.split(",");
		String[] propsList = props.split(",");
		if (petsList.length > 0) {
			for (String pet : petsList) {
				if (!pet.equals("")) {
					int petId = Integer.parseInt(pet);
					Adoption adoption = adoptionService.findByPetAndUser(petId, user.getId());

					for (int i = 0; i < cartAdoptionList.size(); i++) {
						if (cartAdoptionList.get(i) == adoption.getId()) {
							cartAdoptionList.remove(i--);
						}
					}

					adoption.setState(2);
					adoptionService.updateAdoption("state", adoption);
				}
			}
		}

		if (propsList.length > 0) {
			for (String prop : propsList) {
				if (!prop.equals("")) {
					int propId = Integer.parseInt(prop);
					for (int i = 0; i < cartPropList.size(); i++) {
						if (cartPropList.get(i) == propId) {
							cartPropList.remove(i--);
						}
					}
				}
			}
		}
		
		shoppingCart.setAdoptionList(cartAdoptionList);
		shoppingCart.setPropList(cartPropList);
		
		String cartCookie = JSONObject.fromObject(shoppingCart).toString();//Cart转换成对象Json
		cartCookie = URLEncoder.encode(cartCookie, "utf-8");
	    Cookie cookie = new Cookie("cartCookie",cartCookie);  
	    cookie.setMaxAge(60*60*24*7);//保留7天
	    response.addCookie(cookie);
	    
	    MyOrder newOrder = new MyOrder();
	    newOrder.setUserId(user.getId());
	    newOrder.setTotal(total);
	    if (petsList.length > 0) {
	    	newOrder.setPets(pets);
	    }
	    if (propsList.length > 0) {
	    	newOrder.setProps(props);
	    }
	    
	    orderService.addOrder(newOrder);
		
		return "redirect:myOrder";
	}
	
	@RequestMapping(path = { "/recyclePayment" })
	public String recyclePayment(Model model, @RequestParam("recycleId") int recycleId) {
		Recycle recycle = recycleService.findRecycleById(recycleId);
		Double money = recycle.getMoney();
		
		model.addAttribute("total", money);
		model.addAttribute("recycleId", recycleId);
		
		return "payment";
	}
	
	@RequestMapping(path = { "/submitPayRecycle" })
	public String submitPayRecycle(@RequestParam("recycleId") int recycleId,
									@RequestParam("total") Double total) {
		Recycle recycle = recycleService.findRecycleById(recycleId);
		recycle.setState(2);
		
		recycleService.updateRecycle(recycle);
		
		return "redirect:myRecycle";
	}
	
}

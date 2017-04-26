package com.pet.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Adoption;
import com.pet.model.ShoppingCart;
import com.pet.model.User;
import com.pet.service.AdoptionService;

import net.sf.json.JSONObject;

@Controller
public class PaymentController {
	
	@Autowired
	AdoptionService adoptionService;
	
	@RequestMapping(path = { "/payment" })
	@ResponseBody
	public String payment(HttpSession session, HttpServletRequest request, HttpServletResponse response, 
						@CookieValue(value = "cartCookie", required  = false) String cartCookieStr, 
						ShoppingCart shoppingCart) throws Exception {
		cartCookieStr = URLDecoder.decode(cartCookieStr, "utf-8");
		JSONObject jsonCart = JSONObject.fromObject(cartCookieStr);
		shoppingCart = (ShoppingCart) JSONObject.toBean(jsonCart, ShoppingCart.class);//Json转换成对象Cart
		
		List<Integer> cartAdoptionList = shoppingCart.getAdoptionList();
		List<Integer> cartPropList = shoppingCart.getPropList();
		
		User user = (User) session.getAttribute("user");
		
		Double total = Double.parseDouble(request.getParameter("total"));
		String pets = request.getParameter("pets");
		String props = request.getParameter("props");
		
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

					adoption.setState(3);
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
		
		return "支付成功!";
	}
	
}

package com.pet.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pet.model.Pet;
import com.pet.model.Prop;
import com.pet.model.ShoppingCart;
import com.pet.model.User;
import com.pet.service.AdoptionService;
import com.pet.service.PetService;
import com.pet.service.PropService;

import net.sf.json.JSONObject;

@Controller
public class ShoppingCartController {
	
	@Autowired
	AdoptionService adoptionService;
	
	@Autowired
	PetService petService;
	
	@Autowired
	PropService propService;
	
	@RequestMapping(path = { "/addToCart" })
	public String addToCart(HttpServletResponse response, HttpSession session,
							@CookieValue(value = "cartCookie", required  = false) String cartCookieStr, 
							@RequestParam("adoptionId") int adoptionId,
							@RequestParam("transport") String transport,
							@RequestParam(value = "prop[]", required = false) int[] prop, 
							ShoppingCart shoppingCart) throws Exception {
		User user = (User) session.getAttribute("user");
		shoppingCart.setUserId(user.getId());
		
		if (cartCookieStr == null) {
			shoppingCart.getAdoptionList().add(adoptionId);
			for (int i = 0; i < prop.length; i++) {
				shoppingCart.getPropList().add(prop[i]);
			}
			
		} else {
			cartCookieStr = URLDecoder.decode(cartCookieStr, "utf-8");
			JSONObject jsonCart = JSONObject.fromObject(cartCookieStr);
			shoppingCart = (ShoppingCart) JSONObject.toBean(jsonCart, ShoppingCart.class);//Json转换成对象Cart
			shoppingCart.getAdoptionList().add(adoptionId);
			for (int i = 0; i < prop.length; i++) {
				shoppingCart.getPropList().add(prop[i]);
			}
		}

		String cartCookie = JSONObject.fromObject(shoppingCart).toString();//Cart转换成对象Json
		cartCookie = URLEncoder.encode(cartCookie, "utf-8");
	    Cookie cookie = new Cookie("cartCookie",cartCookie);  
	    cookie.setMaxAge(60*60*24*7);//保留7天
	    response.addCookie(cookie);
	    
	    Adoption adoption = adoptionService.findAdoptionById(adoptionId);
	    adoption.setTransport_way(transport);
	    
	    adoptionService.updateAdoption("transport", adoption);
	    
	    
		return "redirect:shoppingCart";
	}
	
	@RequestMapping(path = { "/shoppingCart" })
	public String shoppingCart(Model model, HttpSession session,
							@CookieValue(value = "cartCookie", required  = false) String cartCookieStr) throws Exception {
		User user = (User) session.getAttribute("user");
		
		if (cartCookieStr != null) {
			cartCookieStr = URLDecoder.decode(cartCookieStr, "utf-8");
			JSONObject jsonCart = JSONObject.fromObject(cartCookieStr);
			ShoppingCart shoppingCart = (ShoppingCart) JSONObject.toBean(jsonCart, ShoppingCart.class);

			if (shoppingCart.getUserId() == user.getId()) {
				List<Pet> petList = new ArrayList<>();
				Map<Prop, Integer> propMap = new HashMap<Prop, Integer>();

				List<Integer> adoptionList = shoppingCart.getAdoptionList();
				for (int i = 0; i < adoptionList.size(); i++) {
					int adoptionId = adoptionList.get(i);

					Adoption adoption = adoptionService.findAdoptionById(adoptionId);
					Pet pet = petService.selectById(adoption.getPetId());
					petList.add(pet);
				}
				
				List<Integer> propList = shoppingCart.getPropList();
				for (int k = 0; k < propList.size(); k++) {
					int propId = propList.get(k);
					Prop prop = propService.getByPropId(propId);
					
					boolean exist = false;
					for (Prop p : propMap.keySet()) {
						if (p.getName().equals(prop.getName())) {
							exist = true;
							propMap.put(p, propMap.get(p) + 1);
						}
					}
					if (exist == false) {
						propMap.put(prop, 1);
					}
				}

				model.addAttribute("petList", petList);
				model.addAttribute("propMap", propMap);
			}
		}
		
		return "shoppingCart";
	}
	
}

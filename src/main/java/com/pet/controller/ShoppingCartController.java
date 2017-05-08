package com.pet.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Adoption;
import com.pet.model.Pet;
import com.pet.model.Prop;
import com.pet.model.ShoppingCart;
import com.pet.model.User;
import com.pet.service.AdoptionService;
import com.pet.service.PetService;
import com.pet.service.PropService;

import net.sf.json.JSONObject;
import tools.RequestUtil;

@Controller
public class ShoppingCartController {
	
	@Autowired
	AdoptionService adoptionService;
	
	@Autowired
	PetService petService;
	
	@Autowired
	PropService propService;
	
	@RequestMapping(path = { "/addToCart" })
	public String addToCart(HttpServletResponse response, HttpSession session, Model model, 
							@CookieValue(value = "cartCookie", required  = false) String cartCookieStr, 
							@RequestParam("adoptionId") int adoptionId,
							@RequestParam("transport") String transport,
							@RequestParam(value = "prop[]", required = false) int[] props, 
							ShoppingCart shoppingCart) throws Exception {
		User user = (User) session.getAttribute("user");
		
		if (cartCookieStr == null) {
			shoppingCart.setUserId(user.getId());
			shoppingCart.getAdoptionList().add(adoptionId);
			if(props != null) {
				for (int i = 0; i < props.length; i++) {
					shoppingCart.getPropList().add(props[i]);
				}
			}
			
		} else {
			cartCookieStr = URLDecoder.decode(cartCookieStr, "utf-8");
			JSONObject jsonCart = JSONObject.fromObject(cartCookieStr);
			shoppingCart = (ShoppingCart) JSONObject.toBean(jsonCart, ShoppingCart.class);//Json转换成对象Cart
			if(shoppingCart.getUserId() == user.getId()) {
				shoppingCart.getAdoptionList().add(adoptionId);
				if(props != null) {
					for (int i = 0; i < props.length; i++) {
						shoppingCart.getPropList().add(props[i]);
					}
				}
			} else {
				shoppingCart = new ShoppingCart();
				shoppingCart.setUserId(user.getId());
				shoppingCart.getAdoptionList().add(adoptionId);
				if(props != null) {
					for (int i = 0; i < props.length; i++) {
						shoppingCart.getPropList().add(props[i]);
					}
				}
			}
			
		}

		String cartCookie = JSONObject.fromObject(shoppingCart).toString();//Cart转换成对象Json
		cartCookie = URLEncoder.encode(cartCookie, "utf-8");
	    Cookie cookie = new Cookie("cartCookie",cartCookie);  
	    cookie.setMaxAge(60*60*24*3);//保留3天
	    response.addCookie(cookie);
	    
	    Adoption adoption = adoptionService.findAdoptionById(adoptionId);
	    adoption.setTransportWay(transport);
	    
	    adoptionService.updateAdoption("transport", adoption);
	    
	   
	    if (shoppingCart.getUserId() == user.getId()) {
			List<Pet> petList = new ArrayList<>();
			Map<Prop, Integer> propMap = new HashMap<Prop, Integer>();

			List<Integer> adoptionList = shoppingCart.getAdoptionList();
			for (int i = 0; i < adoptionList.size(); i++) {
				int adopId = adoptionList.get(i);

				Adoption adop = adoptionService.findAdoptionById(adopId);
				Pet pet = petService.selectById(adop.getPetId());
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
	
	@RequestMapping(path = { "/removePetCookie" })
	@ResponseBody
	public String removePetCookie(HttpSession session, 
								HttpServletRequest request, 
								HttpServletResponse response, 
								@CookieValue(value = "cartCookie", required  = false) String cartCookieStr, 
								ShoppingCart shoppingCart) throws Exception {
		cartCookieStr = URLDecoder.decode(cartCookieStr, "utf-8");
		JSONObject jsonCart = JSONObject.fromObject(cartCookieStr);
		shoppingCart = (ShoppingCart) JSONObject.toBean(jsonCart, ShoppingCart.class);//Json转换成对象Cart
		
		List<Integer> cartAdoptionList = shoppingCart.getAdoptionList();
		
		User user = (User) session.getAttribute("user");
		int petId = RequestUtil.getInteger(request, "petId", null);
		
		Adoption adoption = adoptionService.findByPetAndUser(petId, user.getId());
		
		for (int i = 0; i < cartAdoptionList.size(); i++) {
			if (cartAdoptionList.get(i) == adoption.getId()) {
				cartAdoptionList.remove(i--);
			}
		}
		
		shoppingCart.setAdoptionList(cartAdoptionList);
		
		String cartCookie = JSONObject.fromObject(shoppingCart).toString();//Cart转换成对象Json
		cartCookie = URLEncoder.encode(cartCookie, "utf-8");
	    Cookie cookie = new Cookie("cartCookie",cartCookie);  
	    cookie.setMaxAge(60*60*24*7);//保留7天
	    response.addCookie(cookie);
		
		return "删除成功!";
	}
	
	@RequestMapping(path = { "/removePropCookie" })
	@ResponseBody
	public String removePropCookie(HttpServletRequest request, 
									HttpServletResponse response, 
									@CookieValue(value = "cartCookie", required  = false) String cartCookieStr, 
									ShoppingCart shoppingCart) throws Exception {
		cartCookieStr = URLDecoder.decode(cartCookieStr, "utf-8");
		JSONObject jsonCart = JSONObject.fromObject(cartCookieStr);
		shoppingCart = (ShoppingCart) JSONObject.toBean(jsonCart, ShoppingCart.class);//Json转换成对象Cart
		
		List<Integer> cartPropList = shoppingCart.getPropList();
		
		int propId = RequestUtil.getInteger(request, "propId", null);
		
		for (int i = 0; i < cartPropList.size(); i++) {
			if (cartPropList.get(i) == propId) {
				cartPropList.remove(i--);
			}
		}
		
		shoppingCart.setPropList(cartPropList);
		
		String cartCookie = JSONObject.fromObject(shoppingCart).toString();//Cart转换成对象Json
		cartCookie = URLEncoder.encode(cartCookie, "utf-8");
	    Cookie cookie = new Cookie("cartCookie",cartCookie);  
	    cookie.setMaxAge(60*60*24*3);//保留3天
	    response.addCookie(cookie);
	    
		return "删除成功!";
	}
	
}

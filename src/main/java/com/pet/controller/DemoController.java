package com.pet.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.model.Demo;

@Controller
public class DemoController {
    
    //举个例子 http://localhost:8080/profile/12/45?type=111  这个会匹配这样的url
    @RequestMapping(value = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId, 
    						@PathVariable("userId") String userId, 
    						@RequestParam(value="type", defaultValue="1") int type) {
    	return String.format("%s,%s,%d", groupId, userId, type);
    }
    
    @RequestMapping("vm")
    public String news(Model model) {
    	model.addAttribute("value1", "vv1");
    	
    	List<String> colors = Arrays.asList(new String[] {"red","yellow"});
    	
    	Map<String, String> map = new HashMap<>();
    	
    	for(int i=1;i<4;i++) {
    		map.put(String.valueOf(i), String.valueOf(i*i));
    	}
    	
    	model.addAttribute("colors", colors);
    	model.addAttribute("map", map);
    	
    	model.addAttribute("user", new Demo());
    	return "demo";
    	
    }
}

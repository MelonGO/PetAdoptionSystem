package com.pet.controller;

import java.io.File;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	
	@RequestMapping(path = { "/file" })
	public String fileUpload(){
		return "file";
	}
	
	@RequestMapping(path = { "/upload" })
	public String fileupload(@RequestParam(value = "file", required = false) MultipartFile file, 
							String desc,
							HttpServletRequest request, 
							Model model) {

//		String path = request.getSession().getServletContext().getRealPath("upload");
		String path = "/Users/";
		String fileName = file.getOriginalFilename();
		String extfileName = fileName.substring(fileName.indexOf("."));

		System.out.println(path);
		System.out.println(extfileName);

		String systime = System.currentTimeMillis() + "";

		Random r = new Random();
		int rnum = r.nextInt(10000);

		systime += rnum;
		systime += extfileName;

		File targetFile = new File(path, systime);
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		String url=request.getContextPath()+"/upload/"+systime;
		String url = "images/" + systime;
		model.addAttribute("url", url);
		return "success";
	}
}

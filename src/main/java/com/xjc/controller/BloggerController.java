package com.xjc.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjc.service.BloggerService;

@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Resource
	BloggerService bloggerService;
	
	@RequestMapping("/info")
	public String info(Model model){
		
		model.addAttribute("blogger", bloggerService.findById(1));
		return "blogger/info";
	}
}

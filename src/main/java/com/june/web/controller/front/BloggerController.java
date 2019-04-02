package com.june.web.controller.front;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.june.service.BloggerService;

@Controller
public class BloggerController {

	@Resource
	private BloggerService bloggerService;
	
	@GetMapping("/about")
	public String info(Model model) {
		model.addAttribute("blogger", bloggerService.find());
		return "blogger/info";
	}
}

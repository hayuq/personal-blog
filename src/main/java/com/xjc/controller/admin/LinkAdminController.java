package com.xjc.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjc.model.Link;
import com.xjc.service.LinkService;

@Controller
@RequestMapping("/link")
public class LinkAdminController {

	@Resource
	LinkService linkService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("linkList", linkService.getLinkList());
		return "link/list";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "link/add";
	}
	
	@RequestMapping("/add")
	public String add(Link link) {
		linkService.add(link);
		return "redirect:/link/list.do";
	}
	
	@RequestMapping("/delete")
	public String delete(Integer id){
		linkService.delete(id);
		return "redirect:/link/list.do";
	}
}

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
	public String add(Link link,Model model) {
		int result = linkService.add(link);
		if (result > 0)	
			model.addAttribute("msg", "保存成功");
		else
			model.addAttribute("msg", "保存失败");
		return null;
	}
	
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id,Model model) {
		model.addAttribute("link", linkService.findById(id));
		return "link/update";
	}
	
	@RequestMapping("/update")
	public String update(Link link,Model model) {
		int result = linkService.update(link);
		if (result > 0)	
			model.addAttribute("msg", "保存成功");
		else
			model.addAttribute("msg", "保存失败");
		return null;
	}
	
	@RequestMapping("/delete")
	public String delete(Integer id){
		linkService.delete(id);
		return "redirect:/link/list.do";
	}
	
	@RequestMapping("/deletes")
	public String deletes(String ids){
		for(String id : ids.split(","))
			linkService.delete(Integer.parseInt(id));
		return "redirect:/link/list.do";
	}
}

package com.june.web.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.june.model.Link;
import com.june.service.LinkService;

@Controller
@RequestMapping("/link")
public class LinkAdminController {

	@Resource
	private LinkService linkService;
	
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
	public void add(Link link, Model model) {
		int result = linkService.add(link);
		model.addAttribute("msg", result > 0 ? "保存成功" : "保存失败");
	}
	
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id, Model model) {
		model.addAttribute("link", linkService.findById(id));
		return "link/update";
	}
	
	@RequestMapping("/update")
	public void update(Link link,Model model) {
		int result = linkService.update(link);
		model.addAttribute("msg", result > 0 ? "保存成功" : "保存失败");
	}
	
	@RequestMapping("/delete")
	public String delete(Integer id) {
		linkService.delete(id);
		return "redirect:/link/list.do";
	}
	
	@RequestMapping("/deletes")
	public String deletes(String ids) {
		String[] idArr = org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids);
		int len = idArr.length;
		Integer[] linkIds = new Integer[len];
		for (int i = 0; i < len; i++) {
			linkIds[i] = Integer.parseInt(idArr[i]);
		}
		linkService.batchDelete(linkIds);
		return "redirect:/link/list.do";
	}
}

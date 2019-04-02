package com.june.web.controller.admin;

import static com.june.vo.Response.success;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.june.model.Link;
import com.june.service.LinkService;
import com.june.vo.Response;

@Controller
@RequestMapping("/link")
public class LinkAdminController {

	@Resource
	private LinkService linkService;
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("linkList", linkService.getLinkList());
		return "link/list";
	}
	
	@GetMapping("/toAdd")
	public String toAdd() {
		return "link/add";
	}
	
	@PostMapping("/add")
	public @ResponseBody Response add(@RequestParam Link link) {
		linkService.add(link);
		return success();
	}
	
	@GetMapping("/toUpdate")
	public String toUpdate(@RequestParam Integer id, Model model) {
		model.addAttribute("link", linkService.findById(id));
		return "link/update";
	}
	
	@PostMapping("/update")
	public void update(@RequestParam Link link, Model model) {
		boolean success = linkService.update(link);
		model.addAttribute("msg", success ? "保存成功" : "保存失败");
	}
	
	@PostMapping("/delete")
	public @ResponseBody Response delete(@RequestParam Integer id) {
		linkService.delete(id);
		return success();
	}
	
	@PostMapping("/deletes")
	public @ResponseBody Response deletes(@RequestBody Integer[] ids) {
		linkService.batchDelete(ids);
		return success();
	}
}

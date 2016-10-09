package com.xjc.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opensymphony.oscache.util.StringUtil;
import com.xjc.model.BlogType;
import com.xjc.model.PageBean;
import com.xjc.service.BlogTypeService;
import com.xjc.util.PageUtils;

/**
 * 博客类别Controller
 */
@Controller
@RequestMapping("/blogType")
public class BlogTypeAdminController {

	@Resource
	BlogTypeService blogTypeService;

	@RequestMapping("/list")
	public String list(String page, Model model, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer currentPage = StringUtil.isEmpty(page) ? 1 : Integer.parseInt(page);
		PageBean pageBean = new PageBean(currentPage, 10);
		int pageSize = pageBean.getPageSize();

		int totalCount = blogTypeService.getTypeList(map).size();
		pageBean.setTotalCount(totalCount);
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		List<BlogType> blogTypeList = blogTypeService.getTypeList(map);
		model.addAttribute("pagination", pageBean);

		String targetUrl = request.getContextPath() + "/blogType/list.do";
		String pageCode = PageUtils.genPagination(targetUrl, totalCount, currentPage, pageSize, "");
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("entry", map);
		model.addAttribute("blogTypeList", blogTypeList);
		return "blogType/list";
	}

	@RequestMapping("/toAdd")
	public String toAdd() {
		return "blogType/add";
	}

	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id,Model model) {
		model.addAttribute("blogType", blogTypeService.findById(id));
		return "blogType/update";
	}

	@RequestMapping("/add")
	public String add(BlogType blogType) {
		blogTypeService.add(blogType);
		return "redirect:/blogType/list.do";
	}
	
	@RequestMapping("/update")
	public String update(BlogType blogType) {
		blogTypeService.update(blogType);
		return "redirect:/blogType/list.do";
	}

	@RequestMapping("/delete")
	public String delete(Integer id) {
		blogTypeService.delete(id);
		return "redirect:/blogType/list.do";
	}

	@RequestMapping("/deletes")
	public String deletes(String ids) {
		String[] commentIds = ids.split(",");
		for (String id : commentIds) {
			blogTypeService.delete(Integer.parseInt(id));
		}
		return "redirect:/blogType/list.do";
	}
}
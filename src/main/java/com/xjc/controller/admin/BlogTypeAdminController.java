package com.xjc.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opensymphony.oscache.util.StringUtil;
import com.xjc.model.Blog;
import com.xjc.model.BlogType;
import com.xjc.model.PageBean;
import com.xjc.service.BlogService;
import com.xjc.service.BlogTypeService;
import com.xjc.util.PageUtils;
import com.xjc.util.ResponseUtils;

/**
 * 博客类别Controller
 */
@Controller
@RequestMapping("/blogType")
public class BlogTypeAdminController {

	@Resource
	BlogTypeService blogTypeService;
	
	@Resource
	BlogService blogService;

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
	
	@RequestMapping("/search")
	public String search(Integer id,HttpServletResponse response){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", blogService.getByTypeId(id).size());
		ResponseUtils.writeJson(response, jsonObj.toString());
		return null;
	}

	//只删除类别
	@RequestMapping("/delete")
	public String delete(Integer id) {
		blogTypeService.delete(id);
		return "redirect:/blogType/list.do";
	}

	//删除的同时将相关博客的类别置为默认分类
	@RequestMapping("/batch_delete")
	public String batchDelete(Integer id) {
		
		List<Blog> blogs = blogService.getByTypeId(id);
		for(Blog blog : blogs){
			blog.setTypeId(1);
			blogService.update(blog);
		}
		blogTypeService.delete(id);
		return "redirect:/blogType/list.do";
	}
}

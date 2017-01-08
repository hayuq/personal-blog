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
import com.xjc.util.Constants;
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
		Map<String, Object> params = new HashMap<String, Object>();
		Integer currentPage = StringUtil.isEmpty(page) ? 1 : Integer.parseInt(page);
		PageBean pageBean = new PageBean(currentPage, Constants.BACK_PAGE_SIZE + 3);
		int pageSize = pageBean.getPageSize();

		int totalCount = blogTypeService.getTypeList(params).size();
		pageBean.setTotalCount(totalCount);
		params.put("start", pageBean.getStart());
		params.put("size", pageSize);
		List<BlogType> blogTypeList = blogTypeService.getTypeList(params);
		blogTypeList.forEach(blogType -> {
			Map<String, Object> types = new HashMap<String, Object>();
			types.put("typeId", blogType.getTypeId());
			Integer blogCount = blogService.getCount(types);
			blogType.setBlogCount(blogCount);
		});
		model.addAttribute("pagination", pageBean);

		String targetUrl = request.getContextPath() + "/blogType/list.do";
		String pageCode = PageUtils.genPagination(targetUrl, totalCount, currentPage, pageSize, "");
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("entry", params);
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
	public String add(BlogType blogType,Model model) {
		int result = blogTypeService.add(blogType);
		if (result > 0)	
			model.addAttribute("msg", "保存成功");
		else
			model.addAttribute("msg", "保存失败");
		return null;
	}
	
	@RequestMapping("/update")
	public String update(BlogType blogType,Model model) {
		int result = blogTypeService.update(blogType);
		if (result > 0)	
			model.addAttribute("msg", "保存成功");
		else
			model.addAttribute("msg", "保存失败");
		return null;
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

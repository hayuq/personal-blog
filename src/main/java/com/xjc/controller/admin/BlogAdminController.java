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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opensymphony.oscache.util.StringUtil;
import com.xjc.model.Blog;
import com.xjc.model.PageBean;
import com.xjc.service.BlogService;
import com.xjc.service.BlogTypeService;
import com.xjc.service.CommentService;
import com.xjc.util.PageUtils;
import com.xjc.util.ResponseUtils;

/**
 * 博客Controller
 */
@Controller
@RequestMapping("/blog")
public class BlogAdminController {

	@Resource
	BlogService blogService;
	
	@Resource
	BlogTypeService blogTypeService;
	
	@Resource
	CommentService commentService;
	
	@RequestMapping("/list")
	public String list(String page, String firstDate,String secondDate, Integer typeId, 
			String title, Model model, HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Integer currentPage = StringUtil.isEmpty(page) ? 1 : Integer.parseInt(page);
		PageBean pageBean = new PageBean(currentPage, 10);
		int pageSize = pageBean.getPageSize();
		map.put("typeId", typeId);
		map.put("title", title);
		map.put("firstDate", firstDate);
		map.put("secondDate", secondDate);
		int totalCount = blogService.getBlogList(map).size();
		pageBean.setTotalCount(totalCount);
		
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		List<Blog> blogList = blogService.getBlogList(map);
		model.addAttribute("pagination", pageBean);
		
		StringBuffer param=new StringBuffer(); // 分页查询参数
		param.append(StringUtil.isEmpty(title) ? "" : "title=" + title);
		param.append(StringUtil.isEmpty(firstDate) ? "" : "&firstDate=" + firstDate);
		param.append(StringUtil.isEmpty(secondDate) ? "" : "&secondDate=" + secondDate);
		param.append(typeId == null ? "" : "&typeId=" + typeId);
		
		String targetUrl = request.getContextPath() + "blog/list.do";
		String pageCode = PageUtils.genPagination(targetUrl, totalCount, currentPage, pageSize, param.toString());
		model.addAttribute("pageCode",pageCode);
		model.addAttribute("entry",map);
		model.addAttribute("blogList", blogList);
		return "blog/list";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(Model model) {
		model.addAttribute("blogTypeList", blogTypeService.getTypeList());
		return "blog/add";
	}
	
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id,Model model) {
		model.addAttribute("blogTypeList", blogTypeService.getTypeList());
		model.addAttribute("blog", blogService.findById(id));
		return "blog/update";
	}
	
	@RequestMapping("/add")
	public String add(Blog blog,@RequestParam(value="img",required=false)MultipartFile file,
			HttpServletResponse response) {
			
		//TODO 图片文件上传功能
		System.out.println("原始路径：================="+file.getOriginalFilename());
		int result = blogService.add(blog);
		JSONObject jsonObj = new JSONObject();
		if(result > 0)
			jsonObj.put("success", true);
		ResponseUtils.writeJson(response, jsonObj.toString());
		return null;
	}
	
	@RequestMapping("/update")
	public String update(Blog blog,HttpServletResponse response) {
		int result = blogService.update(blog);
		JSONObject jsonObj = new JSONObject();
		if(result > 0)
			jsonObj.put("success", true);
		ResponseUtils.writeJson(response, jsonObj.toString());
		return null;
	}
	
	@RequestMapping("/delete")
	public String delete(Integer id){
		blogService.delete(id);
		return "redirect:/blog/list.do";
	}
	
	@RequestMapping("/deletes")
	public String deletes(String ids){
		String[] blogIds = ids.split(",");
		for(String id : blogIds){
			blogService.delete(Integer.parseInt(id));
		}
		return "redirect:/blog/list.do";
	}
}

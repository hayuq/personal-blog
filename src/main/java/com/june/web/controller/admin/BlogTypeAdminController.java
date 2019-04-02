package com.june.web.controller.admin;

import static com.june.vo.Response.success;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.june.model.BlogType;
import com.june.service.BlogService;
import com.june.service.BlogTypeService;
import com.june.util.Constants;
import com.june.util.PageUtils;
import com.june.vo.PageBean;
import com.june.vo.Response;
/**
 * 博客类别Controller
 */
@Controller
@RequestMapping("/blogType")
public class BlogTypeAdminController {

	@Resource
	private BlogTypeService blogTypeService;
	
	@Resource
	private BlogService blogService;

	@GetMapping("/list")
	public String list(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = Constants.BACK_PAGE_SIZE + 3 + "") Integer pageSize,
			Model model, HttpServletRequest request) {
		int totalCount = blogTypeService.getCount();
		PageBean pageBean = new PageBean(totalCount, page, pageSize);
		Map<String, Object> params = new HashMap<>(2);
		params.put("start", pageBean.getStart());
		params.put("size", pageSize);
		List<BlogType> blogTypeList = blogTypeService.getTypeList(params);
		blogTypeList.forEach(blogType -> {
			Map<String, Object> types = new HashMap<>(1);
			types.put("typeId", blogType.getTypeId());
			Integer blogCount = blogService.getCount(types);
			blogType.setBlogCount(blogCount);
		});
		model.addAttribute("pagination", pageBean);

		String targetUrl = request.getContextPath() + "/blogType/list.do";
		String pageCode = PageUtils.genPagination(targetUrl, pageBean, "");
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("entry", params);
		model.addAttribute("blogTypeList", blogTypeList);
		return "blogType/list";
	}

	@GetMapping("/toAdd")
	public String toAdd() {
		return "blogType/add";
	}

	@GetMapping("/toUpdate")
	public String toUpdate(@RequestParam Integer id,Model model) {
		model.addAttribute("blogType", blogTypeService.findById(id));
		return "blogType/update";
	}

	@PostMapping("/add")
	public void add(@RequestParam BlogType blogType, Model model) {
		boolean success = blogTypeService.add(blogType);
		model.addAttribute("msg", success ? "保存成功" : "保存失败");
	}
	
	@PostMapping("/update")
	public void update(@RequestParam BlogType blogType, Model model) {
		boolean success = blogTypeService.update(blogType);
		model.addAttribute("msg", success ? "保存成功" : "保存失败");
	}
	
	@PostMapping("/search")
	public @ResponseBody Response search(@RequestParam Integer id) {
		Map<String, Object> map = new HashMap<>(1);
		map.put("typeId", id);
		return Response.success("count", blogService.getCount(map));
	}

	//只删除类别
	@PostMapping("/delete")
	public @ResponseBody Response delete(@RequestParam Integer id) {
		blogTypeService.delete(id);
		return success();
	}

	//删除的同时将相关博客的类别置为默认分类
	@PostMapping("/batch_delete")
	public @ResponseBody Response batchDelete(@RequestParam Integer id) {
		blogTypeService.batchDelete(id);
		return success();
	}
}

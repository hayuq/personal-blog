package com.june.web.controller.front;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.june.model.Blog;
import com.june.model.PageBean;
import com.june.service.BlogService;
import com.june.service.BlogTypeService;
import com.june.service.CommentService;
import com.june.util.Constants;
import com.june.util.Objects;
import com.june.util.PageUtils;
import com.june.util.StringUtils;

/**
 * 博客Controller
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;
	
	@Resource
	private CommentService commentService;

	@Resource
	private BlogTypeService blogTypeService;
	
	/**
	 * 获取博客详细信息
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/articles/{id}")
	public String detail(@PathVariable Integer id, Model model, HttpServletRequest request){
		Blog blog = blogService.findById(id);
		if (Objects.isNull(blog)) {
			return "blog/detail";
		}
		// 文章浏览数加一
		blog = blogService.increaseReading(id);
		String desc = StringUtils.escapeHtml(blog.getSummary());
		model.addAttribute("blog", blog);
		model.addAttribute("description", desc.length() > 100 ? desc.substring(0, 100) : desc);
		String keyword = blog.getKeyword();
		if(!StringUtils.isEmpty(keyword)){
			String[] keywords = keyword.split("\\s+");
			model.addAttribute("keywords", Arrays.asList(keywords));
			model.addAttribute("pageKeywords", String.join(",", keywords));
		}
		Map<String,Object> map = new HashMap<String,Object>(2);
		map.put("blogId", id);
		map.put("isPass", 1);
		//获取评论列表
		model.addAttribute("commentList", commentService.getCommentList(map));
		model.addAttribute("pageCode", this.genLastandNextBlogCode(blogService.getLastBlog(id),
				blogService.getNextBlog(id),request.getContextPath()));
		return "blog/detail";
	}

	/**
	 * 获取分类博客列表
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String typeOrMonth(@RequestParam(value="cat",required=false) Integer typeId,
			@RequestParam(required = false) String month,
			@RequestParam(defaultValue = "1") Integer page, Model model,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put("typeId", typeId);
		map.put("releaseDate", month);
		int totalCount = blogService.getCount(map);
		int pageSize = Constants.FRONT_PAGE_SIZE;
		PageBean pageBean = new PageBean(totalCount, page, pageSize);
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		List<Blog> blogList = blogService.getBlogList(map);
		//去除摘要中的html标签，防止浏览器解析
		blogList.forEach(blog -> blog.setSummary(StringUtils.escapeHtml(blog.getSummary())));
		model.addAttribute("blogList", blogList);

		// 分页参数
		StringBuffer param = new StringBuffer();
		param.append(typeId == null ? "" : "cat=" + typeId);
		param.append(StringUtils.isEmpty(month) ? "" : "month=" + month);

		String pageCode = PageUtils.genPagination(request.getContextPath() + "/blog.shtml", 
				pageBean, param.toString());
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("totalCount", totalCount);
		if (typeId != null) {			
			String typeName = blogTypeService.findById(typeId).getTypeName();
			model.addAttribute("title", "文章分类 - " + typeName);
			model.addAttribute("pageTitle", typeName + " - 文章分类 - Promising的博客" );
		}
		if (StringUtils.isNotEmpty(month)) {
			month = String.format("%s年%s月", month.substring(0, 4), month.substring(4));
			model.addAttribute("title", "文章存档 - " + month);
			model.addAttribute("pageTitle", month + " - 文章存档 - Promising的博客" );
		}
		return "blog/list";
	}
	
	/**
	 * 获取下一篇博客和下一篇博客分页代码
	 * @param lastBlog
	 * @param nextBlog
	 * @return
	 */
	private String genLastandNextBlogCode(Blog lastBlog, Blog nextBlog, String projectContext) {
		StringBuilder pageCode = new StringBuilder();
		if (lastBlog == null || lastBlog.getId() == null) {
			pageCode.append("<p>上一篇：没有了</p>");
		} else {
			pageCode.append("<p>上一篇：<a href='" + projectContext + "/blog/articles/" 
					+ lastBlog.getId() + ".shtml'>" + lastBlog.getTitle() + "</a></p>");
		}
		if (nextBlog == null || nextBlog.getId() == null) {
			pageCode.append("<p>下一篇：没有了</p>");
		} else {
			pageCode.append("<p>下一篇：<a href='" + projectContext + "/blog/articles/" 
					+ nextBlog.getId() + ".shtml'>" + nextBlog.getTitle() + "</a></p>");
		}
		return pageCode.toString();
	}
	
}

package com.xjc.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opensymphony.oscache.util.StringUtil;
import com.xjc.lucene.BlogIndex;
import com.xjc.model.Blog;
import com.xjc.model.PageBean;
import com.xjc.service.BlogService;
import com.xjc.service.BlogTypeService;
import com.xjc.service.CommentService;
import com.xjc.util.PageUtils;

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
	
	private BlogIndex blogIndex = new BlogIndex();

	@RequestMapping("/list")
	public String list(String page, String firstDate, String secondDate, Integer typeId, String title, Model model,
			HttpServletRequest request) {

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

		StringBuffer param = new StringBuffer(); // 分页查询参数
		param.append(StringUtil.isEmpty(title) ? "" : "title=" + title);
		param.append(StringUtil.isEmpty(firstDate) ? "" : "&firstDate=" + firstDate);
		param.append(StringUtil.isEmpty(secondDate) ? "" : "&secondDate=" + secondDate);
		param.append(typeId == null ? "" : "&typeId=" + typeId);

		String targetUrl = request.getContextPath() + "blog/list.do";
		String pageCode = PageUtils.genPagination(targetUrl, totalCount, currentPage, pageSize, param.toString());
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("entry", map);
		model.addAttribute("blogList", blogList);
		return "blog/list";
	}

	@RequestMapping("/toAdd")
	public String toAdd(Model model) {
		model.addAttribute("blogTypeList", blogTypeService.getTypeList());
		return "blog/add";
	}

	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id, Model model) {
		model.addAttribute("blogTypeList", blogTypeService.getTypeList());
		model.addAttribute("blog", blogService.findById(id));
		return "blog/update";
	}

	@RequestMapping("/add")
	public String add(Blog blog, @RequestParam(value = "img", required = false) MultipartFile file,
			Model model,HttpServletRequest request) throws IOException {

		// 获取原始文件名
		String fileName = file.getOriginalFilename();
		int index = fileName.indexOf(".");
		String imageUrl = null;
		if(index != -1){
			//生成新文件名
			imageUrl = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + System.currentTimeMillis()
				+ fileName.substring(index);
			handleFileUpload(file, imageUrl,request);
			blog.setImage(imageUrl);
		}
		String content = blog.getContent();
		if (content.length() > 300)
			blog.setSummary(content.substring(0, 300));
		else
			blog.setSummary(content);
		int result = blogService.add(blog);
		//添加博客索引
		blogIndex.createIndex(blog);
		if (result > 0)	
			model.addAttribute("msg", "保存成功");
		else
			model.addAttribute("msg", "保存失败");
		return null;
	}

	@RequestMapping("/update")
	public String update(Blog blog, @RequestParam(value = "img", required = false) MultipartFile file,
			Model model,HttpServletRequest request) throws IOException{

		// 获取原始文件名
		String fileName = file.getOriginalFilename();
		int index = fileName.indexOf(".");
		String imageUrl = null;
		if(index != -1){
			//生成新文件名
			imageUrl = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + System.currentTimeMillis()
				+ fileName.substring(index);
			handleFileUpload(file,imageUrl,request);
			blog.setImage(imageUrl);
		}
		String content = blog.getContent();
		if (content.length() > 300)
			blog.setSummary(content.substring(0, 300));
		else
			blog.setSummary(content);
		int result = blogService.update(blog);
		//更新博客索引
		blogIndex.updateIndex(blog);
		if (result > 0)	
			model.addAttribute("msg", "保存成功");
		else
			model.addAttribute("msg", "保存失败");
		return null;
	}

	@RequestMapping("/delete")
	public String delete(Integer id) throws IOException {
		blogService.delete(id);
		blogIndex.deleteIndex(String.valueOf(id));
		return "redirect:/blog/list.do";
	}

	@RequestMapping("/deletes")
	public String deletes(String ids) throws IOException {
		String[] blogIds = ids.split(",");
		for (String id : blogIds) {
			blogService.delete(Integer.parseInt(id));
			blogIndex.deleteIndex(id);
		}
		return "redirect:/blog/list.do";
	}
	
	private void handleFileUpload(MultipartFile file,String imageUrl,HttpServletRequest request) {
		InputStream is;
		try {
			// 获取输入流
			is = file.getInputStream();
			String filePath = request.getServletContext().getRealPath("images/cover/") + imageUrl;
			File dir = new File(filePath.substring(0, filePath.lastIndexOf("/")));
			//判断上传目录是否存在
			if (!dir.exists()) {
				dir.mkdir();
			}
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buffer = new byte[1024];
			int len = 0;
			// 读取输入流中的内容
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

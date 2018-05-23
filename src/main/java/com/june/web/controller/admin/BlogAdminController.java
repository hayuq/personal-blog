package com.june.web.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.june.lucene.BlogIndex;
import com.june.model.Blog;
import com.june.model.BlogType;
import com.june.model.PageBean;
import com.june.service.BlogService;
import com.june.service.BlogTypeService;
import com.june.service.CommentService;
import com.june.util.Constants;
import com.june.util.DateUtils;
import com.june.util.PageUtils;
import com.june.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 博客Controller
 */
@Controller
@RequestMapping("/blog")
public @Slf4j class BlogAdminController {

	@Resource
	private BlogService blogService;

	@Resource
	private BlogTypeService blogTypeService;

	@Resource
	private CommentService commentService;
	
	@Resource
	private BlogIndex blogIndex;
	
	@RequestMapping("/list")
	public String list(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE - 1 + "") Integer pageSize,
			String firstDate, String secondDate, Integer typeId, String title, Model model,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>(6);
		map.put("typeId", typeId);
		map.put("title", title);
		map.put("firstDate", firstDate);
		map.put("secondDate", secondDate);
		int totalCount = blogService.getCount(map);
		PageBean pageBean = new PageBean(totalCount, page, pageSize);
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		model.addAttribute("pagination", pageBean);
		
		List<BlogType> blogTypeList = blogTypeService.getTypeList();
		model.addAttribute("blogTypeList",blogTypeList);

		StringBuilder param = new StringBuilder(); // 分页查询参数
		param.append(StringUtils.isEmpty(title) ? "" : "title=" + title);
		param.append(StringUtils.isEmpty(firstDate) ? "" : "&firstDate=" + firstDate);
		param.append(StringUtils.isEmpty(secondDate) ? "" : "&secondDate=" + secondDate);
		param.append(typeId == null ? "" : "&typeId=" + typeId);

		String pageCode = PageUtils.genPagination(request.getContextPath() + "/blog/list.do",
				pageBean, param.toString());
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("entry", map);
		model.addAttribute("blogList", blogService.getBlogList(map));
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
		Blog blog = blogService.findById(id);
		model.addAttribute("blog", blog);
		BlogType blogType = blog.getBlogType();
		if(blogType != null){
			model.addAttribute("typeId", blogType.getTypeId());
		}
		return "blog/update";
	}

	@RequestMapping("/add")
	public void add(Blog blog, @RequestParam(value = "img") MultipartFile file,
			Model model) throws Exception {

		// 获取原始文件名
		String fileName = file.getOriginalFilename();
		int index = fileName.indexOf(".");
		String imageUrl = null;
		String imagePath = DateUtils.getTimeStrForImage();
		if (index != -1) {
			//生成新文件名
			imageUrl = imagePath + fileName.substring(index);
			log.info("add {}", imagePath);
			handleFileUpload(file, imageUrl);
			blog.setImage(imageUrl);
		}
		// 添加博客及索引
		int result = blogService.add(blog);
		model.addAttribute("msg", result > 0 ? "保存成功" : "保存失败");
	}

	@RequestMapping("/update")
	public void update(Blog blog, @RequestParam(value = "img", required=false) MultipartFile file,
			Model model) throws Exception {

		if (file != null) {	 //上传图片
			// 获取原始文件名
			String fileName = file.getOriginalFilename();
			int index = fileName.indexOf(".");
			String imageUrl = null;
			String imagePath = DateUtils.getTimeStrForImage();
			if(index != -1){
				//生成新文件名
				imageUrl = imagePath + fileName.substring(index);
				log.info("update {}", imagePath);
				handleFileUpload(file,imageUrl);
				blog.setImage(imageUrl);
			}
		}
		//更新博客及索引
		int result = blogService.update(blog);
		model.addAttribute("msg", result > 0 ? "保存成功" : "保存失败");
	}

	@RequestMapping("/delete")
	public String delete(Integer id) throws IOException {
		// 删除博客、索引及评论
		blogService.delete(id);
		return "redirect:/blog/list.do";
	}

	@RequestMapping("/deletes")
	public String deletes(String ids) throws IOException {
		String[] idArr = org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids);
		int len = idArr.length;
		Integer[] blogIds = new Integer[len];
		for (int i = 0; i < len; i++) {
			blogIds[i] = Integer.parseInt(idArr[i]);
		}
		blogService.batchDelete(blogIds);
		return "redirect:/blog/list.do";
	}
	
	private void handleFileUpload(MultipartFile file, String imageUrl) {
		try (InputStream is = file.getInputStream()) {
			// 获取输入流
			String filePath = Constants.COVER_DIR + imageUrl;
			File dir = new File(filePath.substring(0, filePath.lastIndexOf("/")));
			//判断上传目录是否存在
			if (!dir.exists()) {
				dir.mkdir();
			}
			try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
				byte[] buffer = new byte[1024];
				int len = 0;
				// 读取输入流中的内容
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
			}
		} catch (Exception e) {
			log.error("图片上传失败", e);
		}
	}
}

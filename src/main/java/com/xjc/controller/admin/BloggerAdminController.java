package com.xjc.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xjc.model.Blogger;
import com.xjc.service.BloggerService;
import com.xjc.service.CommentService;
import com.xjc.util.MD5EncodeUtils;
import com.xjc.util.ResponseUtils;

@Controller
@RequestMapping("/blogger")
public class BloggerAdminController {

	@Resource
	CommentService commentService;

	@Resource
	BloggerService bloggerService;

	@RequestMapping("/toModifyInfo")
	public String toModifyInfo(Model model) {
		model.addAttribute("blogger", bloggerService.findById(1));
		return "blogger/modifyInfo";
	}

	@RequestMapping("/toModifyPassword")
	public String toModifyPassword() {
		return "blogger/modifyPassword";
	}

	@RequestMapping("/modifyInfo")
	public String modifyInfo(Blogger blogger, @RequestParam(value = "img", required = false) MultipartFile file,
			Model model, HttpServletResponse response) {

		// 获取原始文件名
		String fileName = file.getOriginalFilename();
		int index = fileName.indexOf(".");
		String imageUrl = null;
		if (index != -1) {
			// 生成新文件名
			imageUrl = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + System.currentTimeMillis()
					+ fileName.substring(index);
			handleFileUpload(file, imageUrl);
			blogger.setImageUrl(imageUrl);
		}
		int result = bloggerService.update(blogger);
		if (result > 0) {
			ResponseUtils.writeHtml(response, "<script>alert('保存成功')</script>");
		}
		else{
			ResponseUtils.writeHtml(response, "<script>alert('保存失败')</script>");
		}
		return null;
	}

	@RequestMapping("/modifyPassword")
	public String modifyPassword(String newpwd, String oldpwd, String repwd, HttpServletResponse response,
			HttpServletRequest request) {

		Blogger blogger = bloggerService.findById(1);
		System.out.println(blogger.getUserName() + ": " + blogger.getPassword());
		if (!blogger.getPassword().equals(MD5EncodeUtils.encrypt(oldpwd, "xjc"))) {
			ResponseUtils.writeText(response, "原密码输入不正确");
			return null;
		}
		if (!newpwd.equals(repwd)) {
			ResponseUtils.writeText(response, "两次密码输入不一致");
			return null;
		}
		blogger.setPassword(MD5EncodeUtils.encrypt(newpwd, "xjc"));
		bloggerService.update(blogger);
		ResponseUtils.writeText(response, "修改成功");
		return null;
	}

	private void handleFileUpload(MultipartFile file, String imageUrl) {
		InputStream is;
		try {
			// 获取输入流
			is = file.getInputStream();
			String filePath = "C:/uploadFiles/avatar/" + imageUrl;
			File dir = new File(filePath.substring(0, filePath.lastIndexOf("/")));
			// 判断上传目录是否存在
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

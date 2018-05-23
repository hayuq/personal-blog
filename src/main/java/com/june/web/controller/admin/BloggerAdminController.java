package com.june.web.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.june.model.Blogger;
import com.june.service.BloggerService;
import com.june.service.CommentService;
import com.june.util.Constants;
import com.june.util.DateUtils;
import com.june.util.ResponseUtils;
import com.june.util.ShiroUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/blogger")
public @Slf4j class BloggerAdminController {

	@Resource
	private CommentService commentService;

	@Resource
	private BloggerService bloggerService;

	@RequestMapping("/toModifyInfo")
	public String toModifyInfo(Model model) {
		model.addAttribute("blogger", bloggerService.find());
		return "blogger/modifyInfo";
	}

	@RequestMapping("/toModifyPassword")
	public String toModifyPassword() {
		return "blogger/modifyPassword";
	}

	@RequestMapping("/modifyInfo")
	public void modifyInfo(Blogger blogger,
			@RequestParam(value = "img", required = false) MultipartFile file,
			Model model) throws Exception {
		
		if (file != null) {	 //上传图片
			// 获取原始文件名
			String fileName = file.getOriginalFilename();
			int index = fileName.indexOf(".");
			String imageUrl = null;
			if (index != -1) {
				// 生成新文件名
				imageUrl = DateUtils.getTimeStrForImage() + fileName.substring(index);
				handleFileUpload(file, imageUrl);
				blogger.setImageUrl(imageUrl);
			}
		}
		int result = bloggerService.update(blogger);
		model.addAttribute("msg", result > 0 ? "保存成功" : "保存失败");
	}

	@RequestMapping("/modifyPassword")
	public void modifyPassword(String newpwd, String oldpwd, String repwd, HttpServletResponse response,
			HttpServletRequest request) {

		Blogger blogger = bloggerService.find();
		if (!blogger.getPassword().equals(ShiroUtils.encryptPassword(oldpwd))) {
			ResponseUtils.writeText(response, "原密码输入不正确");
			return;
		}
		if (!newpwd.equals(repwd)) {
			ResponseUtils.writeText(response, "两次密码输入不一致");
			return;
		}
		blogger.setPassword(ShiroUtils.encryptPassword(newpwd));
		bloggerService.update(blogger);
		ResponseUtils.writeText(response, "修改成功");
	}

	private void handleFileUpload(MultipartFile file,String imageUrl) {
		try (InputStream is = file.getInputStream()) {
			// 获取输入流
			String filePath = Constants.AVATAR_DIR + imageUrl;
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

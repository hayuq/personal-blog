package com.june.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.june.dao.BloggerMapper;
import com.june.model.Blogger;
import com.june.service.BloggerService;
import com.june.util.Constants;
import com.june.util.FileUploader;

@Service
public class BloggerServiceImpl implements BloggerService {

	@Resource
	private BloggerMapper bloggerMapper;
	
	@Resource
	private FileUploader fileUploader;
	
	@Override
	public Blogger find() {
		return bloggerMapper.find();
	}

	@Override
	public Blogger findByName(String name) {
		return bloggerMapper.findByName(name);
	}

	@Override
	public boolean update(Blogger blogger, MultipartFile img) {
		if (img != null) {
			// 上传头像
			String imgUrl = fileUploader.uploadImage(img, Constants.AVATAR_DIR);
			if (imgUrl != null) {
				blogger.setImageUrl(imgUrl);	
			}
		}
		return bloggerMapper.updateByPrimaryKeySelective(blogger) > 0;
	}

}

package com.june.service;

import org.springframework.web.multipart.MultipartFile;

import com.june.model.Blogger;

public interface BloggerService {

	Blogger find();
	
	Blogger findByName(String name);
	
	boolean update(Blogger blogger, MultipartFile file);
	
}

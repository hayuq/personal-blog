package com.june.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.web.multipart.MultipartFile;

import com.june.model.Blog;

public interface BlogService {

	Blog findById(Integer id);

	List<Blog> getBlogList(Map<String, Object> map);
	
	Blog getPrevBlog(Integer id);
	
	Blog getNextBlog(Integer id);
	
	Integer getCount(Map<String, Object> map);
	
	List<Blog> getByTypeId(Integer typeId);
	
	List<Blog> getTopReview();
	
	List<Blog> getTopReading();
	
	/**
	 * 添加博客及索引
	 * @param blog
	 * @param file
	 * @return
	 * @throws IOException
	 */
	boolean add(Blog blog, MultipartFile file);
	
	/**
	 * 更新博客及索引
	 * @param blog
	 * @param file
	 * @return
	 * @throws IOException
	 */
	boolean update(Blog blog, MultipartFile file);
	
	List<Blog> getByDate();
	
	/**
	 * 删除博客、索引及该篇博客的评论
	 * @param id
	 * @return
	 */
	void delete(Integer id);

	/**
	 * 浏览量加一
	 * @param id 博客id
	 * @return 
	 */
	Blog increaseReading(Integer id);
	
	/**
	 * 评论数加一
	 * @param id 博客id
	 * @return 
	 */
	void increaseReview(Integer id);
	
	boolean batchDelete(Integer[] ids);

	/**
	 * 根据关键字从lucene全文检索博客
	 * @param q 关键字
	 * @param maxCount 最多的匹配结果数 
	 * @return
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException
	 * @throws IOException
	 */
	List<Blog> search(String q, int maxCount) throws ParseException, InvalidTokenOffsetsException, IOException;
 }

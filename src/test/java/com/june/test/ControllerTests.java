package com.june.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.june.dao.BlogMapper;
import com.june.lucene.BlogIndex;
import com.june.model.Blog;
import com.june.util.DateUtils;
import com.june.util.ShiroUtils;

@WebAppConfiguration //注入WebApplicationContext对象
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring*.xml")
public class ControllerTests {

	@Resource
	private WebApplicationContext applicationContext;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
				.addFilter((Filter) applicationContext.getBean("shiroFilter"))
				.build();
	}
	
	@Test
	public void index() throws Exception {
		mockMvc.perform(get("/"))
			   .andDo(print());
	}
	
	@Test
	public void vCode() throws Exception {
		mockMvc.perform(get("/vCode.jpg"))
			   .andDo(print());
	}
	
	@Test
	public void login() throws Exception {
		mockMvc.perform(get("/admin/login.do"))
			   .andDo(print());
	}

	@Test
	public void logout() throws Exception {
		mockMvc.perform(get("/admin/logout.do"))
			   .andDo(print());
	}
	
	@Test
	public void userLogin() throws Exception {
		mockMvc.perform(post("/admin/userLogin.do")
						  .param("username", "admin")
						  .param("password", "1233")
				)
			   .andDo(print());
	}
	
	@Test
	public void about() throws Exception {
		mockMvc.perform(get("/about.shtml"))
			   .andDo(print());
	}
	
	@Test
	public void createBlogIndexes() {
		BlogMapper blogMapper = applicationContext.getBean(BlogMapper.class);
		BlogIndex blogIndex = applicationContext.getBean(BlogIndex.class);
		List<Blog> blogs = blogMapper.getBlogList(new HashMap<>());
		System.out.println("开始更新博客索引...");
		try {
			for (Blog blog : blogs) {
				blog.setReleaseDateStr(DateUtils.formatDate(blog.getReleaseDate(), "yyyy-MM-dd HH:mm"));
				blogIndex.updateIndex(blog);
			}
			System.out.println("索引更新完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(ShiroUtils.encryptPassword("blog180"));
	}
	
}

package com.xjc.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.xjc.model.BlogType;
import com.xjc.service.BlogService;
import com.xjc.service.BlogTypeService;
import com.xjc.service.BloggerService;
import com.xjc.service.LinkService;

/**
 * 自定义Listener，在web容器初始化时就查询出博客、博客类别、博主、以及链接信息
 */
@Component
public class MyListener implements ServletContextListener,ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
    public MyListener() {
    }

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.applicationContext = applicationContext;
	}
    
    public void contextDestroyed(ServletContextEvent arg0)  { 
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
    	
		/*//获取ServletContext对象
		ServletContext application = arg0.getServletContext();
		
		//从Spring的IOC容器中获取Bean
		BlogService blogService = (BlogService) applicationContext.getBean("blogService");
		BlogTypeService blogTypeService = (BlogTypeService) applicationContext.getBean("blogTypeService");
		BloggerService bloggerService = (BloggerService)applicationContext.getBean("bloggerService");
		LinkService linkService = (LinkService) applicationContext.getBean("linkService");
		
		List<BlogType> blogTypeList = blogTypeService.getTypeList();
		for(BlogType blogType : blogTypeList){
			blogType.setBlogCount(blogService.getByTypeId(blogType.getTypeId()).size());
		}		
		application.setAttribute("blogTypeList", blogTypeList);
		application.setAttribute("dateRankList", blogService.getByDate());
		application.setAttribute("readingRankList", blogService.getTopReading());
		application.setAttribute("reviewRankList", blogService.getTopReview());
		application.setAttribute("blogger", bloggerService.findById(1));
		application.setAttribute("linkList", linkService.getLinkList());*/
    }

}

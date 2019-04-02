package com.june.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.context.WebApplicationContext;

import com.june.dao.BlogMapper;
import com.june.model.Blog;
import com.june.search.BlogIndexManager;
import com.june.util.CommonUtils;
import com.june.util.Constants;
import com.june.util.DateUtils;

//@WebAppConfiguration // 注入WebApplicationContext对象
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:spring/spring-core.xml", "classpath:spring/springmvc-admin.xml",
//        "classpath:spring/springmvc-front.xml" })
public class ControllerTests {

    @Resource
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @Resource
    private ServletContext servletContext;

//    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Ignore
    @Test
    public void index() throws Exception {
        mockMvc.perform(get("/index")).andDo(print());
    }

    @Ignore
    @Test
    public void login() throws Exception {
        mockMvc.perform(get("/admin/login.do")).andDo(print());
    }

    @Ignore
    @Test
    public void addOrUpdateBlog() throws Exception {
        // 模拟图片上传
        BufferedImage image = new BufferedImage(20, 30, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", os);
        mockMvc.perform(
                // fileUpload("/blog/add")
                fileUpload("/blog/update").file(new MockMultipartFile("img", os.toByteArray())).header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE).param("id", "64").param("title", "Test Title").param("content", "随便写点什么都行").param("releaseDateStr", DateUtils.getCurrentDateStr())).andDo(print());
    }

    @Ignore
    @Test
    public void userLogin() throws Exception {
        mockMvc.perform(post("/admin/userLogin.do").param("username", "admin").param("password", "1233").param("csrfToken", "dasas").cookie(new Cookie(Constants.COOKIE_NAME, CommonUtils.getUUID()))).andDo(print());
    }

    @Ignore
    @Test
    public void about() throws Exception {
        mockMvc.perform(get("/about.shtml")).andDo(print());
    }

    @Ignore
    @Test
    public void createBlogIndexes() {
        BlogMapper blogMapper = applicationContext.getBean(BlogMapper.class);
        BlogIndexManager blogIndex = applicationContext.getBean(BlogIndexManager.class);
        List<Blog> blogs = blogMapper.getBlogList(null);
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

//    @Ignore
    @Test
    public void pageTest() throws Exception {
//        mockMvc.perform(get("/search.shtml?q=跨域&page=sd")).andDo(print());
        ServletRequest.class.getMethod("startAsync");
    }

    @Ignore
    @Test
    public void deletes() throws Exception {
        mockMvc.perform(get("/blog/deletes.do").header(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).content(Arrays.asList(1000, 2000, 3000).toString())).andDo(print());
    }

}

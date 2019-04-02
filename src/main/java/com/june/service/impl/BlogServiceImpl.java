package com.june.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.june.dao.BlogMapper;
import com.june.dao.CommentMapper;
import com.june.model.Blog;
import com.june.search.BlogIndexManager;
import com.june.service.BlogService;
import com.june.util.Constants;
import com.june.util.DateUtils;
import com.june.util.FileUploader;
import com.june.util.ObjectUtils;
import com.june.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
public @Slf4j class BlogServiceImpl implements BlogService {

    private static final Pattern SPECIAL_CHAR_REGEX = Pattern
        .compile("[+\\-&|!(){}\\[\\]^\"~*?:(\\)(AND)(OR)(NOT)]");

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private BlogIndexManager blogIndexManager;

    @Resource
    private FileUploader fileUploader;

    @Override
    public Blog findById(Integer id) {
        return blogMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Blog> getBlogList(Map<String, Object> map) {
        return wrap(blogMapper.getBlogList(map));
    }

    @Override
    public Blog getPrevBlog(Integer id) {
        return blogMapper.getLastBlog(id);
    }

    @Override
    public Blog getNextBlog(Integer id) {
        return blogMapper.getNextBlog(id);
    }

    @Override
    public Integer getCount(Map<String, Object> map) {
        return blogMapper.getCount(map);
    }

    @Override
    public List<Blog> getByTypeId(Integer typeId) {
        return wrap(blogMapper.getByTypeId(typeId));
    }

    @Override
    public List<Blog> getTopReading() {
        return wrap(blogMapper.getTopReading());
    }

    @Override
    public List<Blog> getTopReview() {
        return wrap(blogMapper.getTopReview());
    }

    @Override
    public List<Blog> getByDate() {
        return wrap(blogMapper.getByDate());
    }

    private List<Blog> wrap(List<Blog> blogs) {
        if (CollectionUtils.isEmpty(blogs)) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(blogs);
    }

    @Override
    public boolean add(Blog blog, MultipartFile img) {
        uploadBlogImage(blog, img);
        boolean isSuccess = blogMapper.insertSelective(blog) > 0;
        if (isSuccess) {
            // SubmitLinkUtils.createLink(ResourceType.ARTICLE, blog.getId());
            blogIndexManager.createIndex(blog);
        }
        return isSuccess;
    }

    @Override
    public boolean update(Blog blog, MultipartFile img) {
        uploadBlogImage(blog, img);
        boolean isSuccess = blogMapper.updateByPrimaryKeySelective(blog) > 0;
        if (isSuccess) {
            // SubmitLinkUtils.updateLink(ResourceType.ARTICLE, blog.getId());
            blogIndexManager.updateIndex(blog);
        }
        return isSuccess;
    }

    /**
     * 上传博客封面图片
     * @param blog
     * @param img
     */
    private void uploadBlogImage(Blog blog, MultipartFile img) {
        if (img == null) {
            return;
        }
        String imgUrl = fileUploader.uploadImage(img, Constants.COVER_DIR);
        if (imgUrl != null) {
            blog.setImage(imgUrl);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog increaseReading(Integer id) {
        blogMapper.increaseReading(id);
        return blogMapper.selectByPrimaryKey(id);
    }

    @Override
    public void increaseReview(Integer id) {
        blogMapper.increaseReview(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer id) {
        boolean isSuccess = blogMapper.deleteByPrimaryKey(id) > 0;
        if (isSuccess) {
            // SubmitLinkUtils.createLink(ResourceType.ARTICLE, id);
            commentMapper.deleteByBlogIds(id);
            blogIndexManager.deleteIndex(String.valueOf(id));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchDelete(Integer[] ids) {
        boolean isSuccess = blogMapper.batchDelete(ids) > 0;
        if (isSuccess) {
            // SubmitLinkUtils.deleteLink(ResourceType.ARTICLE, ids);
            commentMapper.deleteByBlogIds(ids);
            List<String> blogIds = Arrays.stream(ids).map(id -> String.valueOf(id))
                .collect(Collectors.toList());
            blogIndexManager.deleteIndexes(blogIds);
        }
        return isSuccess;
    }

    /**
     * 根据关键字索引出相关博客
     * @param q 查询条件
     * @param maxCount 最多的匹配结果数
     * @return
     * @throws ParseException
     * @throws InvalidTokenOffsetsException
     * @throws IOException
     */
    @Override
    public List<Blog> search(String q, int maxCount) throws ParseException, InvalidTokenOffsetsException,
                                                     IOException {

        // 转义特殊字符，防止lucene报异常
        String kwd = QueryParser.escape(q);
        BlogServiceImpl.log.info("search kwd: {}", kwd);

        // 转义lucene关键词，防止异常
        StringBuffer kwdBuffer = new StringBuffer();
        Matcher matcher = BlogServiceImpl.SPECIAL_CHAR_REGEX.matcher(kwd);
        if (matcher != null) {
            while (matcher.find()) {
                matcher.appendReplacement(kwdBuffer, "\\\\" + matcher.group());
            }
            matcher.appendTail(kwdBuffer);
        }
        kwd = kwdBuffer.toString();
        BlogServiceImpl.log.info("search kwd: {}", kwd);

        Analyzer analyzer = blogIndexManager.getAnalyzer();
        Directory directory = blogIndexManager.getDirectory();

        // 获取IndexReader对象
        try (IndexReader reader = DirectoryReader.open(directory)) {

            // 1、构造搜索器IndexSearcher
            IndexSearcher searcher = new IndexSearcher(reader);

            // 2、构造搜索条件
            QueryParser titleParser = new QueryParser(BlogIndexManager.FIELD_TITLE, analyzer);
            QueryParser contentParser = new QueryParser(BlogIndexManager.FIELD_CONTENT, analyzer);

            // 使用BooleanQuery实现多个Query条件的组合查询
            Query query = new BooleanQuery.Builder().add(titleParser.parse(kwd), BooleanClause.Occur.SHOULD)
                .add(contentParser.parse(kwd), BooleanClause.Occur.SHOULD).build();

            // 3、获取查询结果
            TopScoreDocCollector collector = TopScoreDocCollector.create(maxCount);
            searcher.search(query, collector);
            TopDocs topDocs = collector.topDocs();
            if (ObjectUtils.isNull(topDocs) || ArrayUtils.isEmpty(topDocs.scoreDocs)) {
                return Collections.emptyList();
            }

            // 4、指定高亮关键词的样式，默认是<b>..</b>
            QueryScorer scorer = new QueryScorer(query);
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>",
                                                                              "</font>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));

            return buildResults(analyzer, searcher, topDocs.scoreDocs, highlighter);
        }
    }

    /**
     * 拼装查询到的博客列表，并为匹配到的关键词增加高亮效果
     * @param analyzer 分词器
     * @param searcher 搜索器对象
     * @param hits 匹配的结果
     * @param highlighter 关键词高亮样式
     * @return
     * @throws IOException
     * @throws InvalidTokenOffsetsException
     */
    private List<Blog> buildResults(Analyzer analyzer, IndexSearcher searcher, ScoreDoc[] hits,
                                    Highlighter highlighter) throws IOException,
                                                             InvalidTokenOffsetsException {
        List<Blog> blogs = new ArrayList<>(hits.length);
        for (ScoreDoc hit : hits) {
            Document doc = searcher.doc(hit.doc);
            String idStr = doc.get(BlogIndexManager.FIELD_ID);
            Integer id = StringUtils.isBlank(idStr) ? 0 : Integer.parseInt(idStr);
            Blog blog = this.findById(id);
            if (blog == null) {
                continue;
            }
            String dateStr = doc.get(BlogIndexManager.RELEASE_DATE_STR);
            blog.setReleaseDateStr(StringUtils.isEmpty(dateStr) ? DateUtils.formatDate(blog.getReleaseDate(),
                    "yyyy-MM-dd HH:mm") : dateStr);
            String title = doc.get(BlogIndexManager.FIELD_TITLE);
            if (title != null) {
                TokenStream tokenStream = analyzer.tokenStream(BlogIndexManager.FIELD_TITLE,
                        new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                blog.setTitle(StringUtils.isEmpty(hTitle) ? title : hTitle);
            }
            String content = StringUtils.escapeHtml(doc.get(BlogIndexManager.FIELD_CONTENT));
            if (StringUtils.isNotEmpty(content)) {
                TokenStream tokenStream = analyzer.tokenStream(BlogIndexManager.FIELD_CONTENT,
                        new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                content = StringUtils.isEmpty(hContent) ? content : hContent;
                // 摘要最大长度300，超出部分用省略号代替
                blog.setSummary(org.apache.commons.lang3.StringUtils.abbreviate(content, 303));
            }
            blogs.add(blog);
        }
        return Collections.unmodifiableList(blogs);
    }

}

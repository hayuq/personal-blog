package com.june.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import com.june.model.Blog;
import com.june.service.BlogService;
import com.june.util.Constants;
import com.june.util.DateUtils;
import com.june.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 用于全文检索的博客索引操作类
 */
@Component
public @Slf4j class BlogIndex {
	
	private static final String RELEASE_DATE_STR = "releaseDateStr";
	private static final String FIELD_CONTENT = "content";
	private static final String FIELD_TITLE = "title";
	private static final String FIELD_ID = "id";

	@Resource
	private BlogService blogService;
	
 	private static Analyzer analyzer;

	private static Directory dir; // 存储索引的目录
	
	static {
		try {
			analyzer = new SmartChineseAnalyzer();
			dir = FSDirectory.open(Paths.get(Constants.LUCENE_INDEX_DIR));
		} catch (IOException e) {
			log.error("获取索引目录失败", e);
		}
	}

	/**
	 * 获取IndexWriter对象
	 * @throws IOException
	 */
	private IndexWriter getWriter() throws IOException {
		return new IndexWriter(dir, new IndexWriterConfig(analyzer));
	}

	/**
	 * 创建索引
	 * @param blog 博客对象
	 * @throws IOException
	 */
	public void createIndex(Blog blog) throws IOException {
		try (IndexWriter writer = getWriter()) {
			Document doc = new Document();
			doc.add(new StringField(FIELD_ID, String.valueOf(blog.getId()), Field.Store.YES));

			// 将博客的标题和内容作为索引存储到磁盘文件中
			doc.add(new TextField(FIELD_TITLE, blog.getTitle(), Field.Store.YES));
			doc.add(new TextField(FIELD_CONTENT, blog.getContent(), Field.Store.YES));

			/**
			 * StringField: 只索引不分词 TextField: 既索引又分词
			 */
			doc.add(new StringField(RELEASE_DATE_STR, 
					DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm"), Field.Store.YES));
			writer.addDocument(doc);
			writer.commit();
		}
	}

	/**
	 * 更新索引
	 * @param blog 博客对象
	 * @throws IOException
	 */
	public void updateIndex(Blog blog) throws IOException {
		try (IndexWriter writer = getWriter()) {
			Document doc = new Document();
			String blogId = String.valueOf(blog.getId());
			doc.add(new StringField(FIELD_ID, blogId, Field.Store.YES));

			// 将博客的标题和内容作为索引存储到磁盘文件中
			doc.add(new TextField(FIELD_TITLE, blog.getTitle(), Field.Store.YES));
			doc.add(new TextField(FIELD_CONTENT, blog.getContent(), Field.Store.YES));

			doc.add(new StringField(RELEASE_DATE_STR, blog.getReleaseDateStr(), Field.Store.YES));
			writer.updateDocument(new Term(FIELD_ID, blogId), doc);
			writer.commit();
		}
	}

	/**
	 * 根据博客id删除索引
	 * @param blogId 博客id
	 * @throws IOException
	 */
	public void deleteIndex(String blogId) throws IOException {
		try (IndexWriter writer = getWriter()) {
			writer.deleteDocuments(new Term(FIELD_ID, blogId));
			writer.forceMergeDeletes();
			writer.commit();
		}
	}

	/**
	 * 根据关键字索引出相关博客
	 * @param q 查询条件
	 * @return
	 * @throws Exception
	 */
	public List<Blog> query(String q) throws Exception {

		// 获取IndexReader对象
		IndexReader reader = DirectoryReader.open(dir);

		// 构造搜索器IndexSearcher
		IndexSearcher searcher = new IndexSearcher(reader);

		QueryParser titleParser = new QueryParser(FIELD_TITLE, analyzer);
		QueryParser contentParser = new QueryParser(FIELD_CONTENT, analyzer);
		Query titleQuery = titleParser.parse(q);
		Query contentQuery = contentParser.parse(q);

		// 使用BooleanQuery实现多个Query的组合查询
		Query query = new BooleanQuery.Builder()
				.add(titleQuery, BooleanClause.Occur.SHOULD)
				.add(contentQuery, BooleanClause.Occur.SHOULD)
				.build();
		// 查询结果信息类
		TopScoreDocCollector collector = TopScoreDocCollector.create(100);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		QueryScorer scorer = new QueryScorer(query);
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);

		// 指定高亮显示的样式，默认是<b>..</b>
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);

		List<Blog> blogList = new ArrayList<>(hits.length);
		for (ScoreDoc hit : hits) {
			Document doc = searcher.doc(hit.doc);
			String idStr = doc.get(FIELD_ID);
			Integer id = StringUtils.isBlank(idStr) ? 0 : Integer.parseInt(idStr);
			Blog blog = blogService.findById(id);
			if (blog == null) {
				continue;
			}
			String dateStr = doc.get(RELEASE_DATE_STR);
			blog.setReleaseDateStr(StringUtils.isEmpty(dateStr)
					? DateUtils.formatDate(blog.getReleaseDate(), "yyyy-MM-dd HH:mm") : dateStr);
			String title = doc.get(FIELD_TITLE);
			if (title != null) {
				TokenStream tokenStream = analyzer.tokenStream(FIELD_TITLE, new StringReader(title));
				String hTitle = highlighter.getBestFragment(tokenStream, title);
				blog.setTitle(StringUtils.isEmpty(hTitle) ? title : hTitle);
			}
			String content = StringUtils.escapeHtml(doc.get(FIELD_CONTENT));
			if (content != null) {
				TokenStream tokenStream = analyzer.tokenStream(FIELD_CONTENT, new StringReader(content));
				String hContent = highlighter.getBestFragment(tokenStream, content);
				final int maxLength = 300;
				if (StringUtils.isEmpty(hContent)) {
					blog.setContent(content);
					blog.setSummary(content.length() <= maxLength ? content : content.substring(0, maxLength) + "...");
				} else {
					blog.setContent(hContent);
					blog.setSummary(hContent.length() <= maxLength ? 
							hContent + "..." : hContent.substring(0, maxLength) + "...");
				}
			}
			blogList.add(blog);
		}
		return blogList;
	}
	
}

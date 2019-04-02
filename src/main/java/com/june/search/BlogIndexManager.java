package com.june.search;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import com.june.model.Blog;
import com.june.util.Constants;
import com.june.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 用于全文检索的博客索引操作类，包括索引的创建、更新、删除
 */
@Component
public @Slf4j class BlogIndexManager {
	
	/**
	 * 博客发布时间
	 */
	public static final String RELEASE_DATE_STR = "releaseDateStr";
	
	/**
	 * 博客内容
	 */
	public static final String FIELD_CONTENT = "content";
	
	/**
	 * 博客标题
	 */
	public static final String FIELD_TITLE = "title";
	
	/**
	 * 博客id
	 */
	public static final String FIELD_ID = "id";

 	private static IndexWriter indexWriter;

	private static Directory directory; // 存储索引的目录
	
 	private static Analyzer analyzer = new SmartChineseAnalyzer();

 	static {
 		try {
 			BlogIndexManager.directory = FSDirectory.open(Paths.get(Constants.LUCENE_INDEX_DIR));
 		} catch (IOException e) {
 			log.error("获取索引目录失败", e);
 		}
 	}
	
	public void setAnalyzer(Analyzer analyzer) {
		if (analyzer != null) {
			BlogIndexManager.analyzer = analyzer;
		}
	}
	
	public Analyzer getAnalyzer() {
		return BlogIndexManager.analyzer;
	}
	
	public Directory getDirectory() {
		return BlogIndexManager.directory;
	}

	/**
	 * 创建索引
	 * @param blog 博客对象
	 * @throws IOException
	 */
	public void createIndex(Blog blog) {
		try (IndexWriter writer = getIndexWriter()) {
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
		} catch (IOException e) {
			log.error("索引创建失败", e);
		}
	}

	/**
	 * 更新索引
	 * @param blog 博客对象
	 * @throws IOException
	 */
	public void updateIndex(Blog blog) {
		try (IndexWriter writer = getIndexWriter()) {
			Document doc = new Document();
			String blogId = String.valueOf(blog.getId());
			doc.add(new StringField(FIELD_ID, blogId, Field.Store.YES));

			// 将博客的标题和内容作为索引存储到磁盘文件中
			doc.add(new TextField(FIELD_TITLE, blog.getTitle(), Field.Store.YES));
			doc.add(new TextField(FIELD_CONTENT, blog.getContent(), Field.Store.YES));

			doc.add(new StringField(RELEASE_DATE_STR, blog.getReleaseDateStr(), Field.Store.YES));
			writer.updateDocument(new Term(FIELD_ID, blogId), doc);
			writer.commit();
		} catch (IOException e) {
			log.error("索引更新失败", e);
		}
	}
	
	/**
	 * 批量删除博客索引
	 * @param blogIds 博客id列表
	 * @throws IOException
	 */
	public void deleteIndexes(List<String> blogIds) {
		try (IndexWriter writer = getIndexWriter()) {
			int size = blogIds.size();
			Term[] terms = new Term[size];
			for (int i = 0; i < size; i++) {
				terms[i] = new Term(FIELD_ID, blogIds.get(i));
			}
			writer.deleteDocuments(terms);
			writer.forceMergeDeletes();
			writer.commit();
		} catch (IOException e) {
			log.error("索引删除失败", e);
		}
	}

	/**
	 * 根据博客id删除索引
	 * @param blogId 博客id
	 * @throws IOException
	 */
	public void deleteIndex(String blogId) {
		try (IndexWriter writer = getIndexWriter()) {
			writer.deleteDocuments(new Term(FIELD_ID, blogId));
			writer.forceMergeDeletes();
			writer.commit();
		} catch (IOException e) {
			log.error("索引删除失败", e);
		}
	}

	/**
	 * 获取IndexWriter对象
	 * @throws IOException
	 */
	private IndexWriter getIndexWriter() {
		if (indexWriter == null || !indexWriter.isOpen()) {
			try {
				log.info("IndexWriter未打开或已被关闭，重新初始化...");
				indexWriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));
			} catch (IOException e) {
				log.error("获取IndexWriter失败", e);
			}
		}
		return indexWriter;
	}
	
}

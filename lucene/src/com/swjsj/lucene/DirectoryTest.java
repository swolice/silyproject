package com.swjsj.lucene;

import java.io.File;
import java.io.FileReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class DirectoryTest {

	// 数据源路径
	String dspath = "h:/logs";
	// 存放索引文件的位置，即索引库
	String indexpath = "H:\\workspace2\\lucene\\indexs01";
	// 分词器
	Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);

	/**
	 * 创建索引，会抛异常，因为没对索引库进行保存
	 * 
	 * IndexWriter 用来操作（增、删、改）索引库的
	 */
	@org.junit.Test
	public void createIndex() throws Exception {
		// Directory dir=FSDirectory.getDirectory(indexpath);
		// 内存存储：优点速度快，缺点程序退出数据就没了，所以记得程序退出时保存索引库，已FSDirectory结合使用
		// 由于此处只暂时保存在内存中，程序退出时没进行索引库保存，因此在搜索时程序会报错
		Directory dir = new RAMDirectory();
		// Document存放经过组织后的数据源，只有转换为Document对象才可以被索引和搜索到
		Document doc = null;
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, analyzer);
		IndexWriter iw = new IndexWriter(dir, config);
		File[] files = new File(dspath).listFiles();
		for (File file : files) {
			doc = new Document();
			doc.add(new Field("content", new FileReader(file)));
			doc.add(new Field("filename",file.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
			doc.add(new Field("path",file.getAbsolutePath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
			iw.addDocument(doc);
		}
		iw.close();
		
		IndexReader ir = IndexReader.open(dir);
		IndexSearcher is = new IndexSearcher(ir);
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		QueryParser qp = new  QueryParser(Version.LUCENE_36, "content", analyzer);
		Query query = qp.parse("jishijun204@163.com");
		TopDocs td = is.search(query, 10);
		ScoreDoc[] sd = td.scoreDocs;
		for (ScoreDoc scoreDoc : sd) {
			Document doc1  = is.doc(scoreDoc.doc);
			System.out.println(doc1.get("path")+ " 文件名：" +doc1.get("filename"));
		}
	}

}

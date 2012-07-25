package com.swjsj.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class HelloLucene {

	public void index(){
		IndexWriter iw = null;
		try {
			FSDirectory d = FSDirectory.open(new File("H:/workspace2/lucene/indexs"));
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, analyzer);
			iw = new IndexWriter(d, config);

			Document doc = null;
			File[] files = new File("h:/logs").listFiles();
			for (File file : files) {
				doc = new Document();
				doc.add(new Field("content", new FileReader(file)));
				doc.add(new Field("filename",file.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
				doc.add(new Field("path",file.getAbsolutePath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
				iw.addDocument(doc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				iw.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void search(){
		IndexReader ir  = null;
		IndexSearcher  is  = null;
		try {
			FSDirectory d = FSDirectory.open(new File("H:/workspace2/lucene/indexs"));
			ir = IndexReader.open(d);
			is = new IndexSearcher(ir);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			QueryParser qp = new  QueryParser(Version.LUCENE_36, "content", analyzer);
			Query query = qp.parse("jishijun204@163.com");
			TopDocs td = is.search(query, 10);
			ScoreDoc[] sd = td.scoreDocs;
			for (ScoreDoc scoreDoc : sd) {
				Document doc  = is.doc(scoreDoc.doc);
				System.out.println(doc.get("path")+ " 文件名：" +doc.get("filename"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally{
			try {
				ir.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

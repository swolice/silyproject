package com.swjsj.analyzer;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import com.swjsj.searcher.SearchUtils;

public class AnalyzerTest {
 	@Test
	public void test01(){
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_36);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_36);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_36);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_36);
		
		String str ="this is my analyzer test,it' ok ? ";
		
		AnalyzerUtils.analyzer01(str, a1);
		AnalyzerUtils.analyzer01(str, a2);
		AnalyzerUtils.analyzer01(str, a3);
		AnalyzerUtils.analyzer01(str, a4);
		
		
	}
	
	@Test
	public void test02(){
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_36);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_36);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_36);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_36);
		
		String str ="中文分词其作用不 ，测试一下就知道了。 ";
		
		AnalyzerUtils.analyzer02(str, a1);
		System.out.println("=-------------------=");
		AnalyzerUtils.analyzer02(str, a2);
		System.out.println("=-------------------=");
		AnalyzerUtils.analyzer02(str, a3);
		System.out.println("=-------------------=");
		AnalyzerUtils.analyzer02(str, a4);
		
		
	}
	
	@Test
	public void test03(){
		Analyzer a1 = new MyStopAnalyzer(new String[]{"i","test"});
//		Analyzer a1 = new MyStopAnalyzer();
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_36);
		String str ="this is my analyzer test,it' ok ?  ";
		
		AnalyzerUtils.analyzer02(str, a1);
		System.out.println("=-------------------=");
		AnalyzerUtils.analyzer02(str, a2);
	}
	
	
	@Test
	public void test04(){
//		第三方的分词器
		MMSegAnalyzer a1 = new MMSegAnalyzer("H:\\源码\\mmseg4j-1.8.5\\data\\");
//		最大匹配
		ComplexAnalyzer a2 = new ComplexAnalyzer("H:\\源码\\mmseg4j-1.8.5\\data\\");
		String str ="MMSeg 算法有两种分词方法：Simple和Complex，吉仕军 都是基于正向最大匹配。Complex 加了四个规则过虑。白云山 ";
		AnalyzerUtils.analyzer02(str, a1);
		System.out.println("===================");
		AnalyzerUtils.analyzer02(str, a2);
		
		
		
	}
	@Test
	public void test05(){
		//第三方的分词器此功能可以让外置程序做相关的控制
		Analyzer a1 = new MySameWordAnalyzer();
		String str ="此功能可以让外置程序做相关的控制";
		Directory dir = new RAMDirectory();
		try {
			IndexWriter iw = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_36, a1));
			Document doc = new Document();
			doc.add(new Field("content",str,Field.Store.YES,Field.Index.ANALYZED));
			iw.addDocument(doc);
			iw.commit();
			
			SearchUtils su = new SearchUtils(dir);
			IndexSearcher is = su.getIndexSearcher();
			QueryParser qp = new  QueryParser(Version.LUCENE_36, "content", a1);
			Query query = qp.parse("zuo");
			TopDocs td = is.search(query, 10);
			System.out.println(is.doc(td.scoreDocs[0].doc).get("content"));
			
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}

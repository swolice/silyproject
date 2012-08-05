package com.swjsj.searcher;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

public class SearchTest {

	
	private SearchUtils su;
	@Before
	public void init(){
		su = new SearchUtils();
	}
	
	@Test
	public void index(){
		su.index();
	}
	
	@Test
	public void search1(){
		su.SearchByQueryParse1();
	}
	
	@Test
	public void search2(){
//		QueryParser qp = new QueryParser(Version.LUCENE_36, "content",
//				new SimpleAnalyzer(Version.LUCENE_36));
		Query query;	
		//不支持全部匹配？？
		query = new TermQuery(new Term("filename","netsd_info.log"));
		su.SearchByQueryParse2(query);
	}
	@Test
	public void search3(){
		QueryParser qp = new QueryParser(Version.LUCENE_36, "content",
				new SimpleAnalyzer(Version.LUCENE_36));
		Query query;
		try {
//			query = qp.parse("jishijun 163.");
//			query = qp.parse("filename:publish*");
			qp.setAllowLeadingWildcard(true);
//			query = qp.parse("filename:*_info.log");
			
//			query = qp.parse("- filename:netsdlog*.log + filename:netsd*");
			
			//完全匹配不起作用了 使用下面的方法
			query = qp.parse("filename:\"netsd_info.log\"");
//			query = new TermQuery(new Term("filename","netsd_info.log"));
			
			query = new WildcardQuery(new Term("filename","*.log"));
			
			su.SearchByQueryParse2(query);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void searchPage1(){
		QueryParser qp = new QueryParser(Version.LUCENE_36, "content",
				new SimpleAnalyzer(Version.LUCENE_36));
		Query query;
		try {
			query = qp.parse("i");
			su.searchNoPage(query);
			System.out.println("=====================");
			su.searchByPage1(query, 2, 20);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void searchByPage2(){
		QueryParser qp = new QueryParser(Version.LUCENE_36, "content",
				new SimpleAnalyzer(Version.LUCENE_36));
		Query query;
		try {
			query = qp.parse("i");
			su.searchNoPage(query);
			System.out.println("=====================");
			su.searchByPage1(query, 2, 20);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
}

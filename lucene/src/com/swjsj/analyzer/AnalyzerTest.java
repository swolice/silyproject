package com.swjsj.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Test;

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
	
}

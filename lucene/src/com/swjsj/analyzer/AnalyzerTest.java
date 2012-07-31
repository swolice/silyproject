package com.swjsj.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

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
	
}

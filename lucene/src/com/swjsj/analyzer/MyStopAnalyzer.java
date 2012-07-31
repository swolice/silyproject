package com.swjsj.analyzer;

import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LetterTokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;

public class MyStopAnalyzer extends Analyzer {
	private Set stopWs = new HashSet();

	public MyStopAnalyzer(String[] str) {
		this.stopWs = StopFilter.makeStopSet(Version.LUCENE_36, str);
		//添加所有的过滤词到set中，必须要用addAll方法
		stopWs.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
	}
	
	public MyStopAnalyzer() {
		//添加所有的过滤词到set中，必须要用addAll方法
		stopWs.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new StopFilter(Version.LUCENE_36, new LowerCaseFilter(
				Version.LUCENE_36, new LetterTokenizer(Version.LUCENE_36,
						reader)), stopWs);
	}
}

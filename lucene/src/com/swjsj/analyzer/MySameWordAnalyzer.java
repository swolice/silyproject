package com.swjsj.analyzer;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;

public class MySameWordAnalyzer  extends Analyzer{
	
	@Override
	public TokenStream tokenStream(String arg0, Reader arg1) {
		Dictionary dic = Dictionary.getInstance("C:/Documents and Settings/sily/My Documents/Downloads/mmseg4j-1.8.5/data");
		SimpleSameWord ssw = new SimpleSameWord();
		return new MySameWordFilter(new MMSegTokenizer(new MaxWordSeg(dic), arg1),ssw);
	}

}

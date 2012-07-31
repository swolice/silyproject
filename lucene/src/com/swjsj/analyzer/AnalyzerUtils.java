package com.swjsj.analyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionLengthAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class AnalyzerUtils {

	public static void analyzer01(String str, Analyzer a) {
		TokenStream ts = a.tokenStream("test", new StringReader(str));
		CharTermAttribute cta = ts.addAttribute(CharTermAttribute.class);
		try {
			while (ts.incrementToken()) {
				System.out.print("[" + cta + "]");
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void analyzer02(String str, Analyzer a) {
		TokenStream ts = a.tokenStream("test", new StringReader(str));
		CharTermAttribute cta = ts.addAttribute(CharTermAttribute.class);
		OffsetAttribute oa = ts.addAttribute(OffsetAttribute.class);
		PositionIncrementAttribute pia = ts
				.addAttribute(PositionIncrementAttribute.class);
		PositionLengthAttribute pla = ts
				.addAttribute(PositionLengthAttribute.class);
		TypeAttribute ta = ts.addAttribute(TypeAttribute.class);

		try {
			while (ts.incrementToken()) {
				System.out.println("[" + cta + "][" + oa.startOffset() + "-"
						+ oa.endOffset() + "][" + pia.getPositionIncrement()
						+ "]" + pla + ta);
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

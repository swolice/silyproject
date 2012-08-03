package com.swjsj.analyzer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.AttributeSource;

public class MySameWordFilter extends TokenFilter {
	private CharTermAttribute cta = null;
	private PositionIncrementAttribute pia = null;
	private Stack<String> samewords = null;
	private AttributeSource.State state = null;
	private ISameWord sw = null;

	public MySameWordFilter(TokenStream input,ISameWord sw) {
		super(input);
		cta = input.addAttribute(CharTermAttribute.class);
		pia = input.addAttribute(PositionIncrementAttribute.class);
		samewords = new Stack<String>();
		this.sw = sw;
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (!samewords.isEmpty()) {
			restoreState(state);
			cta.setEmpty();
			cta.append(samewords.pop());
			pia.setPositionIncrement(0);
			return true;
		}
		
		if (!input.incrementToken()){
			return false;
		}

		String[] strs = sw.getSameWords(cta.toString());
		
		if (null != strs) {
			for (String str : strs) {
				samewords.push(str);
			}
			state = captureState();
		}
		return true;
	}
}

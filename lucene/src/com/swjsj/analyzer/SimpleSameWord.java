package com.swjsj.analyzer;

import java.util.HashMap;
import java.util.Map;

public class SimpleSameWord implements ISameWord {

	private Map<String, String[]> sws = new HashMap<String, String[]>();
	
	public SimpleSameWord() {
		sws.put("可以", new String[] { "能够", "行的" ,"keyi"});
		sws.put("做", new String[] { "do", "干" ,"zuo"});
	}

	public String[] getSameWords(String name) {
		if (sws.containsKey(name)) {
			return sws.get(name);
		}
		return null;
	}
}

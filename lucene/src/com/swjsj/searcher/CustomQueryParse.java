package com.swjsj.searcher;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;

public class CustomQueryParse extends QueryParser {

	public CustomQueryParse(Version matchVersion, String f, Analyzer a) {
		super(matchVersion, f, a);
	}
	
	@Override
	protected org.apache.lucene.search.Query getFuzzyQuery(String field,
			String termStr, float minSimilarity) throws ParseException {
		throw new ParseException("系统禁用了模糊查询");
	}
	
	@Override
	protected org.apache.lucene.search.Query getWildcardQuery(String field,
			String termStr) throws ParseException {
		throw new ParseException("系统禁用了通配符查询");
	}

}

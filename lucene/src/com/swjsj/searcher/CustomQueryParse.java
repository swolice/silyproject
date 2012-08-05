package com.swjsj.searcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
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

	@Override
	protected org.apache.lucene.search.Query getRangeQuery(String field,
			String part1, String part2, boolean inclusive)
			throws ParseException {
		if ("size".equals(field)) {
			return NumericRangeQuery.newDoubleRange(field, Double.parseDouble(part1),
					Double.parseDouble(part2), inclusive, inclusive);
		}
		if("date".equals(field)){
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			df.setLenient(false);
			try {
				Date d1 = df.parse(part1);
				Date d2 = df.parse(part2);
				return NumericRangeQuery.newLongRange(field, d1.getTime(),
					d2.getTime(), inclusive, inclusive);
			} catch (java.text.ParseException e) {
				throw new ParseException("输入的日期格式不合法");
			}
		}
		return super.getRangeQuery(field, part1, part2, inclusive);
	}
}

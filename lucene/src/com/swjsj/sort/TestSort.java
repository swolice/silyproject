package com.swjsj.sort;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermRangeFilter;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

import com.swjsj.searcher.SearchUtils;

public class TestSort {

	private SortUtils su;

	@Before
	public void init() {
		su = new SortUtils();
	}

	@Test
	public void index() {
		new SearchUtils().index();
	}

	@Test
	public void testScore() {
		MyScoreUtils msu = new MyScoreUtils();
		msu.scortSort();
	}

	@Test
	public void testsort() {
		su.sort("to", null);
		// su.sort("i", Sort.INDEXORDER);

		// su.sort(" \to ", Sort.RELEVANCE);

		// su.sort("\to", new Sort(new SortField("date",SortField.LONG)));
		// su.sort("\to", new Sort(new SortField("size",SortField.DOUBLE)));

		// su.sort("\to", new Sort(new
		// SortField("size",SortField.DOUBLE,true)));

		// su.sort("i", new Sort(new SortField("score",SortField.INT)));

	}

	@Test
	public void testfilter() {
		Filter filter = new TermRangeFilter("filename", "netsdlog.log",
				"netsdlog.log", true, true);
		filter = NumericRangeFilter.newDoubleRange("size", 1000.0, 10000.0,
				true, true);
		filter = new QueryWrapperFilter(new WildcardQuery(new Term("filename",
				"*.txt")));
		QueryParser qp = new QueryParser(Version.LUCENE_36, "content",
				new StandardAnalyzer(Version.LUCENE_36));
		Query query;
		try {
			query = qp.parse("so");
			su.filter(query, filter);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCustFilter() {
		Filter filter = new MyIdCustFilter(new FilterAccess() {
			public boolean isSet() {
				return false;
			}
			public String[] getValues() {
				return new String[]{"1","2","3","4","7"};
			}
			public String getField() {
				return "id";
			}
		});
		QueryParser qp = new QueryParser(Version.LUCENE_36, "content",
				new StandardAnalyzer(Version.LUCENE_36));
		Query query;
		try {
			query = qp.parse("so");
			su.customFilter(query, filter);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}

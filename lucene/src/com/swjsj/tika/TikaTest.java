package com.swjsj.tika;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.swjsj.searcher.SearchUtils;

public class TikaTest {

	@Test
	public void index(){
		TikaIndexUtils tiu = new TikaIndexUtils();
		tiu.index();
	}
	
	
	@Test
	public void search3(){
		QueryParser qp = new QueryParser(Version.LUCENE_36, "content",
				new StandardAnalyzer(Version.LUCENE_36));
		Query query;
		try {
			qp.setAllowLeadingWildcard(true);
			query = NumericRangeQuery.newDoubleRange("size", 10.0, 10000.0, true, true) ;
			
			SearchUtils su = new SearchUtils(FSDirectory.open(new File(
			"H:/workspace2/lucene/indexs03")));
			su.SearchByQueryParse2(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

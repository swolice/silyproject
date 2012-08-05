package com.swjsj.searcher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

public class CustomQueryUtils {

	public void SearchByCustQueryParse(String val) {
		IndexSearcher is  = new SearchUtils().getIndexSearcher();
		try {
			CustomQueryParse qp = new CustomQueryParse(Version.LUCENE_36, "content",
					new StandardAnalyzer(Version.LUCENE_36));
			Query query = qp.parse(val);
			TopDocs sds = is.search(query, 100);
			Document doc = null;
			for (ScoreDoc sd : sds.scoreDocs) {
				doc = is.doc(sd.doc);
				System.out.println(" doc.id = " + sd.doc + " sd.score:"
						+ sd.score + "--> filename :" + doc.get("filename")
						+ "--> path = " + doc.get("path") + "---->"
						+ "--> size:" + doc.get("size") + " --> date: "
						+ getLong2DateStr(doc.get("date")));
			}
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getLong2DateStr(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Long l = Long.parseLong(str);
		Date date = new Date();
		date.setTime(l);
		return sdf.format(date);
	}
	
}

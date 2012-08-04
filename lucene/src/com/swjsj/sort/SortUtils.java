package com.swjsj.sort;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SortUtils {

	private static IndexReader reader = null;
	private Directory directory = null;

	public SortUtils() {
		try {
			directory = FSDirectory.open(new File(
					"H:/workspace2/lucene/indexs02"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public IndexSearcher getIndexSearcher() {
		try {
			if (reader == null) {
				reader = IndexReader.open(directory);
			} else {
				IndexReader ir = reader.openIfChanged(reader);
				if (ir != null) {
					if (reader != null) {
						reader.close();
					}
					reader = ir;
				}
			}
			IndexSearcher is = new IndexSearcher(reader);
			return is;
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sort(String qstr, Sort sort) {
		IndexSearcher is = getIndexSearcher();
		try {
			QueryParser qp = new QueryParser(Version.LUCENE_36, "content",
					new SimpleAnalyzer(Version.LUCENE_36));
			Query query = qp.parse(qstr);
			TopDocs td = null;
			if(null != sort){
				td= is.search(query, 150, sort);
			}else{
				td= is.search(query, 150);
			}
			
			ScoreDoc[] sds = td.scoreDocs;
			Document doc = null;
			for (ScoreDoc sd : sds) {
				doc = is.doc(sd.doc);
				System.out.println(" doc.id = " + sd.doc + " sd.score:"
						+ sd.score + "--> filename :" + doc.get("filename")
						+ "--> path = " + doc.get("path") + "---->"
						+ "--> size:" + doc.get("size") + " --> date: "
						+ doc.get("date"));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void filter(Query query, Filter filter) {
		IndexSearcher is = getIndexSearcher();
		
		try {
			TopDocs td= is.search(query,filter, 150);
			ScoreDoc[] sds = td.scoreDocs;
			Document doc = null;
			for (ScoreDoc sd : sds) {
				doc = is.doc(sd.doc);
				System.out.println(" doc.id = " + sd.doc + " sd.score:"
						+ sd.score + "--> filename :" + doc.get("filename")
						+ "--> path = " + doc.get("path") + "---->"
						+ "--> size:" + doc.get("size") + " --> date: "
						+ doc.get("date"));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

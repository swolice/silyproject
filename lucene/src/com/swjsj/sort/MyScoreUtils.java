package com.swjsj.sort;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;
import org.apache.lucene.search.function.FieldScoreQuery;
import org.apache.lucene.search.function.FieldScoreQuery.Type;
import org.apache.lucene.search.function.ValueSourceQuery;

public class MyScoreUtils {

	public void scortSort() {
		SortUtils su = new SortUtils();
		IndexSearcher is = su.getIndexSearcher();
		TermQuery tq = new TermQuery(new Term("content", "i"));
//		FieldScoreQuery fsq = new FieldScoreQuery("score", Type.INT);
//		MyCustomScoreQuery mcsq = new MyCustomScoreQuery(tq, fsq);
		FileNameCustomScoreQuery fncsq = new FileNameCustomScoreQuery(tq);
		TopDocs td;
		try {
			td = is.search(fncsq, 150);
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
			e.printStackTrace();
		}

	}

	private class FileNameCustomScoreQuery extends CustomScoreQuery {
		private static final long serialVersionUID = -8974666950750498553L;

		public FileNameCustomScoreQuery(Query subQuery) {
			super(subQuery);
		}

		@Override
		protected CustomScoreProvider getCustomScoreProvider(IndexReader reader)
				throws IOException {
			return new FileNameCustomScoreProvider(reader);
		}

	}

	private class FileNameCustomScoreProvider extends CustomScoreProvider {
		private String[] filenames;
		
		public FileNameCustomScoreProvider(IndexReader reader) {
			super(reader);
			try {
				filenames = FieldCache.DEFAULT.getStrings(reader, "filename");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public float customScore(int doc, float subQueryScore, float valSrcScore)
				throws IOException {
			String filename = filenames[doc];
			if(filename.endsWith(".txt")){
				return subQueryScore*100.0f;
			}
			return subQueryScore;
		}
	}

	private class MyCustomScoreQuery extends CustomScoreQuery {
		private static final long serialVersionUID = -8974666950750498553L;

		public MyCustomScoreQuery(Query subQuery, ValueSourceQuery valSrcQuery) {
			super(subQuery, valSrcQuery);
		}

		@Override
		protected CustomScoreProvider getCustomScoreProvider(IndexReader reader)
				throws IOException {
			return new MyCustomScoreProvider(reader);
		}

	}

	private class MyCustomScoreProvider extends CustomScoreProvider {

		public MyCustomScoreProvider(IndexReader reader) {
			super(reader);
		}

		@Override
		public float customScore(int doc, float subQueryScore, float valSrcScore)
				throws IOException {
			// System.out.println("subQueryScore:" + subQueryScore +
			// " valSrcScore:" + valSrcScore);
			return subQueryScore / valSrcScore;
		}
	}

}
